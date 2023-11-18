package Facade;

import DatabaseOperation.RuntimeDatabase;
import Generator.*;
import NewUi.ExericiseLogPage;
import Operator.*;
import OutcomeGenerator.*;
import Visualizer.*;

import java.text.DecimalFormat;

/**
 * This class is the facade for user to use
 */
public class NutritionServiceFacade {

    public NutritionServiceFacade(){}
    public void displayDiet(int userID){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.setId(userID);
        try {
            //runtimeDatabase.displayDietData(runtimeDatabase.getId());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void displayDietChart(){
        //call diet chart module. modify number of nutrition here
        DietDataVisualizer.getChart(5);//change here
    }

    public static void displayCalorieChart(){
        //call calorie intake and burned chart module
        ExerciseDataVisualizer.getChart();//change here
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
}
