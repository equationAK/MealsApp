package REST;

import events.MsgToUser;
import objects.Category;
import objects.Meal;
import com.google.gson.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Response {

    private List<Meal> mealList = new ArrayList<>();
    private HashMap<String, Meal> mealHashMap = new HashMap<>();
    private List<Category> allCategories = new ArrayList<>();
    private List<String> categoryFilter = new ArrayList<>();
    private String json;

    //create an object of Response
    private static Response responseInstance = new Response();

    //make the constructor private so that this class cannot be instantiated
    private Response() {
    }

    //Get the only object available
    public static Response getResponseInstance() {
        if (responseInstance == null)
            responseInstance = new Response();
        return responseInstance;
    }

    // makes the Response null -- use on window close at GUI
    public void setResponseNull() {
        responseInstance = null;
    }


    public void setJson(String json) {
        this.json = json;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public HashMap<String,Meal> getMealHashMap(){
        return mealHashMap;
    }


    public List<String> getCategoryFilter() {
        return categoryFilter;
    }

    // method to get from API the data to fill the Meal List and Meal HashMap

    public void jsonByName() {

        JsonElement jsonTree = JsonParser.parseString(json);
        Meal meal = new Meal();
        //typeOfJson();
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        //System.out.println(jsonObject.get("meals")); // this is for testing
        // the following 2 lines are in case the search has on results
        if (jsonObject.get("meals").isJsonNull())
            MsgToUser.infoBox("Cannot find the meal!", "No results");

        JsonArray jsonArray = jsonObject.get("meals").getAsJsonArray();
        jsonArray.forEach(j -> {
            meal.setMeal_Id(j.getAsJsonObject().get("idMeal").getAsInt());
            meal.setMeal_Name(j.getAsJsonObject().get("strMeal").getAsString());
            meal.setMeal_Area(j.getAsJsonObject().get("strArea").getAsString());
            meal.setMeal_Category(j.getAsJsonObject().get("strCategory").getAsString());
            meal.setMeal_Instructions(j.getAsJsonObject().get("strInstructions").getAsString());
            meal.setMeal_Youtube_Url(j.getAsJsonObject().get("strYoutube").getAsString());
            meal.setMeal_Photo_Url(j.getAsJsonObject().get("strMealThumb").getAsString());
            mealHashMap.put(meal.getMeal_Name().toString(), new Meal(meal.getMeal_Id(), meal.getMeal_Name(), meal.getMeal_Category(), meal.getMeal_Area(),
                    meal.getMeal_Instructions(), meal.getMeal_Photo_Url(), meal.getMeal_Youtube_Url()));
            mealList.add(new Meal(meal.getMeal_Id(), meal.getMeal_Name(), meal.getMeal_Category(), meal.getMeal_Area(),
                    meal.getMeal_Instructions(), meal.getMeal_Photo_Url(), meal.getMeal_Youtube_Url()));

        });
    }

    // method to get from API the data to fill the Meals List filtered by Category name
    public void jsonByFilterCategory() {
        JsonElement jsonTree = JsonParser.parseString(json);
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        // System.out.println(jsonObject.get("meals")); -- this is for testing
        JsonArray jsonArray = jsonObject.get("meals").getAsJsonArray();
        jsonArray.forEach(j -> categoryFilter.add(j.getAsJsonObject().get("strMeal").getAsString()));
    }

    // method to get from API the data to fill the Category List and Category HashMap
    public void jsonAllCategories() {

        JsonElement jsonTree = JsonParser.parseString(json);
        if (jsonTree.isJsonObject()) {

            JsonObject jsonObject = jsonTree.getAsJsonObject();

            /* --The following line is useful in case of checking what the
                 json from okHttp call is a list--
             */
            //System.out.println(jsonObject.get("categories"));

            Category category = new Category();
            JsonArray jsonArray = jsonObject.get("categories").getAsJsonArray();
            jsonArray.forEach(j -> {
                                    category.setCategoryId(j.getAsJsonObject().get("idCategory").getAsInt());
                                    category.setCategoryName(j.getAsJsonObject().get("strCategory").getAsString());
                                    category.setCategoryPhotoUrl(j.getAsJsonObject().get("strCategoryThumb").getAsString());
                                    allCategories.add(new Category(category.getCategoryId(), category.getCategoryName(), category.getCategoryPhotoUrl()));
            });
        }
    }

        /*
                =================================================================
                *       The following methods were made during development      *
                *       from the BackEnd team for test and debugging and are    *
                *       kept in case of new features of the MealsApp            *
                =================================================================

     */

    // this method is used to check the type of json string
    // instead I can make methods to handle the json objects
    // depending on what is the requirement each time
    public void typeOfJson() {
        JsonElement jsonTree = JsonParser.parseString(json);
        if (jsonTree.isJsonObject()) {
            System.out.println("Object");
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            System.out.println(jsonObject);
            JsonElement jsonElement = jsonObject.get("categories");
            System.out.println(jsonElement);
            if (jsonElement.isJsonArray())
                System.out.println("Array");
            else if (jsonElement.isJsonPrimitive())
                System.out.println("Primitive");
            else if (jsonElement.isJsonNull())
                System.out.println("null");
            else
                System.out.println("what?");
        } else if (jsonTree.isJsonArray())
            System.out.println("Array");
        else if (jsonTree.isJsonPrimitive())
            System.out.println("Primitive");
        else if (jsonTree.isJsonNull())
            System.out.println("null");
        else
            System.out.println("what?");
    }

    // I used this only for debug
    public void showMealHashMap(){
        mealHashMap.forEach((j,k) -> System.out.println(k.getMeal_Name() + " " + k.getMeal_Id()));
    }



    // the method returns all values of the category list
    public void showJsonAllCategories() {
        allCategories.forEach(categ -> {
            System.out.println("====================================================================");
            System.out.println("Id: " + categ.getCategoryId() + "\nName: "
                    + categ.getCategoryName() + "\nPhotoUrl: " + categ.getCategoryPhotoUrl());
        });
    }

    // the method returns all values of the category list as String by /n
    public String allCategoriesLineByLine() {
        String categoriesLineByLine = null;
        for (Category category : allCategories)
            categoriesLineByLine = category.getCategoryName() + "\n";
        return categoriesLineByLine;
    }

    public void showJsonByName() {
        mealList.forEach(elem -> {
            System.out.println("====================================================================");
            System.out.println("Id: " + elem.getMeal_Id() + "\nName: " + elem.getMeal_Name() +
                    "\nArea: " + elem.getMeal_Area() + "\nCategory: " + elem.getMeal_Category() +
                    "\nInstructions: " + elem.getMeal_Instructions() + "\nYouTube: " + elem.getMeal_Youtube_Url() +
                    "\nPhotoUrl: " + elem.getMeal_Photo_Url());
        });
    }

    public void setCategoryFilterNull(){
        categoryFilter = null;
    }
}
