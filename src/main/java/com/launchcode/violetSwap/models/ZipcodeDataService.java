package com.launchcode.violetSwap.models;

//import com.externalapi.model.ZipcodeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ZipcodeDataService {


    @Autowired
    private final RestTemplate template = new RestTemplate();

    @Value("${user.Geocode_API_key}")
    private String geocodeAPIKey;


    //consume API and return all its information, returns Object Array
    public Object[] findAllDataComplete(String zipcode){
        return template.getForObject("https://geocode.maps.co/search?postalcode=" + zipcode +"&country=US&api_key=" + geocodeAPIKey, Object[].class);
    }

//    //get object from API, get values from json object
//    public Object findAllData(Integer zipcode){
//        Object object = template.getForObject("https://geocode.maps.co/search?postalcode=" + zipcode.toString() +"&country=US&api_key=" + geocodeAPIKey, Object.class);
//        String display_name = object.toString();
//        System.out.println("_________________________________" + display_name + "____________________________________________________________________________--");
//        return object;
//        //new ZipcodeData(String display_name, lat, lon) zipcodeData;
//    }


    //returns only fields specified in ZipcodeData class - gets a 500 error, I think this API is not set up for REST
    public ZipcodeData[] findAllData(String zipcode){
        //ZipcodeData[] zipInfo = new ZipcodeData[]{template.getForObject("https://geocode.maps.co/search?postalcode=" + zipcode.toString() + "&country=US&api_key=" + geocodeAPIKey, ZipcodeData.class)};
        //return zipInfo[0]; //return the first object in the array
        return template.getForObject("https://geocode.maps.co/search?postalcode=" + zipcode + "&country=US&api_key=" + geocodeAPIKey, ZipcodeData[].class);
    }

}
