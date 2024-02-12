package com.launchcode.violetSwap.controllers;

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



    @GetMapping("/varieties")//_________________________________________________Browse Varieties
    public String showVarieties(Model model) {
        List<Variety> varieties = varietyRepository.findAll();
        model.addAttribute("varieties", varieties);
        return "/search/varieties";
    }

    @GetMapping("/search/variety/{id}")//_________________________________________________Browse Listings in Variety
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

    //___________________________________________________________________________browse variety

//    @GetMapping("/browse")
//    public String browseVarieties(Model model, @RequestParam(required = false) String varietySearch) {
//        List<Variety> varieties;
//        if (varietySearch != null && !varietySearch.isEmpty()) {
//            Variety foundVariety = varietyRepository.findByName(varietySearch);
//            if (foundVariety != null) {
//                return "redirect:/search/search/variety/" + foundVariety.getId();
//            }
//            // Handle case when variety is not found
//            return "redirect:/search";
//        } else {
//            varieties = varietyRepository.findAll();
//        }
//        model.addAttribute("varieties", varieties);
//        return "search/varieties";
//    }


}
