package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.auth.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByUsername() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String role = "USER";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assert userDetails != null;
        assert userDetails.getUsername().equals(username);
        assert userDetails.getPassword().equals(password);
        assert userDetails.getAuthorities().size() == 1;
        assert userDetails.getAuthorities().iterator().next().getAuthority().equals("USER");
    }


    private void assertThrows(Class<UsernameNotFoundException> usernameNotFoundExceptionClass, Object o) {
    }

    @Test
    public void testGetAuthorities() {
        // Arrange
        User user = new User();
        user.setRole("ADMIN");

        // Act
        Collection<GrantedAuthority> authorities = userDetailsService.getAuthorities(user);

        // Assert
        assert authorities != null;
        assert authorities.size() == 1;
        assert authorities.iterator().next().getAuthority().equals("ADMIN");
    }
}

