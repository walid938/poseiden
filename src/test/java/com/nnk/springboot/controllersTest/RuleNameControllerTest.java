package com.nnk.springboot.controllersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class RuleNameControllerTest {

    private RuleNameController ruleNameController;
    private RuleNameService ruleNameService;

    @BeforeEach
    void setUp() {
        ruleNameService = mock(RuleNameService.class);
        ruleNameController = new RuleNameController(ruleNameService);
    }

    @Test
    void testShowUpdateForm() {
        // Given
        int id = 1;
        RuleName ruleName = new RuleName();
        when(ruleNameService.findById(id)).thenReturn(ruleName);
        Model model = mock(Model.class);

        // When
        String viewName = ruleNameController.showUpdateForm(id, model);

        // Then
        assertEquals("ruleName/update", viewName);
        verify(model).addAttribute("ruleName", ruleName);
    }

    @Test
    void testUpdateRuleName_WithValidData() {
        // Given
        int id = 1;
        RuleName ruleName = new RuleName();
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(ruleNameService.updateRuleName(id, ruleName)).thenReturn(true);

        // When
        String viewName = ruleNameController.updateRuleName(id, ruleName, bindingResult, model);

        // Then
        assertEquals("redirect:/ruleName/list", viewName);
        verify(model).addAttribute("ruleNames", ruleNameService.findAll());
    }

    @Test
    void testUpdateRuleName_WithInvalidData() {
        // Given
        int id = 1;
        RuleName ruleName = new RuleName();
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = ruleNameController.updateRuleName(id, ruleName, bindingResult, model);

        // Then
        assertEquals("ruleName/update", viewName);
    }

    @Test
    void testDeleteRuleName() {
        // Given
        int id = 1;
        Model model = mock(Model.class);

        // When
        String viewName = ruleNameController.deleteRuleName(id, model);

        // Then
        assertEquals("redirect:/ruleName/list", viewName);
        verify(ruleNameService).deleteById(id);
        verify(model).addAttribute("ruleNames", ruleNameService.findAll());
    }


}
