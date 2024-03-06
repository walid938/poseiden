package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername_UserExists() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    void testFindByUsername_UserNotExists() {
        // Arrange
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertNull(foundUser);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    void testFindById_UserExists() {
        // Arrange
        int id = 1;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findById(id);

        // Assert
        assertNotNull(foundUser);
        assertEquals(id, foundUser.getId());
    }

    @Test
    void testFindById_UserNotExists() {
        // Arrange
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        User foundUser = userService.findById(id);

        // Assert
        assertNull(foundUser);
    }

    @Test
    void testUpdateUser_UserExists() {
        // Arrange
        int id = 1;
        User existingUser = new User();
        existingUser.setId(id);

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUsername("UpdatedUsername");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(encoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        // Act
        boolean result = userService.updateUser(id, updatedUser);

        // Assert
        assertTrue(result);
        assertEquals("UpdatedUsername", existingUser.getUsername());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_UserNotExists() {
        // Arrange
        int id = 1;
        User updatedUser = new User();
        updatedUser.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = userService.updateUser(id, updatedUser);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testSave() {
        // Arrange
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        when(encoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        // Act
        userService.save(user);

        // Assert
        verify(encoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDelete() {
        // Arrange
        User user = new User();
        user.setId(1);

        // Act
        userService.delete(user);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }
}

