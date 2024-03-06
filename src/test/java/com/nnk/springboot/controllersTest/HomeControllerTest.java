package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HomeControllerTest {

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @Test
    public void testHome() {
        // Arrange
        when(authentication.getName()).thenReturn("user");

        // Act
        String viewName = homeController.home(authentication, model);

        // Assert
        assertEquals("home", viewName);
        verify(model).addAttribute("name", "Login as: user");
    }

    @Test
    public void testAdminHome() {
        // Act
        String viewName = homeController.adminHome(model);

        // Assert
        assertEquals("redirect:/bidList/list", viewName);
    }
}

