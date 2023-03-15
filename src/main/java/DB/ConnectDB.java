package DB;

import events.MsgToUser;
import objects.Meal;
import objects.MealCounted;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ConnectDB {


    //create an object of ConnectDB
    private static ConnectDB connectionInstance = new ConnectDB();

    //make the constructor private so that this class cannot be instantiated
    private ConnectDB() {
    }

    //Get the only object available
    public static ConnectDB getConnectionInstance() {
        if (connectionInstance == null)
            connectionInstance = new ConnectDB();
        return connectionInstance;
    }

    public java.sql.Connection connect() {
        String connectionString = "jdbc:derby:MealsAppDB;create=true";  // this should change to the final DB name
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public void createTableMeals() {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String createSQL = "CREATE TABLE Meals" +
                    "(Meals_ID INTEGER NOT NULL PRIMARY KEY," +
                    "Meals_Name VARCHAR(150)," +
                    "Meals_Category VARCHAR(150)," +
                    "Meals_Area VARCHAR(100)," +
                    "Meals_Instructions VARCHAR(4000)," +
                    "Meals_Youtube VARCHAR(300)," +
                    "Meals_PhotoUrl VARCHAR(300)," +
                    "Counter INTEGER)";
            statement.executeUpdate(createSQL);
            statement.close();
            connection.close();
            //System.out.println("Done!"); // for BackEnd
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    //
    public void createTableCategory() {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String createSQL =
                    "CREATE TABLE Category" +
                            "(Category_ID INTEGER NOT NULL PRIMARY KEY," +
                            "Category_Name VARCHAR(150)," +
                            "Category_Description VARCHAR(300)," +
                            "Category_Url  VARCHAR(300))";
            statement.executeUpdate(createSQL);
            statement.close();
            connection.close();
            //System.out.println("Done!"); // for BackEnd
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }


    // before execution of this method a validation should take place if the meal
    // already exists
    public void insertNewMeal(int meal_Id, String meal_Name, String meal_Category, String meal_Area,
                              String meal_Instructions, String meal_YouTube_Url, String meal_Photo_Url) {
        try {
            java.sql.Connection connection = connect();
            String insertSQL = "Insert into Meals values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, meal_Id);
            preparedStatement.setString(2, meal_Name);
            preparedStatement.setString(3, meal_Category);
            preparedStatement.setString(4, meal_Area);
            preparedStatement.setString(5, meal_Instructions);
            preparedStatement.setString(6, meal_YouTube_Url);
            preparedStatement.setString(7, meal_Photo_Url);
            preparedStatement.setInt(8, 1);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                MsgToUser.infoBox("The Meal was added to Favorites","Success");
            } else {
                System.out.println("Something went wrong. Check the exception"); // for BackEnd
            }
            preparedStatement.close();
            connection.close();
            // System.out.println("Done!"); // for BackEnd
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }


    // a method to select on meals and return a List<String> με το ονόματα των meal στο Meals.ta
    public List<String> selectAllReturnMealName() {
        List<String> mealList = new ArrayList<>();
        String mealName = null;
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            String selectSQL = "Select * from meals";
            ResultSet rs = statement.executeQuery(selectSQL);
            while (rs.next()) {
                mealName = rs.getString("Meals_Name");
                mealList.add(mealName);
            }
            statement.close();
            connection.close();
            System.out.println("Done!"); // for BackEnd
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
        return mealList;
    }


    // method to select on meals by meal_id
    // this method needs to be trimmed for the gui Name / Category / Instructions / Area

    public MealCounted selectMealForFav(String mealName) {
        MealCounted meal = new MealCounted();
        try {
            java.sql.Connection connection = connect();
            String selectSQL = "SELECT * from Meals where meals_Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, mealName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                meal.setMeal_Id(rs.getInt("Meals_ID"));
                meal.setMeal_Name(rs.getString("Meals_Name"));
                meal.setMeal_Category(rs.getString("Meals_Category"));
                meal.setMeal_Area(rs.getString("Meals_Area"));
                meal.setMeal_Instructions(rs.getString("Meals_Instructions"));
                meal.setMeal_Youtube_Url(rs.getString("Meals_Youtube"));
                meal.setMeal_Photo_Url(rs.getString("Meals_PhotoUrl"));
                meal.setCounter(rs.getInt("counter"));
            }
            preparedStatement.close();
            connection.close();
            System.out.println("Done!"); // for BackEnd
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
        return meal;
    }


    // a method to delete all data from Meals Table

    public void deleteAllinMeals() {
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            String deleteAllSQL = "Delete from Meals";
            int count = statement.executeUpdate(deleteAllSQL);
            if (count > 0) {
                MsgToUser.infoBox(String.valueOf(count) + " records deleted", "Delete from Favorites");
                //System.out.println(count + " records deleted"); // for BackEnd
            } else
                System.out.println("Something went wrong. Check the exception"); // for BackEnd
            statement.close();
            connection.close();
            System.out.println("Done!"); // for BackEnd
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }


    // method to delete a meal in the meals table
    public void deleteMeal(int id) {

        try {
            java.sql.Connection connection = connect();
            String deleteSQL = "Delete from Meals where meals_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                MsgToUser.infoBox(String.valueOf(count) + " record deleted","");// this msg should return to GUI
            else
                System.out.println("Something went wrong. Check the exception"); // for BackEnd
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    // method that updates a record of the meals table

    public void updateMeal(int id, String meal_Name, String meal_Category, String meal_Area,
                           String meal_Instructions) {
        try {
            java.sql.Connection connection = connect();
            String updateSQL = "Update Meals set meals_Name = ?, meals_Category = ?, meals_Area = ?," +
                    " meals_Instructions = ? where meals_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, meal_Name);
            preparedStatement.setString(2, meal_Category);
            preparedStatement.setString(3, meal_Area);
            preparedStatement.setString(4, meal_Instructions);
            preparedStatement.setInt(5, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                MsgToUser.infoBox(String.valueOf(count) + " record updated","Success");// this msg should return to GUI
            else
                System.out.println("Something went wrong. Check the exception"); // for BackEnd
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    // a method that updates counter value of a record of the meals table
    public void updateMealCounter(int id, int counter) {
        try {
            java.sql.Connection connection = connect();
            String updateSQL = "Update Meals set counter = ? where meals_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, String.valueOf(counter));
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                System.out.println(count + " record updated"); // for BackEnd
            else
                System.out.println("Something went wrong. Check the exception"); // for BackEnd
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    // a method to check on tables if record already exists

    public boolean isInMeals(int id) {
        boolean existsInMeals = false;
        try {
            java.sql.Connection connection = connect();
            String selectSQL = "Select Meals_ID from meals where Meals_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                if (id == rs.getInt("meals_id")) {
                    //System.out.println("Error: This meal is already in the database!"); // for BackEnd
                    //System.out.println(rs.getInt("meals_id")); // for BackEnd
                    existsInMeals = true;
                }
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage()); // for BackEnd
        }
        return existsInMeals;
    }

    // method to return the counter of a meal in the DB
    public int getMealCounter(int id) {
        int counter = 0;
        try {
            java.sql.Connection connection = connect();
            String selectSQL = "SELECT counter from Meals where meals_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            counter = rs.getInt("counter");
            preparedStatement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
        return counter;
    }

    // a method to increase counter +1

    public void setCounterPlus1(int meal_id) {
        if(isInMeals(meal_id))
            updateMealCounter(meal_id,getMealCounter(meal_id) + 1);
        System.out.printf("Meal doesn't exist in DB");
    }

    // a method to drop the tables of the DB

    public void dropTables() {
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            String dropTable = "DROP TABLE Meals";
            statement.execute(dropTable);
            System.out.println("Meals Table deleted"); // this msg should return to GUI
            dropTable = "DROP TABLE Category";
            statement.execute(dropTable);
            System.out.println("Category Table deleted"); // this msg should return to GUI
            statement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    // method to return Hashmap<String,int> Food name and its counter in desk order
    public LinkedHashMap<String, Integer> getMealStatistics() {
        LinkedHashMap<String, Integer> statisticsMap = new LinkedHashMap<>();
        String mealName;
        Integer counter;
        try {
            java.sql.Connection connection = connect();
            String selectSQL = "SELECT meals_name,counter from Meals order by counter desc fetch first 10 rows only";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                mealName = rs.getString("Meals_Name");
                counter = rs.getInt("counter");
                statisticsMap.put(mealName,counter);
                //System.out.println("=============================================================================");
                //System.out.println(rs.getString("Meals_Name") + "\n" + rs.getInt("Counter"));
            }
            statisticsMap.forEach((j,k)-> System.out.println(j + " " + k + "\n"));
            preparedStatement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
        return statisticsMap;
    }


        /*
                =================================================================
                *       The following methods were made during development      *
                *       from the BackEnd team for test and debugging and are    *
                *       kept in case of new features of the MealsApp            *
                =================================================================

     */



    // I have to create a method to select on meals -- works fine
    public void selectAll() {
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            String selectSQL = "Select * from meals";
            ResultSet rs = statement.executeQuery(selectSQL);
            // the following msg should return to GUI
            while (rs.next()) {
                System.out.println("=============================================================================");
                System.out.println(rs.getInt("Meals_ID") + "\n" + rs.getString("Meals_Name") + "\n" +
                        rs.getString("Meals_Category") + "\n" + rs.getString("Meals_Area") + "\n" +
                        rs.getString("Meals_Instructions") + "\n" + rs.getString("Meals_Youtube") + "\n" +
                        rs.getString("Meals_PhotoUrl") + "\n" + rs.getInt("Counter"));
            }
            statement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }


    // I have to create a method to select on meals and return a List<Meal> -- needs check
    public List<Meal> selectAllReturnMeals() {
        List<Meal> mealList = new ArrayList<>();
        Meal meal = new Meal();
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            String selectSQL = "Select * from meals";
            ResultSet rs = statement.executeQuery(selectSQL);
            while (rs.next()) {
                meal.setMeal_Id(rs.getInt("Meals_ID"));
                meal.setMeal_Name(rs.getString("Meals_Name"));
                meal.setMeal_Category(rs.getString("Meals_Category"));
                meal.setMeal_Area(rs.getString("Meals_Area"));
                meal.setMeal_Instructions(rs.getString("Meals_Instructions"));
                meal.setMeal_Youtube_Url(rs.getString("Meals_Youtube"));
                meal.setMeal_Photo_Url(rs.getString("Meals_PhotoUrl"));
                mealList.add(meal);
            }
            statement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
        return mealList;
    }




    // I have to create a method to select on meals by meal_id -- works fine
    // this method needs to be trimmed for the gui Name / Category / Instructions / Area

    public void selectMeal(int id) {
        try {
            java.sql.Connection connection = connect();
            String selectSQL = "SELECT * from Meals where meals_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println("=============================================================================");
                System.out.println(rs.getInt("Meals_ID") + "\n" + rs.getString("Meals_Name") + "\n" +
                        rs.getString("Meals_Category") + "\n" + rs.getString("Meals_Area") + "\n" +
                        rs.getString("Meals_Instructions") + "\n" + rs.getString("Meals_Youtube") + "\n" +
                        rs.getString("Meals_PhotoUrl") + "\n" + rs.getInt("Counter"));
            }
            preparedStatement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }



    // I have to create a method to select on meals by meal_Name -- needs check
    // this method needs to be trimmed for the gui Name / Category / Instructions / Area

    public Meal selectMealByName(String mealName) {
        Meal meal = new Meal();
        try {
            java.sql.Connection connection = connect();
            String selectSQL = "SELECT * from Meals where Meals_Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, mealName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                meal.setMeal_Id(rs.getInt("Meals_ID"));
                meal.setMeal_Name(rs.getString("Meals_Name"));
                meal.setMeal_Category(rs.getString("Meals_Category"));
                meal.setMeal_Area(rs.getString("Meals_Area"));
                meal.setMeal_Instructions(rs.getString("Meals_Instructions"));
                meal.setMeal_Youtube_Url(rs.getString("Meals_Youtube"));
                meal.setMeal_Photo_Url(rs.getString("Meals_PhotoUrl"));
            }
            preparedStatement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
        return meal;
    }


    // I have to create a method to delete all data from Category Table -- works fine
    public void deleteAllinCategory() {
        try {
            java.sql.Connection connection = connect();
            Statement statement = connection.createStatement();
            String deleteAllSQL = "Delete from Category";
            int count = statement.executeUpdate(deleteAllSQL);
            if (count > 0)
                System.out.println(count + " records deleted"); // this msg should return to GUI
            else
                System.out.println("Something went wrong. Check the exception"); // this msg should return to GUI
            statement.close();
            connection.close();
            System.out.println("Done!"); // this msg should return to GUI
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }







}