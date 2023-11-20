package OutcomeGenerator;


import DatabaseOperation.RuntimeDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This class is for calculating data
 */
public class Calculator extends DataCalculator {


    public static double calculateBMR() throws ParseException {
        // change here
        double bmr;
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        String gender1 = RuntimeDatabase.getInstance().getSex();
        char gender = gender1.charAt(0);
        double weight = RuntimeDatabase.getInstance().getWeight();
        double height = RuntimeDatabase.getInstance().getHeight();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // Calculate the age
        LocalDate currentdate = LocalDate.now();
        Date today = format.parse(currentdate.getYear() + "-" + currentdate.getMonth().getValue() + "-" + currentdate.getDayOfMonth());
        //String[] ymd = runtimeDatabase.getDOB().split("-");
        Date dob = format.parse(runtimeDatabase.getDOB());
        long diffInMillies = Math.abs(today.getTime() - dob.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) /365;
        int age = (int) diff;

        //calculate BMR based on gender
        if (gender == 'M' || gender == 'm') {
            // BMR calculation for men
            if (RuntimeDatabase.getInstance().getMeasurement() == "Imperial") {
                bmr = 66 + 6.23 * weight + 12.7 * height - 6.8 * age;
            } else {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5;
            }
        } else if (gender == 'F' || gender == 'f') {
            // BMR calculation for women
            if (RuntimeDatabase.getInstance().getMeasurement() == "Imperial") {
                bmr = 655 + 4.35 * weight + 4.7 * height - 4.7 * age;
            }
            else{
                bmr = 10 * weight + 6.25 * height - 5 * age - 161;
            }

        } else {
            throw new IllegalArgumentException("Invalid gender. Use 'M' for male or 'F' for female.");
        }

        return bmr;
    }

    public double getActivityLevel(String intensity){
        if(intensity.equalsIgnoreCase("very low")){
            return 1.2;
        }
        else if(intensity.equalsIgnoreCase("low")){
            return 1.375;
        }
        else if(intensity.equalsIgnoreCase("medium")){
            return 1.55;
        }
        else if(intensity.equalsIgnoreCase("high")){
            return 1.725;
        }
        else if(intensity.equalsIgnoreCase("very high")){
            return 1.9;
        }
        return 0.0;
    }


    public static double weightForecast(String userInputDate) {
        double CalorieDeficit;
        int lengthDays;

        double avgCalorieIntake = getAvgCIntake();
        double avgCalorieBurned = getAvgCBurned();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Parse the user input date
        LocalDate selectedDate = LocalDate.parse(userInputDate, dateFormatter);

        // Get today's date
        LocalDate currentDate = LocalDate.now();
        lengthDays = (int) ChronoUnit.DAYS.between(currentDate, selectedDate);

        CalorieDeficit = avgCalorieIntake - avgCalorieBurned;
        return CalorieDeficit * lengthDays / 7700;
    }

    private static double getAvgCBurned() {
        //change here link to database
        String[][] helper= RuntimeDatabase.CaloryBurnedDataReader();
        double result = 0;
        for(int i=0;i<helper.length-1;i++){
            result= result+Double.parseDouble(helper[i][1]);
        }
        //RuntimeDatabase.getInstance().get
        return result;
    }

    private static double getAvgCIntake() {
        //change here link to database
        String[][] helper= RuntimeDatabase.CaloryIntakeDataReader();
        double result = 0;
        for(int i=0;i<helper.length-1;i++){
            result= result+Double.parseDouble(helper[i][1]);
        }
        return result;
    }
}
