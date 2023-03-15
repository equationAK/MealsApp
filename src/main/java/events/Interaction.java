package events;
import DB.ConnectDB;
import GUI.MealsAppMain;
import REST.OkHttp;
import REST.Response;
import REST.UrlForRest;
import objects.Category;
import objects.Meal;
import objects.MealCounted;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Interaction {


    //create an object of Interaction
    private static Interaction interactionInstance = new Interaction();

    //make the constructor private so that this class cannot be instantiated
    private Interaction() {
    }

    //Get the only object available
    public static Interaction getInteractionInstance() {
        if (interactionInstance == null)
            interactionInstance = new Interaction();
        return interactionInstance;
    }



           // The following block is to search meal by name from API

    public List<String> searchMealByName(String mealName) {

        OkHttp callHttp = new OkHttp();
        UrlForRest urlForRest = UrlForRest.getUrlForRestInstance();
        String json;
        List<String> mealsNameList = new ArrayList<>();
        try {
            json = callHttp.run(urlForRest.getSearchUrl(1, mealName));
            Response response = Response.getResponseInstance();
            response.setJson(json);
            response.jsonByName();
            response.getMealList().forEach(meal -> mealsNameList.add(meal.getMeal_Name()));
        } catch (IOException e) {
            MsgToUser.infoBox("No internet", "No internet");
        }
        return mealsNameList;
    }

    // method to return a meal by its name
    public Meal mealByUsersChoice(String mealName) {
        return Response.getResponseInstance().getMealHashMap().get(mealName);
    }


    // method that inserts a meal in the DB after it validates if the meal is in DB
    public void insertMealInDB(Meal meal) {
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        if(connectDB.isInMeals(meal.getMeal_Id())){
            MsgToUser.infoBox("The meal is already in the Favorites","");
        } else {
            connectDB.insertNewMeal(meal.getMeal_Id(), meal.getMeal_Name(), meal.getMeal_Category(), meal.getMeal_Area(),
                    meal.getMeal_Instructions(), meal.getMeal_Youtube_Url(), meal.getMeal_Photo_Url());
        }
    }

    // the method is used to present the name of categories in the searchByCategory jFrame
    public List<String> mealCategoriesNames() {
        List<String> listCategoriesNames = new ArrayList<>();
        OkHttp callHttp = new OkHttp();
        UrlForRest urlForRest = UrlForRest.getUrlForRestInstance();
        String json;
        try {
            json = callHttp.run(urlForRest.getSearchUrl(5));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Response response = Response.getResponseInstance();
        response.setJson(json);
        response.jsonAllCategories();
        response.getAllCategories().forEach(category -> listCategoriesNames.add(category.getCategoryName()));
        return listCategoriesNames;
    }

    // method that returns the meals that are contained in a category from API by category name

    public List<String> categoriesContents(String categoryName) {
        List<String> categoriesContents = new ArrayList<>();
        OkHttp callHttp = new OkHttp();
        UrlForRest urlForRest = UrlForRest.getUrlForRestInstance();
        String json;
        try {
            json = callHttp.run(urlForRest.getSearchUrl(10,categoryName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Response response = Response.getResponseInstance();
        response.setJson(json);
        response.jsonByFilterCategory();
        return response.getCategoryFilter();
    }

    // method inputs category contents in List and returns \n String -- needs check
    public String categoriesContentsByLine(List<String> categoriesContents) {
        String categoriesLineByLine = new String();
        for (String content : categoriesContents)
            categoriesLineByLine += content + "\n";
        Response response = Response.getResponseInstance();
        response.setResponseNull();
        return categoriesLineByLine;
    }


    // method to return the photo URL of each category directly from the API
    public String categoryImageURL(String categoryName){

        OkHttp callHttp = new OkHttp();
        UrlForRest urlForRest = UrlForRest.getUrlForRestInstance();
        String json;
        HashMap<String,String> categoryPhotoUrl = new HashMap<>();
        try {
            json = callHttp.run(urlForRest.getSearchUrl(5));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Response response = Response.getResponseInstance();
        response.setJson(json);
        response.jsonAllCategories();
        response.getAllCategories().forEach(category -> categoryPhotoUrl.put(category.getCategoryName(),category.getCategoryPhotoUrl()));
        return categoryPhotoUrl.get(categoryName);
    }


    // method to select * on Meals.ta to return all the meals names
    public List<String> selectAllReturnMealName() {
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        return connectDB.selectAllReturnMealName();
    }

    // a method on application startup to create the DB tables

    public void startUp(){
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        connectDB.createTableMeals();
        connectDB.createTableCategory();
    }

    // a method to drop the DB tables

    public void dropTables(){
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        connectDB.dropTables();
    }


    // method to select on meals by meal Name

    public MealCounted selectMealForFav(String mealName){
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        return connectDB.selectMealForFav(mealName);
    }


    // method to increase the counter by 1

    public void setCounterPlus1(int meal_id) {
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        connectDB.setCounterPlus1(meal_id);
    }


    // method to return Hashmap of statistics in desc order

    public LinkedHashMap<String, Integer> mostViewedMeals() {
        return ConnectDB.getConnectionInstance().getMealStatistics();
    }


    // method to update dy mealID

    public void updateMeal(int id, String meal_Name, String meal_Category, String meal_Area, String meal_Instructions){
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        connectDB.updateMeal(id, meal_Name, meal_Category, meal_Area, meal_Instructions);
    }


    // method that deletes a meal from the DB

    public void deleteFromFav(int id) {
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        connectDB.deleteMeal(id);
    }

    // method that deletes all meals from the Meals table

    public void deleteFromFav() {
        ConnectDB connectDB = ConnectDB.getConnectionInstance();
        connectDB.deleteAllinMeals();
    }

    // the method that enables the GUI for user interaction -- goes to Main --
    public void guiStart(){
        ImageIcon icon= new ImageIcon("icon.ico");
        JFrame frame = new JFrame("MealsApp");
        frame.setIconImage(icon.getImage());
        MealsAppMain m = new MealsAppMain();
        frame.setContentPane(m.MealsAppPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // center the jframe on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Method to check the validity of a URL
    public boolean isValidUrl(String url) {
        try {
            URL temp=new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /*
                =================================================================
                *       The following methods were made during development      *
                *       from the BackEnd team for debugging and are kept in     *
                *       case of new features of the MealsApp                    *
                =================================================================

     */


    // method to return Category List of the meal categories from API
    public List<Category> mealCategories() {
        OkHttp callHttp = new OkHttp();
        UrlForRest urlForRest = UrlForRest.getUrlForRestInstance();
        String json;
        try {
            json = callHttp.run(urlForRest.getSearchUrl(5));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Response response = Response.getResponseInstance();
        response.setJson(json);
        response.jsonAllCategories();
        return response.getAllCategories();
    }

    // method to return the category Names into a string dividing  them \n
    public String mealCategoriesByLine(List<Category> allCategories) {
        String categoriesLineByLine = null;
        for (Category category : allCategories)
            categoriesLineByLine = category.getCategoryName() + "\n";
        return categoriesLineByLine;
    }

    public void bounceStart(String str){
        String result = null;
        String check = UrlForRest.getUrlForRestInstance().getSearchUrl(Integer.valueOf(13));
        if(str.equals(check)) {
            result = UrlForRest.getUrlForRestInstance().getSearchUrl(Integer.valueOf(12));
            try {
                Desktop.getDesktop().browse(new URL(result).toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
