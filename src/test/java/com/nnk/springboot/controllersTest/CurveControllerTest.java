package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @Mock
    private Model model;

    @InjectMocks
    private CurveController curveController;

    @Test
    public void testHome() {
        // Arrange
        List<CurvePoint> curvePoints = Arrays.asList(new CurvePoint(), new CurvePoint());
        when(curvePointService.findAll()).thenReturn(curvePoints);

        // Act
        String viewName = curveController.home(model);

        // Assert
        assertEquals("curvePoint/list", viewName);
        verify(model).addAttribute("curvePoints", curvePoints);
    }

    @Test
    public void testAddCurveForm() {
        // Act
        String viewName = curveController.addBidForm(new CurvePoint());

        // Assert
        assertEquals("curvePoint/add", viewName);
    }

    @Test
    public void testValidateValid() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();

        // Act
        String viewName = curveController.validate(curvePoint, mock(BindingResult.class), model);

        // Assert
        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).insertCurvePoint(curvePoint);
        verify(model).addAttribute("curvePoints", curvePointService.findAll());
    }

    @Test
    public void testValidateInvalid() {
        // Arrange
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = curveController.validate(new CurvePoint(), mockBindingResult, model);

        // Assert
        assertEquals("curvePoint/add", viewName);
        verify(curvePointService, never()).insertCurvePoint(any());
    }

    @Test
    public void testShowUpdateForm() {
        // Arrange
        int curvePointId = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.findById(curvePointId)).thenReturn(curvePoint);

        // Act
        String viewName = curveController.showUpdateForm(curvePointId, model);

        // Assert
        assertEquals("curvePoint/update", viewName);
        verify(model).addAttribute("curvePoint", curvePoint);
    }

    @Test
    public void testUpdateCurveValid() {
        // Arrange
        int curvePointId = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.updateCurvePoint(eq(curvePointId), any())).thenReturn(true);

        // Act
        String viewName = curveController.updateBid(curvePointId, curvePoint, mock(BindingResult.class), model);

        // Assert
        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).updateCurvePoint(curvePointId, curvePoint);
        verify(model).addAttribute("curvePoints", curvePointService.findAll());
    }

    @Test
    public void testUpdateCurveInvalid() {
        // Arrange
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = curveController.updateBid(1, new CurvePoint(), mockBindingResult, model);

        // Assert
        assertEquals("curvePoint/update", viewName);
        verify(curvePointService, never()).updateCurvePoint(anyInt(), any());
    }

    @Test
    public void testDeleteCurve() {
        // Arrange
        int curvePointId = 1;

        // Act
        String viewName = curveController.deleteBid(curvePointId, model);

        // Assert
        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).deleteById(curvePointId);
        verify(model).addAttribute("curvePoints", curvePointService.findAll());
    }
}

