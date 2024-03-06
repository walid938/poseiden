package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameServiceImpl;
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
public class RuleNameServiceImplTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @Test
    public void testFindAll() {
        // Arrange
        List<RuleName> ruleNames = Arrays.asList(new RuleName(), new RuleName());
        Mockito.when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        // Act
        List<RuleName> result = ruleNameService.findAll();

        // Assert
        assert result != null;
        assert result.size() == 2;
    }

    @Test
    public void testInsertRuleName() {
        // Arrange
        RuleName ruleName = new RuleName();

        // Act
        ruleNameService.insertRuleName(ruleName);

        // Assert
        Mockito.verify(ruleNameRepository, Mockito.times(1)).save(ruleName);
    }

    @Test
    public void testUpdateRuleName() {
        // Arrange
        int id = 1;
        RuleName existingRuleName = new RuleName();
        existingRuleName.setId(id);

        RuleName updatedRuleName = new RuleName();
        updatedRuleName.setId(id);

        Mockito.when(ruleNameRepository.findById(id)).thenReturn(Optional.of(existingRuleName));

        // Act
        boolean result = ruleNameService.updateRuleName(id, updatedRuleName);

        // Assert
        assert result;
        Mockito.verify(ruleNameRepository, Mockito.times(1)).save(existingRuleName);
    }

    @Test
    public void testFindById() {
        // Arrange
        int id = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(id);

        Mockito.when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        // Act
        RuleName result = ruleNameService.findById(id);

        // Assert
        assert result != null;
        assert result.getId() == id;
    }

    @Test
    public void testDeleteById() {
        // Arrange
        int id = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(id);

        Mockito.when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        // Act
        ruleNameService.deleteById(id);

        // Assert
        Mockito.verify(ruleNameRepository, Mockito.times(1)).delete(ruleName);
    }
}

