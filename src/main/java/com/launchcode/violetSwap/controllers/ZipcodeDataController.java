package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.ZipcodeData;
import com.launchcode.violetSwap.models.ZipcodeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ZipcodeDataController {

    @Autowired
    private ZipcodeDataService zipcodeDataService;

    //temporary field just to figure this stuff out.
    String zipcode = "63119";

    @GetMapping("/complete")
    public Object getAllZipcodeDataComplete(){
        return zipcodeDataService.findAllDataComplete(zipcode);
    }

    @GetMapping("/getallzip")
    public ZipcodeData[] getAllZipcodeData(){
        return zipcodeDataService.findAllData(zipcode);
    }


}
