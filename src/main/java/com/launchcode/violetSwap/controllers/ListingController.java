package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.FileValidator;
import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.Maturity;
import com.launchcode.violetSwap.models.data.ListingRepository;
import com.launchcode.violetSwap.models.data.VarietyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("listing")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;


    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private FileValidator fileValidator;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Make sure to apply the validator only to the appropriate class
        if (binder.getTarget() instanceof MultipartFile) {
            binder.addValidators(fileValidator);
        }
    }

    //________________________________________________________________________________________________listing/new.html - make a new listing
    @GetMapping("new")
    public String displayNewListingForm(Model model) {
        model.addAttribute(new Listing());
        model.addAttribute("varieties", varietyRepository.findAll()); //note for future to pass in the available AV varieties, does not have to follow this naming convention/structure

        model.addAttribute("maturityLevels", Maturity.values());//enum Maturity

        return "listing/new";
    }

    @PostMapping("new")
    public String processNewListingForm(@ModelAttribute @Valid Listing newListing,
                                        @RequestParam(value = "photo", required = false) MultipartFile photoFile,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "listing/new";
        } else {
            listingRepository.save(newListing);
            redirectAttributes.addFlashAttribute("successMessage", "Listing created successfully");
            return "redirect:/listing/new";
        }
    }

    //________________________________________________________________________________________________end of listing/new.html


}
