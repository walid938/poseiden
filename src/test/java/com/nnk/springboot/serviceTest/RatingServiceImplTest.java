package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    public void testFindAll() {
        // Arrange
        List<Rating> ratings = Arrays.asList(new Rating(), new Rating());
        Mockito.when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<Rating> result = ratingService.findAll();

        // Assert
        assert result != null;
        assert result.size() == 2;
    }

    @Test
    public void testInsertRating() {
        // Arrange
        Rating rating = new Rating();

        // Act
        ratingService.insertRating(rating);

        // Assert
        Mockito.verify(ratingRepository, Mockito.times(1)).save(rating);
    }

    @Test
    public void testUpdateRating() {
        // Arrange
        int id = 1;
        Rating existingRating = new Rating();
        existingRating.setId(id);

        Rating updatedRating = new Rating();
        updatedRating.setId(id);

        Mockito.when(ratingRepository.findById(id)).thenReturn(Optional.of(existingRating));

        // Act
        boolean result = ratingService.updateRating(id, updatedRating);

        // Assert
        assert result;
        Mockito.verify(ratingRepository, Mockito.times(1)).save(existingRating);
    }

    @Test
    public void testFindById() {
        // Arrange
        int id = 1;
        Rating rating = new Rating();
        rating.setId(id);

        Mockito.when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        // Act
        Rating result = ratingService.findById(id);

        // Assert
        assert result != null;
        assert result.getId() == id;
    }

    @Test
    public void testDeleteById() {
        // Arrange
        int id = 1;
        Rating rating = new Rating();
        rating.setId(id);

        Mockito.when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        // Act
        ratingService.deleteById(id);

        // Assert
        Mockito.verify(ratingRepository, Mockito.times(1)).delete(rating);
    }
}
