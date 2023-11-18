package OutcomeGenerator;


import DatabaseOperation.RuntimeDatabase;

/**
 * This class is for calculating data
 */
public class Calculator implements DataCalculator{

    /**
     * This method calculate the BMR
     * @param weightKg is the weight in kg
     * @param heightCm is the height in cm
     * @param age is the age
     * @param gender is the gender
     * @return the BMR
     */
    @Override
    public double calculateBMR(double weightKg, double heightCm, int age, char gender) {
        double bmr;

        //calculate BMR based on gender
        if (gender == 'M') {
            // BMR calculation for men
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age + 5;
        } else if (gender == 'F') {
            // BMR calculation for women
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age - 161;
        } else {
            throw new IllegalArgumentException("Invalid gender. Use 'M' for male or 'F' for female.");
        }

        return bmr;
    }

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
                if(meal[i][j] == null){
                    calories[i][j] = "0.00";//if no meals found, no calorie intake
                }
                else{
                    //we first split out the information of all ingredients and quantities, the format of result element are: ingredient, quantity
                    String[] ingredientsAndQuantity = ingredientsAndQuantity = meal[i][j].split(" - ");//get ingredient and quantity

                    String[] temp = new String[2];
                    double totalCalorie = 0.0; //total calorie per meal
                    for(int k = 0; k < ingredientsAndQuantity.length; k++){
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
