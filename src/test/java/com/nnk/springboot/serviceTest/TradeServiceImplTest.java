package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeServiceImpl;
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
public class TradeServiceImplTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    public void testFindAll() {
        // Arrange
        List<Trade> trades = Arrays.asList(new Trade(), new Trade());
        Mockito.when(tradeRepository.findAll()).thenReturn(trades);

        // Act
        List<Trade> result = tradeService.findAll();

        // Assert
        assert result != null;
        assert result.size() == 2;
    }

    @Test
    public void testInsertTrade() {
        // Arrange
        Trade trade = new Trade();

        // Act
        tradeService.insertTrade(trade);

        // Assert
        Mockito.verify(tradeRepository, Mockito.times(1)).save(trade);
    }

    @Test
    public void testUpdateTrade() {
        // Arrange
        int id = 1;
        Trade existingTrade = new Trade();
        existingTrade.setId(id);

        Trade updatedTrade = new Trade();
        updatedTrade.setId(id);

        Mockito.when(tradeRepository.findById(id)).thenReturn(Optional.of(existingTrade));

        // Act
        boolean result = tradeService.updateTrade(id, updatedTrade);

        // Assert
        assert result;
        Mockito.verify(tradeRepository, Mockito.times(1)).save(existingTrade);
    }

    @Test
    public void testFindById() {
        // Arrange
        int id = 1;
        Trade trade = new Trade();
        trade.setId(id);

        Mockito.when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        // Act
        Trade result = tradeService.findById(id);

        // Assert
        assert result != null;
        assert result.getId() == id;
    }

    @Test
    public void testDeleteById() {
        // Arrange
        int id = 1;
        Trade trade = new Trade();
        trade.setId(id);

        Mockito.when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        // Act
        tradeService.deleteById(id);

        // Assert
        Mockito.verify(tradeRepository, Mockito.times(1)).delete(trade);
    }
}

