package Facade;

import DatabaseOperation.RuntimeDatabase;
import Generator.*;
import NewUi.ExericiseLogPage;
import Operator.*;
import OutcomeGenerator.*;
import Visualizer.*;

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
        DietDataVisualizer.getChart(5);
    }

    public static void displayCalorieChart(){
        //call calorie intake and burned chart module
        ExerciseDataVisualizer.getChart();
    }
}
