package OutcomeGenerator;


import DatabaseOperation.RuntimeDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This class is for calculating data
 */
public class Calculator extends DataCalculator {

    /**
     * This method calculate the BMR
     * @param weightKg is the weight in kg
     * @param heightCm is the height in cm
     * @param age is the age
     * @param gender is the gender
     * @return the BMR
     */
    @Override
    public double calculateBMR(double weightKg, double heightCm, int age, String gender) {
        double bmr;

        //calculate BMR based on gender
        if (gender.equalsIgnoreCase("Male")) {
            // BMR calculation for men
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age + 5;
        } else if (gender.equalsIgnoreCase("Female")) {
            // BMR calculation for women
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age - 161;
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


    public static double weightForecast(String userInputDate){
        double CalorieDeficit;
        int lengthDays;

        double avgCalorieIntake=getAvgCIntake();
        double avgCalorieBurned=getAvgCBurned();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Parse the user input date
        LocalDate selectedDate = LocalDate.parse(userInputDate, dateFormatter);

        // Get today's date
        LocalDate currentDate = LocalDate.now();
        lengthDays= (int) ChronoUnit.DAYS.between(currentDate, selectedDate);

        CalorieDeficit=avgCalorieIntake-avgCalorieBurned;
        return CalorieDeficit*lengthDays/7700;
    }

    private static double getAvgCBurned() {
        //change here link to database
        return 17600;
    }

    private static double getAvgCIntake() {
        //change here link to database
        return 16000;
    }
}
