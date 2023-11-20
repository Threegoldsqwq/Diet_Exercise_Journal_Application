package Facade;
import Generator.*;
import NewUi.ExericiseLogPage;
import Operator.*;
import DatabaseOperation.RuntimeDatabase;
import OutcomeGenerator.*;
import Visualizer.*;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * This class is the facade for user to use
 */
public class NutritionServiceFacade {

    public NutritionServiceFacade(){}

    public String[] displayProfile() throws Exception {
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        ArrayList<String> profile = runtimeDatabase.extractProfile();
        String[] result = new String[profile.size()];
        for(int i = 0; i < profile.size(); i++){
            result[i] = profile.get(i);
        }
        return result;
    }

    public void createProfile(String UserName, String sex, int year, int month, int day, double height, double weight, String measurement){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.createProfile(UserName, sex, year, month, day, height, weight, measurement);
    }

    public void selectProfile(int id){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.setId(id);
        runtimeDatabase.setMealInfo(runtimeDatabase.readAllMealInfo(id));
        runtimeDatabase.setExerciseInfo(runtimeDatabase.readAllExerciseInfo(id));
    }

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

    public void logExercise(String date, String exerciseType, String duration, String intensity) throws ParseException{
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.logExercise(date, exerciseType, duration, intensity);
    }

    public void writeBack(){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.writeAllMealBack();
        runtimeDatabase.writeExerciseBack();
    }

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
     * need a date
     * @param type
     * @param selectedDate
     * @return
     */
    public String[] getIngredientsAndQuantity(int type, String selectedDate){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        String[][] temp = new String[runtimeDatabase.getMealInfo().length][runtimeDatabase.getMealInfo()[0].length];
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[i].length; j++){
                temp[i][j] = runtimeDatabase.getMealInfo()[i][j];
            }
        }


        String[] ingredientsQuantity = new String[1];
        for(int i = 0; i < temp.length; i++){
            if(temp[i][0].equalsIgnoreCase(selectedDate)){
                ingredientsQuantity = temp[i][type + 1].split(" - ");
            }
        }
        for(int i = 0; i < ingredientsQuantity.length; i++){
            if(ingredientsQuantity[i].charAt(0) == 'e'){
                ingredientsQuantity[i] = ingredientsQuantity[i];
            }
            else{
                ingredientsQuantity[i] = ingredientsQuantity[i] + "g/ml";
            }
        }
        
        return ingredientsQuantity;
    }

    /**
     * need a date
     * @param type
     * @param selectedDate
     * @return
     */
    public String[] getOtherNutrients(int type, String selectedDate){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        String[][] temp = new String[runtimeDatabase.getOtherNutrientInfo().length][runtimeDatabase.getOtherNutrientInfo()[0].length];
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[i].length; j++){
                temp[i][j] = runtimeDatabase.getOtherNutrientInfo()[i][j];
            }
        }


        String[] otherNutrients = new String[1];
        for(int i = 0; i < temp.length; i++){
            if(temp[i][0].equalsIgnoreCase(selectedDate)){
                otherNutrients = temp[i][type + 1].split("; ");
            }
        }
        for(int i = 0; i < otherNutrients.length; i++){

            otherNutrients[i] = otherNutrients[i] + "g";
        }

        return otherNutrients;
    }

    public double[] getTotalFoodGroupIntake(){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        return runtimeDatabase.getFoodGroup();
    }
    public static void displayDietChart(String startDate, String endDate){
        //call diet chart module. modify number of nutrition here
        DietDataVisualizer.getChart(5,startDate,endDate);//change here
    }

    public static void displayCalorieChart(String startDate, String endDate){
        //call calorie intake and burned chart module
        CalorieDataVisualizer.getChart(startDate,endDate);//change here
    }

    public static String getWeightForecast(String date) {
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


    public static void getCFGchart(double[] userIntake,double[] cfgRecommendations ){
        //change here
        userIntake = new double[]{30.0, 25.0, 20.0, 15.0, 10.0};
        cfgRecommendations = new double[]{30.0, 30.0, 20.0, 15.0, 5.0};

        CFG dietChart = new CFG("Diet Comparison Chart", userIntake, cfgRecommendations);
        dietChart.displayChart();
    }

    public void editProfile(String name, String gender, int parseInt, int parseInt1, int parseInt2, double parseDouble, double parseDouble1, String measurement) {
        //change here
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.modifyProfile(name, gender, parseInt, parseInt1, parseInt2, parseDouble, parseDouble1, measurement);
        System.out.println("editProfile called");
    }
}
