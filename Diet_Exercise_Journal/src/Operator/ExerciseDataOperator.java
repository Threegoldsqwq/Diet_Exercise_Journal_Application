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
 * TBD
 */
public class ExerciseDataOperator implements DataOperator{
    public double calculateCalorieBurnt(String type, String duration, String intensity) throws ParseException {

        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        DataCalculator calculator = new Calculator();
        //RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        double activityLevel = calculator.getActivityLevel(intensity);//get activity level
//        //below calculating the age
//        LocalDate currentdate = LocalDate.now();
//        Date today = format.parse(currentdate.getYear() + "-" + currentdate.getMonth().getValue() + "-" + currentdate.getDayOfMonth());
//        //String[] ymd = runtimeDatabase.getDOB().split("-");
//        Date dob = format.parse(runtimeDatabase.getDOB());
//        long diffInMillies = Math.abs(today.getTime() - dob.getTime());
//        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) /365;
//        int age = (int) diff;
        //calculate BMR
        double bmr = Calculator.calculateBMR();
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
        return new String[0][];
    }
}
