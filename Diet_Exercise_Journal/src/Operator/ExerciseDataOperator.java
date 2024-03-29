package Operator;

import DatabaseOperation.RuntimeDatabase;
import OutcomeGenerator.Calculator;

import java.text.ParseException;
/**
 * This class handles data from the exercise
 */
public class ExerciseDataOperator implements DataOperator{

    /**
     * This method calculate the calorie burnt from the exercise
     * IMPORTANT: THE FORMULA is: weight (in pound) * ActivityLevel(base on intensity) * CalorieBurnt/min(based on exercise type) * duration in minute
     * @param type is the exercise type, NOTE: we have 6 exercises in our database
     * @param duration is the time in minute
     * @param intensity is the intensity in low, medium, high
     * @return the calorie burnt after that exercise
     * @throws ParseException if date input is wrong
     */
    public double calculateCalorieBurnt(String type, String duration, String intensity) throws ParseException {

        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        Calculator calculator = new Calculator();

        double activityLevel = calculator.getActivityLevel(intensity);//get activity level

        //unit change
        if(runtimeDatabase.getMeasurement().equalsIgnoreCase("Metric")){
            return runtimeDatabase.getWeight() * 2.204 * calculator.getActivityLevel(intensity) * calculator.getCalorieBurntPerMinute(type) * Double.parseDouble(duration);
        }
        else {
            return runtimeDatabase.getWeight() * calculator.getActivityLevel(intensity) * calculator.getCalorieBurntPerMinute(type) * Double.parseDouble(duration);
        }
    }


    @Override
    public String[][] calculateCalorieInfo(String[][] meal) {
        System.out.println("Used wrong method calculateCalorieInfo() is in DietDataOperator");
        return null;
    }
}
