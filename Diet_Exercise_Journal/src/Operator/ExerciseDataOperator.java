package Operator;

import DatabaseOperation.RuntimeDatabase;
import OutcomeGenerator.Calculator;
import OutcomeGenerator.DataCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        DataCalculator calculator = new Calculator();

        double activityLevel = calculator.getActivityLevel(intensity);//get activity level

        //unit change
        if(runtimeDatabase.getMeasurement().equalsIgnoreCase("Metric")){
            return runtimeDatabase.getWeight() * 2.204 * calculator.getActivityLevel(intensity) * calculator.getCalorieBurntPerMinute(type) * Double.parseDouble(duration);
        }
        else {
            return runtimeDatabase.getWeight() * calculator.getActivityLevel(intensity) * calculator.getCalorieBurntPerMinute(type) * Double.parseDouble(duration);
        }
        //System.out.println(bmr);
    }


    @Override
    public String[][] calculateCalorieInfo(String[][] meal) {
        System.out.println("Used wrong method calculateCalorieInfo() is in DietDataOperator");
        return null;
    }
}
