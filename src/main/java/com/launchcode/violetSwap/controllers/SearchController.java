package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.Search;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.Variety;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.launchcode.violetSwap.models.data.VarietyRepository;

import java.util.List;

//connects to search/varieties, and search/variety/{id}
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private VarietyRepository varietyRepository;

    private Search search; //instance of search, so we can call search methods




    @GetMapping("/varieties")//_________________________________________________Browse All Varieties and can search
    public String searchVarieties(Model model) {
        List<Variety> varieties = varietyRepository.findAll();
        model.addAttribute("varieties", varieties);
        return "/search/varieties";
    }

    @GetMapping("/variety/{id}")//_________________________________________________Show Listings in selected Variety
    public String showListingsForVariety( @PathVariable Integer id, Model model) {

        Variety selectedVariety = varietyRepository.findById(id).orElse(null);
        if (selectedVariety != null) {
            model.addAttribute("listings", selectedVariety.getListings());
            model.addAttribute("selectedVariety", selectedVariety);
            return "/search/listings";
        } else {
            return "redirect:/search/varieties";
        }
    }



    @PostMapping("/varieties")//_____________________________________________________________search for a variety
    public String processSearchVarieties(Model model, @RequestParam String varietySearch) {
        List<Variety> varieties; //field for varieties

        if (varietySearch != null && !varietySearch.isEmpty()) { //if varietySearch is present

            List<Variety> varietyList = search.searchVarieties(varietySearch); //search for variety, returns a list of varieties that contain the search term(s)

            if (varietyList != null) { //if varietyList is present

                if (varietyList.size() == 1){//if there's only 1 variety in varietyList, redirect to search/variety w/ the id.
                    Variety singleVariety = varietyList.get(0);
                    return "redirect:/search/variety/" + singleVariety.getId();

                }else{ //if there are multiple varieties in varietyList, add them to model, and return "search/varieties"
                    model.addAttribute("varieties",varietyList);
                    return "search/varieties";
                }
            }
            // if varietylist is not found, return to browse-varieties
            return "redirect:/search/varieties";
        }
        //if varietySearch is null or empty, return to search/browse-varieties
        return "redirect:/search/varieties";
    }



}
