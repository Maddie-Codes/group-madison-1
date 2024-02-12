package com.launchcode.violetSwap.models;

import com.launchcode.violetSwap.models.data.ListingRepository;
import com.launchcode.violetSwap.models.data.UserRepository;
import com.launchcode.violetSwap.models.data.VarietyRepository;
import jakarta.persistence.MappedSuperclass;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MappedSuperclass //no table for Search
public class Search {

    private ListingRepository listingRepository;
    private UserRepository userRepository;
    private VarietyRepository varietyRepository;
    //TODO: intelliJ says .findAll() will return a null pointer exception?
    private final Iterable<Listing> availableListings = listingRepository.findAll(); //get available listings
    private final Iterable<User> availableUsers = userRepository.findAll();
    private final Iterable<Variety> availableVarieties = varietyRepository.findAll();


    //fields for filtered listings and users:
    List<Listing> filteredListings;
    List<User> filteredUsers;
    List<Variety> filteredVarieties;





    //remove extra parts from the search term to make it easier to search with
    public static String removeExtraChars(String string){
        String fixedString = string.toUpperCase(); // case insensitive (Uppercase)
        List<String> removeThese = Arrays.asList( "'", "-", "_", Character.toString('"')); //array [ ', -, _, "]

        for(String item : removeThese){
            fixedString = fixedString.replaceAll(item,""); // for every item present in removeThese, delete it
        }
        return fixedString;
    }
    //set up search term into a list (calls removeExtraChars)
    public List<String> makeSearchTerm (String searchTerm){
        String editedString = removeExtraChars(searchTerm); //call removeExtraChars
        List<String> editedSearchTerm = Arrays.asList(editedString.split(" "));  //convert the search term into a list
        return editedSearchTerm;
    }



    //search for varieties
    public List<Variety> searchVarieties(String search){
        List<String> searchItems = makeSearchTerm(search); //make search parameter into a list of Strings (searchItems)
        for(Variety variety : availableVarieties){ //For each variety
            int counter = searchItems.size();
            String varietyName = variety.getSearchTerm(); //get searchTerm of the variety.
            for(String searchItem : searchItems){ //For each searchItem
                if (varietyName.contains(searchItem)){ //check if varietyName contains the searchItem.
                    counter --; //if yes, mark it and move to next searchItem
                    if (counter == 0){//once counter reaches 0, all search items have been found in varietyName, and that variety can be added to the list.
                        filteredVarieties.add(variety);
                    }
                } else{
                    break;
                }
            }
        }
        return filteredVarieties;
    }




    //search Users by zipcode
    public List<User> filterUsersByZipcode(String searchZip){
        Integer searchZipcode = Integer.valueOf(searchZip);
        for(User user : availableUsers){
            if(user.getZipcode().equals(searchZipcode)){ //if the search term equals the zipcode, add it to filteredLocationUsers
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

//    //search Users by city / state, needs to have
//    public List<User> filterUsersByLocation(String searchCity, String searchState){
//        searchCity = searchCity.toUpperCase();
//        searchState = searchState.toUpperCase();
//        for(User user : availableUsers){
//            if(user.getCity().toUpperCase().contains(searchCity) && user.getState().toUpperCase().contains(user.getState())){ //if the search term contains the city and state, add it to filteredLocationUsers
//                filteredUsers.add(user);
//            }
//        }
//        return filteredUsers;
//    }


    //may be moved to controller??:
    //filters users by calling methods, then gets the listings from those users
    public List<Listing> filterListingsByZipcode(String searchZip){
        List<User> filteredLocationUsers = filterUsersByZipcode(searchZip); //call filterUsersByLocation/Zipcode
        for(User user : filteredLocationUsers){ //for every user returned,
            List<Listing> filteredLocationListings = user.getListings(); //get the user's listings
            filteredListings.addAll(filteredLocationListings); //save the listings to filteredListings
        }
        return filteredListings;
    }




    //search for users
    public List<User> searchUsers(String search){
        search = search.toUpperCase();
        for(User user : availableUsers){ //for every user
            if(user.getUsername().toUpperCase().contains(search)){ //if they contain the search term, save in filteredUsers
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }




    //controller will pick which search method to call based on inputs selected in the view and pass in the search term
    //method will return a List<> of listings or users or varieties

}
