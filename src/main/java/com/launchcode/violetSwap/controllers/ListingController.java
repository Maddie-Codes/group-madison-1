package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.Maturity;
import com.launchcode.violetSwap.models.Variety;
import com.launchcode.violetSwap.models.data.ListingRepository;
import com.launchcode.violetSwap.models.data.VarietyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("listing")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private VarietyRepository varietyRepository;

    //________________________________________________________________________________________________listing/new.html - make a new listing
    @GetMapping("new")
    public String displayNewListingForm(Model model) {
        model.addAttribute(new Listing());
        model.addAttribute("maturityLevels", Maturity.values());//enum Maturity
        model.addAttribute("availableVarieties", varietyRepository.findAll());
        return "listing/new";
    }

    @PostMapping("new")
    public String processNewListingForm(@ModelAttribute @Valid Listing newListing, Errors errors, Model model){
        if (errors.hasErrors()){
            return "listing/new";
        } else{
            listingRepository.save(newListing);
        }
        //if no errors, save listing to repository

        return "redirect:";
    }
    //________________________________________________________________________________________________end of listing/new.html

    //__________________________________________________________________________________________listing/newVariety - make a new variety

    @GetMapping("newVariety")
    public String displayNewVarietyForm (Model model){
        model.addAttribute(new Variety());
        return"listing/newVariety";
    }

    @PostMapping("newVariety")
    public String processNewVarietyForm(@ModelAttribute @Valid Variety newVariety, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute(new Variety());
            return "listing/newVariety";
        } else{
            varietyRepository.save(newVariety);

        }
        model.addAttribute(new Listing());
        model.addAttribute("maturityLevels", Maturity.values());//enum Maturity
        model.addAttribute("availableVarieties", varietyRepository.findAll());
        return "listing/new";
    }
    //________________________________________________________________________________________________end of listing/newVariety.html

}
