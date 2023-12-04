package Generator;

import DatabaseOperation.RuntimeDatabase;

/**
 * Class for generate detailed report
 */
public class DetailedReportGenerator implements ReportGenerator{

    private void deepCopyInfo(String[][] temp, String type){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        if(type.equalsIgnoreCase("mealInfo")){
            for(int i = 0; i < temp.length; i++){
                for(int j = 0; j < temp[i].length; j++){
                    temp[i][j] = runtimeDatabase.getMealInfo()[i][j];
                }
            }
        }
        else if(type.equalsIgnoreCase("otherNutrients")){
            for(int i = 0; i < temp.length; i++){
                for(int j = 0; j < temp[i].length; j++){
                    temp[i][j] = runtimeDatabase.getOtherNutrientInfo()[i][j];
                }
            }
        }

    }
    private void formResult(String[] s, String type){
        if(type.equalsIgnoreCase("mealInfo")){
            for(int i = 0; i < s.length; i++){
                if(s[i].charAt(0) == 'e'){
                    s[i] = s[i];
                }
                else{
                    s[i] = s[i] + "g/ml";
                }
            }
        }
        else if(type.equalsIgnoreCase("otherNutrients")){
            for(int i = 0; i < s.length; i++){

                s[i] = s[i] + "g";
            }
        }
    }

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
        deepCopyInfo(temp, "mealInfo");

        String[] ingredientsQuantity = new String[1];
        for(int i = 0; i < temp.length; i++){
            if(temp[i][0].equalsIgnoreCase(selectedDate)){
                ingredientsQuantity = temp[i][type + 1].split(" - ");
            }
        }

        //form result
        formResult(ingredientsQuantity, "mealInfo");

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
        deepCopyInfo(temp, "otherNutrients");

        String[] otherNutrients = new String[1];
        for(int i = 0; i < temp.length; i++){
            if(temp[i][0].equalsIgnoreCase(selectedDate)){
                otherNutrients = temp[i][type + 1].split("; ");
            }
        }

        //form result
        formResult(otherNutrients, "otherNutrients");

        return otherNutrients;
    }

}
