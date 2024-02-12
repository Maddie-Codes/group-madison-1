package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.Variety;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.launchcode.violetSwap.models.data.VarietyRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//connects to search/varieties, and search/variety/{id}
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private VarietyRepository varietyRepository;



    @GetMapping("/browse-varieties")//_________________________________________________Browse Varieties
    public String showVarieties(Model model) {
        List<Variety> varieties = varietyRepository.findAll();
        model.addAttribute("varieties", varieties);
        return "/search/varieties";
    }

    @GetMapping("/variety/{id}")//_________________________________________________Show Listings in selected Variety
    public String showListingsForVariety(@RequestParam(required = false) String varietySearch, @PathVariable Integer id, Model model) {
        Variety selectedVariety = varietyRepository.findById(id).orElse(null);
        if (selectedVariety != null) {
            model.addAttribute("listings", selectedVariety.getListings());
            model.addAttribute("selectedVariety", selectedVariety);
            return "/search/listings";
        } else {
            return "redirect:/search/varieties";
        }
    }

    //___________________________________________________________________________search for a variety

    @GetMapping("/varieties")
    public String searchVarieties(Model model, @RequestParam(required = false) String varietySearch) {
        List<Variety> varieties; //field for varieties

        if (varietySearch != null && !varietySearch.isEmpty()) { //if varietySearch is present
            Variety foundVariety = varietyRepository.findByName(varietySearch); //find the variety
            if (foundVariety != null) { //if found variety is present, redirect to search/search/variety w/ the id
                return "redirect:/search/variety/" + foundVariety.getId();
            }
            // Handle case when variety is not found
            return "redirect:/search/varieties";
        } else {
            varieties = varietyRepository.findAll();
        }

        model.addAttribute("varieties", varieties);
        return "search/varieties";
    }



}
