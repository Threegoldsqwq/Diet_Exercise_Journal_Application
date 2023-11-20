package Main_Classes;

import DatabaseOperation.RuntimeDatabase;
import Operator.DataOperator;
import Operator.ExerciseDataOperator;
import OutcomeGenerator.Calculator;
import OutcomeGenerator.DataCalculator;

import java.sql.Date;
import java.util.ArrayList;

/**
 * This class for design main GUI
 * Not implemented GUI yet
 */
//测试用
public class TestingClass {
    public static void main(String[] args) throws Exception {

        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        //runtimeDatabase.displayDietData("breakfast", "2023-11-13", 100001);
        runtimeDatabase.setId(100001);
        String[][] s = runtimeDatabase.readAllMealInfo(100001);
        String[][] e = runtimeDatabase.readAllExerciseInfo(100001);
        runtimeDatabase.setExerciseInfo(e);
        runtimeDatabase.setMealInfo(s);
        String[][] s1 = runtimeDatabase.getExerciseInfo();
        String[][] s2 = runtimeDatabase.getCalorieInfo();
        String[][] s3 = RuntimeDatabase.CaloryIntakeDataReader();
        String[][] s4 = runtimeDatabase.getOtherNutrientInfo();
        String[][] s5 = RuntimeDatabase.CaloryBurnedDataReader();

        double[] s6 = runtimeDatabase.getFoodGroup();

        for (double value : s6) {
            System.out.println(value);
        }
//        ArrayList<String> ingredients = new ArrayList<>();
//        ingredients.add("cookie");
//        ingredients.add("rice");
//        ingredients.add("pork");
//        ArrayList<String> quantity = new ArrayList<>();
//        quantity.add("10");
//        quantity.add("11");
//        quantity.add("12");

//        s = runtimeDatabase.getMealInfo();

//        for (String[] strings : s1) {
//            for (String string : strings) {
//                System.out.print(string + " ");
//            }
//            System.out.println();
//        }
//        for (String[] strings : s5) {
//            for (String string : strings) {
//                System.out.print(string + " ");
//            }
//            System.out.println();
//        }
        //runtimeDatabase.writeExerciseBack();
        //System.out.println(runtimeDatabase.getOtherNutrientValues("tomato, 11 - beef, 23"));


        String[][] testMeal = {{"2023-11-13", "tomato, 11", "tomato, 11", "tomato, 11", "tomato, 11"},
                {"2023-11-14", "potato, 11 - tomato, 20", "tomato, 11 - beef, 23", "pork, 20 - chicken, 30 - fruit salad, 40", "tomato, 13 - potato, 13"}};
        String[][] testCal = {{"2023-11-13", "1.87", "1.87", "1.87", "1.87"},
                {"2023-11-14", "11.870000000000001", "74.78", "164.5", "12.219999999999999" }};
//        double c = runtimeDatabase.readCalorieInfo("MILK");
//        System.out.println(c);

        //runtimeDatabase.readDatabase();
        //runtimeDatabase.displayProfile();

//        DataOperator operator = new ExerciseDataOperator();
//        System.out.println(operator.calculateCalorieBurnt("very high"));

    }
}
