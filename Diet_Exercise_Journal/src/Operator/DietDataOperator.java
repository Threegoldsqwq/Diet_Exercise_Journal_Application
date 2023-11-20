package Operator;
import OutcomeGenerator.Calculator;
import OutcomeGenerator.DataCalculator;
import DatabaseOperation.RuntimeDatabase;

import java.util.Date;

/**
 * This class is for input
 */
public class DietDataOperator implements DataOperator{

    /**
     * This class calculate total calorie intake for each meal in the database
     * The calorie is in Cal
     * The data updates when the meal updates
     * @param meal is the meal waiting to be calculated
     * @return a 2D array with the same size as the meals (meals of the user)
     */
    @Override
    public String[][] calculateCalorieInfo(String[][] meal){

        String[][] calories = new String[meal.length][meal[0].length];//array with same size as meal
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();

        //we first retrieve within the meal first
        for(int i = 0; i < meal.length; i++){
            calories[i][0] = meal[i][0];//the date keeps the same as meals
            for(int j = 1; j < meal[i].length; j++){
                if(meal[i][j] == null || meal[i][j].equalsIgnoreCase("")){
                    calories[i][j] = "0.00";//if no meals found, no calorie intake

                }
                else{
                    //we first split out the information of all ingredients and quantities, the format of result element are: ingredient, quantity
                    String[] ingredientsAndQuantity = meal[i][j].split(" - ");//get ingredient and quantity
                    double totalCalorie = 0.0; //total calorie per meal
                    for(int k = 0; k < ingredientsAndQuantity.length; k++){
                        //we then split out ingredient and quantity and process them one by one
                        String[] temp = ingredientsAndQuantity[k].split(", ");
                        double foodCal = runtimeDatabase.extractCalorieInfo(temp[0]);//extract the calorie info of the ingredient in cal/g or ml
                        totalCalorie = totalCalorie + (foodCal * Double.parseDouble(temp[1]));
                    }
                    calories[i][j] = String.valueOf(totalCalorie);//record the data into the array
                }
            }
        }
        return calories;
    }

    @Override
    public double calculateCalorieBurnt(String intensity) {
        return 0;
    }

}
