package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Variety;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.launchcode.violetSwap.models.data.VarietyRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@Controller
@RequestMapping("/varieties")
public class VarietiesController {

    @Autowired
    private VarietyRepository varietyRepository;

    @GetMapping("/")
    public String showVarieties(Model model) {
        Iterable<Variety> varieties = varietyRepository.findAll();
        model.addAttribute("varieties", varieties);
        return "/browse/varieties";
    }

    @PostMapping("/search/variety/{id}")
    public String showListingsForVariety(long id,Model model) {
        Variety selectedVariety = varietyRepository.findById(id).orElse(null);
        if (selectedVariety != null) {
            model.addAttribute("listings", selectedVariety.getListings());
            model.addAttribute("selectedVariety", selectedVariety);
            return "/browse/search";
        } else {
            return "redirect:/varieties";
        }
    }




}
