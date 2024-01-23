package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.data.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private ListingRepository listingRepository;

    @RequestMapping
    public String search(Model model) {

      return "search";
    }

}
