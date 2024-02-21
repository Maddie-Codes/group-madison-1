package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.SearchService;
import com.launchcode.violetSwap.models.Variety;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private SearchService searchService; //instance of searchService, so we can call search methods




    @GetMapping("/varieties")//_________________________________________________Browse All Varieties and can search
    public String searchVarieties(Model model) {

        //optional searchParam/query in url?
        //if searchParam is present, do the thing

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
    public String processSearchVarieties(Model model, @RequestParam String searchTerm) {

        if (searchTerm != null && !searchTerm.isEmpty()) { //if searchTerm is present

            List<Variety> varietyList = searchService.searchVarieties(searchTerm); //search for variety, returns a list of varieties that contain the search term(s)

            if (varietyList != null) { //if varietyList is present

                if (varietyList.size() == 1){//if there's only 1 variety in varietyList, redirect to search/variety w/ the id.
                    Variety singleVariety = varietyList.get(0);
                    return "redirect:/search/variety/" + singleVariety.getId();

                }else{ //if there are multiple varieties in varietyList, add them to model, and return "search/varieties"
//                    model = null; //reset model?? nope, bad. Model can't be null
                    //model.clear(); doesn't work :(
                    //how to clear the model so that it doesn't keep ahold of the previous request's varieties??

                    //HttpSession session = request.getSession();
                    //session.removeAttribute("varieties");

                    model.addAttribute("varieties",varietyList); //add varieties
                    return "/search/varieties";
                }
            }
            // if varietylist is not found, return to search/varieties
            return "redirect:/search/varieties";
        }
        //if varietySearch is null or empty, return to search/varieties
        return "redirect:/search/varieties";
    }





}
