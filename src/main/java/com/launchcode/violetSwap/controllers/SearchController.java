package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Variety;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.launchcode.violetSwap.models.data.VarietyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//connects to search/varieties, and search/variety/{id}
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private VarietyRepository varietyRepository;



    @GetMapping("/varieties")//_________________________________________________Browse Varieties
    public String showVarieties(Model model) {
        List<Variety> varieties = varietyRepository.findAll();
        model.addAttribute("varieties", varieties);
        return "/search/varieties";
    }

    @GetMapping("/listings/{id}") //_____________________________________________Show listings in selected variety
    public String showListingsForVariety(@RequestParam String varietySearch, Model model) {
        Variety selectedVariety = varietyRepository.findByName(varietySearch);
        if (selectedVariety != null) {
            model.addAttribute("listings", selectedVariety.getListings());
            model.addAttribute("selectedVariety", selectedVariety);
            return "/search/listings";
        } else {
            return "redirect:/search/varieties";
        }
    }
    //___________________________________________________________________________


}
