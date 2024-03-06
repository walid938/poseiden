package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TradeController {
    // TODO: Inject Trade service
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // TODO: find all Trade, add to model
        List<Trade> trades = tradeService.findAll();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.insertTrade(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Get Trade by Id
        Trade trade = tradeService.findById(id);

        // Check if Trade is null
        if (trade == null) {
            // If Trade is not found, handle appropriately
            // For example, redirect to an error page or display an error message
            return "error"; // or return "redirect:/error";
        }

        // Add Trade to the model and return the update form
        model.addAttribute("trade", trade);
        return "trade/update";
    }


    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        Boolean updated = tradeService.updateTrade(id, trade);
        model.addAttribute("trades", tradeService.findAll()); // Toujours ajouter "trades" au modèle
        return "redirect:/trade/list";
    }


    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        tradeService.deleteById(id);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
}
