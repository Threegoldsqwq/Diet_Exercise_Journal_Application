package OutcomeGenerator;


import DatabaseOperation.RuntimeDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This class calculating data
 */
public class Calculator extends DataCalculator {


    /**
     * This class calculates the BMR of the user
     * @return the BMR value
     * @throws ParseException if date input is wrong
     */
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
            //calculate based on the measurement
            if (RuntimeDatabase.getInstance().getMeasurement().equalsIgnoreCase("Imperial")) {
                bmr = 66 + 6.23 * weight + 12.7 * height - 6.8 * age;
            } else {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5;
            }
        } else if (gender == 'F' || gender == 'f') {
            // BMR calculation for women
            if (RuntimeDatabase.getInstance().getMeasurement().equalsIgnoreCase("Imperial")) {
                bmr = 655 + 4.35 * weight + 4.7 * height - 4.7 * age;
            }
            else{
                bmr = 10 * weight + 6.25 * height - 5 * age - 161;
            }

        } else {
            throw new IllegalArgumentException("Invalid gender. Use 'M' for male or 'F' for female.");
        }

        //System.out.println("func bmr: " + bmr);
        return bmr;
    }

    /**
     * This method gets the calorie burnt per minute based on exercise type
     * The source coming from website (Citation: Whitney E, Rolfes SR Eds. Understanding Nutrition. 11th ed. Belmont, CA Thomson Higher Education; 2008, p.7.)
     * @param type is the exercise type
     * @return the calorie burnt per minute
     */
    public double getCalorieBurntPerMinute(String type){
        if(type.equalsIgnoreCase("Running")){
            return 0.074;
        }
        else if(type.equalsIgnoreCase("Swimming")){
            return 0.058;
        }
        else if(type.equalsIgnoreCase("Cycling")){
            return 0.076;
        }
        else if(type.equalsIgnoreCase("Walking")){
            return 0.048;
        }
        else if(type.equalsIgnoreCase("Weightlifting")){
            return 0.048;
        }
        else if(type.equalsIgnoreCase("Yoga")){
            return 0.062;
        }
        return 0.0;
    }

    /**
     * This method get the activity level base on intensity
     * The default value is 1 which is high intensity
     * @param intensity is the intensity
     * @return the activity level
     */
    public double getActivityLevel(String intensity){
        if(intensity.equalsIgnoreCase("very low")){
            return 0.1;
        }
        else if(intensity.equalsIgnoreCase("low")){
            return 0.3;
        }
        else if(intensity.equalsIgnoreCase("medium")){
            return 0.6;
        }
        else if(intensity.equalsIgnoreCase("high")){
            return 1;
        }
        else if(intensity.equalsIgnoreCase("very high")){
            return 1.3;
        }
        return 1.0;
    }


    /**
     * This method calculate the weight lose in the future
     * IMPORTANT: The formula is: (Average Daily Calorie Intake - Average Daily Calorie Burnt) * the amount of days till the future date(based on input) / 7700
     * @param userInputDate is the future date
     * @return the weight lost amount (or it is increase if you don't exercise XD)
     * @throws ParseException if date input is wrong
     */
    public static double weightForecast(String userInputDate) throws ParseException {
        double CalorieDeficit;
        int lengthDays;

        double avgCalorieIntake = getAvgCIntake();
        double avgCalorieBurned = getAvgCBurned();

        System.out.println(avgCalorieIntake);
        System.out.println(avgCalorieBurned);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Parse the user input date
        LocalDate selectedDate = LocalDate.parse(userInputDate, dateFormatter);

        // Get today's date
        LocalDate currentDate = LocalDate.now();
        lengthDays = (int) ChronoUnit.DAYS.between(currentDate, selectedDate);

        CalorieDeficit = avgCalorieIntake - avgCalorieBurned;
        System.out.println(CalorieDeficit);
        return CalorieDeficit * lengthDays / 7700;
    }

    /**
     * This method get the average calorie burnt
     * @return the average calorie burnt
     * @throws ParseException if date input is wrong
     */
    private static double getAvgCBurned() throws ParseException {
        //change here link to database
        String[][] helper= RuntimeDatabase.CaloryBurnedDataReader();
        double result = 0;
        for(int i=0;i < helper.length;i++){
            result= result+Double.parseDouble(helper[i][1]);
        }

        //RuntimeDatabase.getInstance().get
        return result / helper.length;
    }

    /**
     * This method get the average calorie intake
     * @return the average calorie intake
     */
    private static double getAvgCIntake() {
        //change here link to database
        String[][] helper = RuntimeDatabase.CaloryIntakeDataReader();
        double result = 0;
        for(int i=0;i < helper.length;i++){
            result= result+Double.parseDouble(helper[i][1]);
        }
        return result / helper.length;
    }
}
