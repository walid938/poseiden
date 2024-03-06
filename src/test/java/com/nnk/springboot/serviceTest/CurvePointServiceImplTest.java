package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointServiceImpl;
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
public class CurvePointServiceImplTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @Test
    public void testFindAll() {
        // Arrange
        List<CurvePoint> curvePoints = Arrays.asList(new CurvePoint(), new CurvePoint());
        Mockito.when(curvePointRepository.findAll()).thenReturn(curvePoints);

        // Act
        List<CurvePoint> result = curvePointService.findAll();

        // Assert
        assert result != null;
        assert result.size() == 2;
    }

    @Test
    public void testInsertCurvePoint() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();

        // Act
        curvePointService.insertCurvePoint(curvePoint);

        // Assert
        Mockito.verify(curvePointRepository, Mockito.times(1)).save(curvePoint);
    }

    @Test
    public void testUpdateCurvePoint() {
        // Arrange
        int id = 1;
        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setId(id);

        CurvePoint updatedCurvePoint = new CurvePoint();
        updatedCurvePoint.setId(id);

        Mockito.when(curvePointRepository.findById(id)).thenReturn(Optional.of(existingCurvePoint));

        // Act
        boolean result = curvePointService.updateCurvePoint(id, updatedCurvePoint);

        // Assert
        assert result;
        Mockito.verify(curvePointRepository, Mockito.times(1)).save(existingCurvePoint);
    }

    @Test
    public void testFindById() {
        // Arrange
        int id = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(id);

        Mockito.when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        // Act
        CurvePoint result = curvePointService.findById(id);

        // Assert
        assert result != null;
        assert result.getId() == id;
    }

    @Test
    public void testDeleteById() {
        // Arrange
        int id = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(id);

        Mockito.when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        // Act
        curvePointService.deleteById(id);

        // Assert
        Mockito.verify(curvePointRepository, Mockito.times(1)).delete(curvePoint);
    }
}
