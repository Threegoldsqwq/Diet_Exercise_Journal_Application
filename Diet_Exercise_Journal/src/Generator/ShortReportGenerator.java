package Generator;

import DatabaseOperation.RuntimeDatabase;

/**
 * Class for generate short report
 */
public class ShortReportGenerator implements ReportGenerator{

    /**
     * This method get total calorie intake
     * @return a 2D array same as RuntimeDatabase
     */
    public String[][] getTotalCalorieIntake(){
        return RuntimeDatabase.CaloryIntakeDataReader();
    }

    @Override
    public String[] getIngredientsAndQuantity(int type, String selectedDate) {
        return new String[0];
    }

    @Override
    public String[] getOtherNutrients(int type, String selectedDate) {
        return new String[0];
    }
}
