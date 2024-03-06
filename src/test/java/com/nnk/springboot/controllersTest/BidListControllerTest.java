package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @Mock
    private Model model;

    @InjectMocks
    private BidListController bidListController;

    @Test
    public void testHome() {
        // Arrange
        List<BidList> bidLists = Arrays.asList(new BidList(), new BidList());
        when(bidListService.findAll()).thenReturn(bidLists);

        // Act
        String viewName = bidListController.home(model);

        // Assert
        assertEquals("bidList/list", viewName);
        verify(model).addAttribute("bidLists", bidLists);
    }

    @Test
    public void testAddBidForm() {
        // Act
        String viewName = bidListController.addBidForm(new BidList());

        // Assert
        assertEquals("bidList/add", viewName);
    }

    @Test
    public void testValidateValid() {
        // Arrange
        BidList bidList = new BidList();

        // Act
        String viewName = bidListController.validate(bidList, mock(BindingResult.class), model);

        // Assert
        assertEquals("redirect:/bidList/list", viewName);
        verify(bidListService).insertBidList(bidList);
        verify(model).addAttribute("bidLists", bidListService.findAll());
    }

    @Test
    public void testValidateInvalid() {
        // Arrange
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = bidListController.validate(new BidList(), mockBindingResult, model);

        // Assert
        assertEquals("bidList/add", viewName);
        verify(bidListService, never()).insertBidList(any());
    }

    @Test
    public void testShowUpdateForm() {
        // Arrange
        int bidListId = 1;
        BidList bidList = new BidList();
        when(bidListService.findById(bidListId)).thenReturn(bidList);

        // Act
        String viewName = bidListController.showUpdateForm(bidListId, model);

        // Assert
        assertEquals("bidList/update", viewName);
        verify(model).addAttribute("bidList", bidList);
    }

    @Test
    public void testUpdateBidValid() {
        // Arrange
        int bidListId = 1;
        BidList bidList = new BidList();
        when(bidListService.updateBidList(eq(bidListId), any())).thenReturn(true);

        // Act
        String viewName = bidListController.updateBid(bidListId, bidList, mock(BindingResult.class), model);

        // Assert
        assertEquals("redirect:/bidList/list", viewName);
        verify(bidListService).updateBidList(bidListId, bidList);
        verify(model).addAttribute("bidLists", bidListService.findAll());
    }

    @Test
    public void testUpdateBidInvalid() {
        // Arrange
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = bidListController.updateBid(1, new BidList(), mockBindingResult, model);

        // Assert
        assertEquals("bidList/update", viewName);
        verify(bidListService, never()).updateBidList(anyInt(), any());
    }

    @Test
    public void testDeleteBid() {
        // Arrange
        int bidListId = 1;
        BidList bidList = new BidList();
        when(bidListService.findById(bidListId)).thenReturn(bidList);

        // Act
        String viewName = bidListController.deleteBid(bidListId, model);

        // Assert
        assertEquals("redirect:/bidList/list", viewName);
        verify(bidListService).deleteById(bidListId);
        verify(model).addAttribute("bidLists", bidListService.findAll());
    }

    @Test
    public void testDeleteBidInvalidId() {
        // Arrange
        int bidListId = 1;
        when(bidListService.findById(bidListId)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bidListController.deleteBid(bidListId, model));
        verify(bidListService, never()).deleteById(anyInt());
        verify(model, never()).addAttribute(eq("bidLists"), any());
    }
}
