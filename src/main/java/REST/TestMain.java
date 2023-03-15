package REST;

public class TestMain {


        /*
                =================================================================
                *       The following methods were made during development      *
                *       from the BackEnd team for test and debugging and        *
                *       are kept in case of new features of the MealsApp        *
                =================================================================

     */


    /*
    public static void main(String[] args) throws IOException {

        ConnectDB connectDB = ConnectDB.getConnectionInstance();

        /*
           The following block is to test search meal by name

        OkHttp callHttp = new OkHttp();
        String json = callHttp.run("https://www.themealdb.com/api/json/v1/1/search.php?s=spicy");
        Response response = new Response();
        response.setJson(json);
        response.jsonByName();
        response.showJsonByName();

        */

        /*
                -- the following Block is to handle all categories List json


        OkHttp callHttp = new OkHttp();
        String json = callHttp.run("https://www.themealdb.com/api/json/v1/1/categories.php");
        Response response = new Response();
        response.setJson(json);
        response.jsonAllCategories();
        response.showJsonAllCategories();

        */



        /*
                -- the following block creates a DB to store all data of the app and
                        makes a connection to it --

        connectDB.createTableMeals();

        */



        /*
                -- The following block inserts a record to the DB from a json
                        meal and the shows the meals Table content --

         OkHttp callHttp = new OkHttp();
         String json = callHttp.run("https://www.themealdb.com/api/json/v1/1/search.php?s=meat");
         Response response = new Response();
         response.setJson(json);
         response.jsonByName();

         response.getMealList().forEach(meal -> connectDB.insertNewMeal(meal.getMeal_Id(), meal.getMeal_Name(),
                 meal.getMeal_Category(), meal.getMeal_Area(), meal.getMeal_Instructions(), meal.getMeal_Youtube_Url(),
                 meal.getMeal_Photo_Url()));

        */

        /* the following block deletes a specific meal from meals Table

        connectDB.deleteMeal(52979); -- the number is the meals_id

        */

         /*
                // the following block returns all the meals of a specific category from api
        Interaction interaction = new Interaction();
        interaction.categoriesContents("Beef").forEach(j->System.out.println(j));
        }
        */
}
