package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @Mock
    private Model model;

    @InjectMocks
    private TradeController tradeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome() {
        List<Trade> tradeList = new ArrayList<>();
        when(tradeService.findAll()).thenReturn(tradeList);

        String viewName = tradeController.home(model);

        assertEquals("trade/list", viewName);
        verify(model).addAttribute("trades", tradeList);
    }

    @Test
    void testAddUser() {
        String viewName = tradeController.addUser(new Trade());

        assertEquals("trade/add", viewName);
    }

    @Test
    void testValidate() {
        Trade trade = new Trade();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(tradeService).insertTrade(trade);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(tradeService.findAll()).thenReturn(new ArrayList<>());

        String viewName = tradeController.validate(trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(model).addAttribute(eq("trades"), anyList());
    }

    @Test
    void testShowUpdateFormWhenTradeNotFound() {
        when(tradeService.findById(1)).thenReturn(null);

        String viewName = tradeController.showUpdateForm(1, model);

        assertEquals("error", viewName);
        verifyZeroInteractions(model); // Make sure no interactions with the model
    }

    @Test
    void testUpdateTrade() {
        // Arrange
        Trade trade = new Trade();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(tradeService.updateTrade(eq(1), any(Trade.class))).thenReturn(true);
        when(tradeService.findAll()).thenReturn(new ArrayList<>());

        // Act
        String viewName = tradeController.updateTrade(1, trade, bindingResult, model);

        // Assert
        assertEquals("redirect:/trade/list", viewName);
        verify(model, times(1)).addAttribute(eq("trades"), anyList());
    }

    @Test
    void testDeleteTrade() {
        int tradeId = 1;

        doAnswer(invocation -> {
            int id = invocation.getArgument(0);
            return id == tradeId;
        }).when(tradeService).deleteById(tradeId);

        String viewName = tradeController.deleteTrade(tradeId, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(model, times(1)).addAttribute(eq("trades"), anyList()); // Vérifiez une seule invocation
    }


}
