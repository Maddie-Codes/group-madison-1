package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Varieties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.launchcode.violetSwap.models.data.VarietiesRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@Controller
@RequestMapping("/varieties")
public class VarietiesController {

    @Autowired
    private VarietiesRepository varietiesRepository;

    @GetMapping
    public String showVarieties(Model model) {
        List<Varieties> varieties = varietiesRepository.findAll();
        model.addAttribute("varieties", varieties);
        return "/browse/varieties";
    }

     @GetMapping("/search/variety/{id}")
    public String showListingsForVariety(@PathVariable Long id, Model model) {
        Varieties selectedVariety = varietiesRepository.findById(id).orElse(null);
        if (selectedVariety != null) {
            model.addAttribute("listings", selectedVariety.getListings());
            model.addAttribute("selectedVariety", selectedVariety);
            return "/browse/search";
        } else {
            return "redirect:/varieties";
        }
    }
}
