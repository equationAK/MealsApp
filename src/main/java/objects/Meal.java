package objects;

import java.net.URL;

public class Meal {


        /*
                =================================================================
                *                                                               *
                *           The following class has the meal object             *
                *                                                               *
                =================================================================

     */




    private int meal_Id;
    private String meal_Name;
    private String meal_Category;
    private String meal_Area;
    private String meal_Instructions;
    private String meal_Photo_Url;
    private String meal_Youtube_Url;


    public Meal(int meal_Id, String meal_Name, String meal_Category, String meal_Area, String meal_Instructions, String meal_Photo_Url, String meal_Youtube_Url) {
        this.meal_Id = meal_Id;
        this.meal_Name = meal_Name;
        this.meal_Category = meal_Category;
        this.meal_Area = meal_Area;
        this.meal_Instructions = meal_Instructions;
        this.meal_Photo_Url = meal_Photo_Url;
        this.meal_Youtube_Url = meal_Youtube_Url;
    }

    public Meal() {
    }


    public int getMeal_Id() {
        return meal_Id;
    }

    public void setMeal_Id(int meal_Id) {
        this.meal_Id = meal_Id;
    }

    public String getMeal_Name() {
        return meal_Name;
    }

    public void setMeal_Name(String meal_Name) {
        this.meal_Name = meal_Name;
    }

    public String getMeal_Category() {
        return meal_Category;
    }

    public void setMeal_Category(String meal_Category) {
        this.meal_Category = meal_Category;
    }

    public String getMeal_Area() {
        return meal_Area;
    }

    public void setMeal_Area(String meal_Area) {
        this.meal_Area = meal_Area;
    }

    public String getMeal_Instructions() {
        return meal_Instructions;
    }

    public void setMeal_Instructions(String meal_Instructions) {
        this.meal_Instructions = meal_Instructions;
    }

    public String getMeal_Photo_Url() {
        return meal_Photo_Url;
    }

    public void setMeal_Photo_Url(String meal_Photo_Url) {
        this.meal_Photo_Url = meal_Photo_Url;
    }

    public String getMeal_Youtube_Url() {
        return meal_Youtube_Url;
    }

    public void setMeal_Youtube_Url(String meal_Youtube_Url) {
        this.meal_Youtube_Url = meal_Youtube_Url;
    }
}
