package Generator;

import DatabaseOperation.RuntimeDatabase;

/**
 * Class for generate detailed report
 */
public class DetailedReportGenerator implements ReportGenerator{

    /**
     * This method get the ingredients and quantity for each meal for detailed meal page
     * @param type is the meal type
     * @param selectedDate is the selected date
     * @return an array in {ingredients, quantities in g/ml}
     */
    public String[] getIngredientsAndQuantity(int type, String selectedDate){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        String[][] temp = new String[runtimeDatabase.getMealInfo().length][runtimeDatabase.getMealInfo()[0].length];
        //deep copy the array
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[i].length; j++){
                temp[i][j] = runtimeDatabase.getMealInfo()[i][j];
            }
        }

        String[] ingredientsQuantity = new String[1];
        for(int i = 0; i < temp.length; i++){
            if(temp[i][0].equalsIgnoreCase(selectedDate)){
                ingredientsQuantity = temp[i][type + 1].split(" - ");
            }
        }
        for(int i = 0; i < ingredientsQuantity.length; i++){
            if(ingredientsQuantity[i].charAt(0) == 'e'){
                ingredientsQuantity[i] = ingredientsQuantity[i];
            }
            else{
                ingredientsQuantity[i] = ingredientsQuantity[i] + "g/ml";
            }
        }

        return ingredientsQuantity;
    }

    /**
     * This method get the other nutrients for each meal for detailed meal page
     * @param type is the meal type
     * @param selectedDate is the selected date
     * @return an array in {other nutrients, values in g}
     */
    public String[] getOtherNutrients(int type, String selectedDate){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        String[][] temp = new String[runtimeDatabase.getOtherNutrientInfo().length][runtimeDatabase.getOtherNutrientInfo()[0].length];
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[i].length; j++){
                temp[i][j] = runtimeDatabase.getOtherNutrientInfo()[i][j];
            }
        }


        String[] otherNutrients = new String[1];
        for(int i = 0; i < temp.length; i++){
            if(temp[i][0].equalsIgnoreCase(selectedDate)){
                otherNutrients = temp[i][type + 1].split("; ");
            }
        }
        for(int i = 0; i < otherNutrients.length; i++){

            otherNutrients[i] = otherNutrients[i] + "g";
        }

        return otherNutrients;
    }

    @Override
    public String[][] getTotalCalorieIntake() {
        return new String[0][];
    }
}
