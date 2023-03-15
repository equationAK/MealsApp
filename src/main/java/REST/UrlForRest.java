package REST;

import DB.ConnectDB;

import java.util.HashMap;
import java.util.HashSet;

public class UrlForRest {

    // the class contains the URLs to be used for the API
    // depending on the choices of the user searches

    private final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private final HashMap<Integer,String> queryType = new HashMap<>();

    /*
            ---   this the content table of the hashmap ---
    1. Search meal by name               ->    BASE_URL + search.php?s=  + searchStr
    2. List all meals by first letter    ->    BASE_URL + search.php?f= + character
    3. Lookup full meal details by id    ->    BASE_URL + lookup.php?i= + meal_Id
    4. Lookup a single random meal       ->    BASE_URL + random.php
    5. List all meal categories          ->    BASE_URL + categories.php
    6. List all Categories               ->    BASE_URL + list.php?c=list
    7. List all Area                     ->    BASE_URL + list.php?a=list
    8. List all Ingredients              ->    BASE_URL + list.php?i=list
    9. Filter by main ingredient         ->    BASE_URL + filter.php?i= + searchStr
    10.Filter by Category                ->    BASE_URL + filter.php?c= + searchStr
    11.Filter by Area                    ->    BASE_URL + filter.php?a= + searchStr
 */



    //create an object of urlForRest
    private static UrlForRest urlForRestInstance = new UrlForRest();

    //make the constructor private so that this class cannot be instantiated

    private UrlForRest(){
        queryType.put(1,BASE_URL + "search.php?s=");  // + searchStr
        queryType.put(2,BASE_URL + "search.php?f=");  // + searchStr
        queryType.put(3,BASE_URL + "lookup.php?i=");  // + meal_Id
        queryType.put(4,BASE_URL + "random.php");
        queryType.put(5,BASE_URL + "categories.php");
        queryType.put(6,BASE_URL + "list.php?c=list");
        queryType.put(7,BASE_URL + "list.php?a=list");
        queryType.put(8,BASE_URL + "list.php?i=list");
        queryType.put(9,BASE_URL + "filter.php?i="); // + searchStr
        queryType.put(10,BASE_URL + "filter.php?c="); // + searchStr
        queryType.put(11,BASE_URL + "filter.php?a="); // + searchStr
        queryType.put(12,"https://www.youtube.com/watch?v=wCkerYMffMo");
        queryType.put(13,"bananas");
    }

    //Get the only object available
    public static UrlForRest getUrlForRestInstance(){
        if (urlForRestInstance == null)
            urlForRestInstance = new UrlForRest();
        return urlForRestInstance;
    }

    public String getSearchUrl(Integer searchType, String searchStr) {
        return queryType.get(searchType) + searchStr;
    }

    public String getSearchUrl(Integer searchType){
        return queryType.get(searchType);
    }
}


