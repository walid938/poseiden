package com.nnk.springboot.controllersTest;
import com.nnk.springboot.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoginControllerTest {

    @Mock
    private Model model;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void testLogin() {
        // Act
        String viewName = loginController.login(model);

        // Assert
        assertEquals("login", viewName);
    }
}
