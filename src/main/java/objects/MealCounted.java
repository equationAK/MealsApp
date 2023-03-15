package objects;

public class MealCounted extends Meal {

            /*
                =================================================================
                *                                                               *
                *           The following class is an extension of the Meal     *
                *           object adding the counter of popularity             *
                =================================================================

     */

    private Integer counter;


    public MealCounted(int meal_Id, String meal_Name, String meal_Category, String meal_Area, String meal_Instructions, String meal_Photo_Url, String meal_Youtube_Url, Integer counter) {
        super(meal_Id,meal_Name,meal_Category,meal_Area,meal_Instructions,meal_Photo_Url,meal_Youtube_Url);
        this.counter = counter;
    }

    public MealCounted() {
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}