package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.MyErrorController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MyErrorControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private Model model;

    @InjectMocks
    private MyErrorController errorController;

    @Test
    public void testHandleErrorNotFound() {
        // Arrange
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.NOT_FOUND.value());

        // Act
        String viewName = errorController.handleError(request);

        // Assert
        assertEquals("error-404", viewName);
    }

    @Test
    public void testHandleErrorBadRequest() {
        // Arrange
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.BAD_REQUEST.value());

        // Act
        String viewName = errorController.handleError(request);

        // Assert
        assertEquals("403", viewName);
    }

    @Test
    public void testHandleErrorMethodNotAllowed() {
        // Arrange
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.METHOD_NOT_ALLOWED.value());

        // Act
        String viewName = errorController.handleError(request);

        // Assert
        assertEquals("405", viewName);
    }

    @Test
    public void testHandleErrorInternalServerError() {
        // Arrange
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // Act
        String viewName = errorController.handleError(request);

        // Assert
        assertEquals("error-500", viewName);
    }

    @Test
    public void testHandleErrorDefault() {
        // Arrange
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(null);

        // Act
        String viewName = errorController.handleError(request);

        // Assert
        assertEquals("error-404", viewName);
    }
}
