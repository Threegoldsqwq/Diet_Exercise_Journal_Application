package Facade;
import Generator.*;
import DatabaseOperation.RuntimeDatabase;
import OutcomeGenerator.*;
import Visualizer.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * This class is the facade for user to use
 */
public class NutritionServiceFacade {

    public NutritionServiceFacade(){}

    /**
     * This method display the profile
     * @return an array of profile
     * @throws Exception sql exception
     */
    public String[] displayProfile() throws Exception {
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        ArrayList<String> profile = runtimeDatabase.extractProfile();
        String[] result = new String[profile.size()];
        for(int i = 0; i < profile.size(); i++){
            result[i] = profile.get(i);
        }
        return result;
    }

    /**
     * This method invoke the RuntimeDatabase class's method to create profile
     */
    public void createProfile(String UserName, String sex, int year, int month, int day, double height, double weight, String measurement){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.createProfile(UserName, sex, year, month, day, height, weight, measurement);
    }

    /**
     * This method is for profile selection
     * @param id is the userID to access the database
     */
    public void selectProfile(int id){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.setId(id);
        runtimeDatabase.setMealInfo(runtimeDatabase.readAllMealInfo(id));
        runtimeDatabase.setExerciseInfo(runtimeDatabase.readAllExerciseInfo(id));
    }

    /**
     * This method log the diet same as RuntimeDatabase
     * @param date is the date
     * @param mealType is the meal type
     * @param ingredients is the ingredients
     * @param quantity is the quantity
     */
    public void logDiet(String date, String mealType, String ingredients, String quantity){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        ArrayList<String> ingredientsList = new ArrayList<>();
        ArrayList<String> quantityList = new ArrayList<>();
        String[] temp = ingredients.split(", ");
        String[] temp2 = quantity.split(", ");
        for(int i = 0; i < temp.length; i++){
            ingredientsList.add(temp[i]);
            quantityList.add(temp2[i]);
        }
        runtimeDatabase.logMeal(date, mealType, ingredientsList, quantityList);
    }

    /**
     * This method is for log exercise same as RuntimeDatabase
     * update the 2D array exerciseInfo[][]
     */
    public void logExercise(String date, String exerciseType, String duration, String intensity) throws ParseException{
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.logExercise(date, exerciseType, duration, intensity);
    }

    /**
     * This method invoke the writeBack function to write everything back to the database
     */
    public void writeBack(){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.writeAllMealBack();
        runtimeDatabase.writeExerciseBack();
    }

    /**
     * This method show all the dates for date selection
     * @return an array of existing dates
     */
    public String[] showDates(){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        String[][] meals = runtimeDatabase.getMealInfo();
        String[] result = new String[meals.length];
        for(int i = 0; i < meals.length; i++){
            result[i] = meals[i][0];
        }
        return result;
    }

    /**
     * This method get total calorie intake
     * @return a 2D array same as RuntimeDatabase
     */
    public static String[][] getTotalCalorieIntake(){
        return CalorieDataVisualizer.CaloryIntakeDataReader();
    }

    /**
     * This method get the ingredients and quantity for each meal for detailed meal page
     * @param type is the meal type
     * @param selectedDate is the selected date
     * @return an array in {ingredients, quantities in g/ml}
     */
    public String[] getIngredientsAndQuantity(int type, String selectedDate){
        ReportGenerator generator = new DetailedReportGenerator();
        return generator.getIngredientsAndQuantity(type, selectedDate);
    }

    /**
     * This method get the other nutrients for each meal for detailed meal page
     * @param type is the meal type
     * @param selectedDate is the selected date
     * @return an array in {other nutrients, values in g}
     */
    public String[] getOtherNutrients(int type, String selectedDate){
        ReportGenerator generator = new DetailedReportGenerator();
        return generator.getOtherNutrients(type, selectedDate);
    }

    /**
     * This method get the quantity of foods that user eat for each food group on CFG
     * Since CFG only has 3 big groups, we made vegetable as vegetable,
     * Protein as the sum of Oil/Fat/Protein, and Whole Grain as Baked product in our database
     * @return an array of food amount in category {Vegetable, Protein, Whole Grain}
     */
    public double[] getTotalFoodGroupIntake(){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        double[] newFoodGroup = new double[3];

        //vege, protein, grain
        newFoodGroup[0] = runtimeDatabase.getFoodGroup()[3];
        newFoodGroup[1] = runtimeDatabase.getFoodGroup()[0] + runtimeDatabase.getFoodGroup()[1] + runtimeDatabase.getFoodGroup()[2];
        newFoodGroup[2] = runtimeDatabase.getFoodGroup()[4];
        //System.out.println("Vege: " + newFoodGroup[0] + " Protein: " + newFoodGroup[1] + " Whole Grain: " + newFoodGroup[2]);
        return newFoodGroup;
    }

    /**
     * This method display diet chart
     */
    public static void displayDietChart(String startDate, String endDate) {
        //call diet chart module. modify number of nutrition here
        DietDataVisualizer helper=new DietDataVisualizer();
        helper.getChart(5,startDate,endDate);
    }

    /**
     * This method display the calorie intake chart
     */
    public static void displayCalorieChart(String startDate, String endDate) throws ParseException {
        //call calorie intake and burned chart module
        CalorieDataVisualizer helper=new CalorieDataVisualizer();
        helper.getChart(startDate,endDate);
    }

    /**
     * This method display the weight forecast
     */
    public static String getWeightForecast(String date) throws ParseException {
        double weightForecast = Calculator.weightForecast(date);

        // Format the result with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format(weightForecast);

        // Add "+" if the result is positive
        if (weightForecast >= 0) {
            formattedNumber = "+" + formattedNumber;
        }

        return formattedNumber;
    }

    /**
     * This method display the chart of user intake and CFG recommendation
     */
    public static void getCFGchart(double[] userIntake,double[] cfgRecommendations ){
        //change here
        CFG dietChart = new CFG("Diet Comparison Chart", userIntake, cfgRecommendations);
        dietChart.displayChart();
    }

    /**
     * This method invoke the modifyProfile in RuntimeDatabase for edit profile
     */
    public void editProfile(String name, String gender, int parseInt, int parseInt1, int parseInt2, double parseDouble, double parseDouble1, String measurement) {
        //change here
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.modifyProfile(name, gender, parseInt, parseInt1, parseInt2, parseDouble, parseDouble1, measurement);
        System.out.println("editProfile called");
    }
}
