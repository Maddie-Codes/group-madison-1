package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.data.ListingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("listing")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    //________________________________________________________________________________________________listing/new.html - make a new listing
    @GetMapping("new")
    public String displayNewListingForm(Model model) {
        model.addAttribute(new Listing());
        //model.addAttribute("varieties", varietyRepository.findAll()); //note for future to pass in the available AV varieties, does not have to follow this naming convention/structure

        List<String> maturityLevels = new ArrayList<String>(); //list of maturity levels for the drop-down
        maturityLevels.add("Seeds");
        maturityLevels.add("Cutting");
        maturityLevels.add("Young Plant");
        maturityLevels.add("Mature Plant");
        model.addAttribute("maturityLevels", maturityLevels);

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


}
