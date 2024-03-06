package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private Model model;

    @InjectMocks
    private RatingController ratingController;

    @Test
    public void testHome() {
        // Arrange
        List<Rating> ratings = new ArrayList<>();
        when(ratingService.findAll()).thenReturn(ratings);

        // Act
        String viewName = ratingController.home(model);

        // Assert
        assertEquals("rating/list", viewName);
        verify(model).addAttribute("ratings", ratings);
    }

    @Test
    public void testAddRatingForm() {
        // Act
        String viewName = ratingController.addRatingForm(new Rating());

        // Assert
        assertEquals("rating/add", viewName);
    }

    @Test
    public void testValidateWithErrors() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = ratingController.validate(new Rating(), bindingResult, model);

        // Assert
        assertEquals("rating/add", viewName);
        verifyZeroInteractions(ratingService);  // Ensure service method is not called with errors
    }

    @Test
    public void testValidateWithoutErrors() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        List<Rating> ratings = new ArrayList<>();
        when(ratingService.findAll()).thenReturn(ratings);

        // Act
        String viewName = ratingController.validate(new Rating(), bindingResult, model);

        // Assert
        assertEquals("redirect:/rating/list", viewName);
        verify(ratingService).insertRating(any(Rating.class));
        verify(model).addAttribute("ratings", ratings);
    }
}

