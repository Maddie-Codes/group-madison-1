package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Variety;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.launchcode.violetSwap.models.data.VarietyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/varieties")
public class VarietyController {

    @Autowired
    private VarietyRepository varietiesRepository;

    @GetMapping
    public String showVarieties(Model model) {
        List<Variety> varieties = varietiesRepository.findAll();
        model.addAttribute("varieties", varieties);
        return "browse/varieties";
    }

    @GetMapping("/search/variety/{id}")
    public String showListingsForVariety(@PathVariable Integer id, @RequestParam(required = false) String varietySearch, Model model) {
        Variety selectedVariety = varietiesRepository.findById(id).orElse(null);

        if (selectedVariety != null) {
            model.addAttribute("listings", selectedVariety.getListings());
            model.addAttribute("selectedVariety", selectedVariety);
            return "browse/listing";
        } else {
            return "redirect:/varieties";
        }
    }

    @GetMapping("/browse")
    public String browseVarieties(Model model, @RequestParam(required = false) String varietySearch) {
        List<Variety> varieties;
        if (varietySearch != null && !varietySearch.isEmpty()) {
            Variety foundVariety = varietiesRepository.findByName(varietySearch);
            if (foundVariety != null) {
                return "redirect:/varieties/search/variety/" + foundVariety.getId();
            }
            // Handle case when variety is not found
            return "redirect:/varieties";
        } else {
            varieties = varietiesRepository.findAll();
        }
        model.addAttribute("varieties", varieties);
        return "browse/varieties";
    }
}
