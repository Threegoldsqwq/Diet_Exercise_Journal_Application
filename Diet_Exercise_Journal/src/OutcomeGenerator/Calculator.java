package OutcomeGenerator;

import DatabaseOperation.RuntimeDatabase;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This class is for calculating data
 */
public class Calculator extends DataCalculator {

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

    public static double calculateBMR() {
        // change here
        double bmr;
        String gender1 = RuntimeDatabase.getInstance().getSex();
        char gender = gender1.charAt(0);
        double weight = RuntimeDatabase.getInstance().getWeight();
        double height = RuntimeDatabase.getInstance().getHeight();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(RuntimeDatabase.getInstance().getDOB(), dateFormatter);
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Calculate the age
        Period age1 = Period.between(birthDate, currentDate);
        int age = age1.getYears();

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
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        } else {
            throw new IllegalArgumentException("Invalid gender. Use 'M' for male or 'F' for female.");
        }

        return bmr;
    }

    /**
     * This class calculate total calorie intake for each meal in the database
     * The calorie is in Cal
     * The data updates when the meal updates
     *
     * @param meal is the meal waiting to be calculated
     * @return a 2D array with the same size as the meals (meals of the user)
     */
    @Override
    public String[][] calculateCalorieInfo(String[][] meal) {

        String[][] calories = new String[meal.length][meal[0].length];//array with same size as meal
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();

        //we first retrieve within the meal first
        for (int i = 0; i < meal.length; i++) {
            calories[i][0] = meal[i][0];//the date keeps the same as meals
            for (int j = 1; j < meal[i].length; j++) {
                if (meal[i][j] == null) {
                    calories[i][j] = "0.00";//if no meals found, no calorie intake
                } else {
                    //we first split out the information of all ingredients and quantities, the format of result element are: ingredient, quantity
                    String[] ingredientsAndQuantity = ingredientsAndQuantity = meal[i][j].split(" - ");//get ingredient and quantity

                    String[] temp = new String[2];
                    double totalCalorie = 0.0; //total calorie per meal
                    for (int k = 0; k < ingredientsAndQuantity.length; k++) {
                        //we then split out ingredient and quantity and process them one by one
                        temp = ingredientsAndQuantity[k].split(", ");
                        double foodCal = runtimeDatabase.extractCalorieInfo(temp[0]);//extract the calorie info of the ingredient in cal/g or ml
                        totalCalorie = totalCalorie + (foodCal * Double.parseDouble(temp[1]));
                    }
                    calories[i][j] = String.valueOf(totalCalorie);//record the data into the array
                }
            }
        }
        return calories;
    }
}
