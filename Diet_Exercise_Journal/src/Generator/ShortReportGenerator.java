package Generator;

import DatabaseOperation.RuntimeDatabase;
import Visualizer.CalorieDataVisualizer;

/**
 * Class for generate short report
 */
public class ShortReportGenerator implements ReportGenerator{

    /**
     * This method get total calorie intake
     * @return a 2D array same as RuntimeDatabase
     */
    public String[][] getTotalCalorieIntake(){
        return CalorieDataVisualizer.CaloryIntakeDataReader();
    }

    @Override
    public String[] getIngredientsAndQuantity(int type, String selectedDate) {
        System.out.println("Used wrong method getIngredientsAndQuantity is in DetailedReportGenerator");
        return null;
    }

    @Override
    public String[] getOtherNutrients(int type, String selectedDate) {
        System.out.println("Used wrong method getOtherNutrients is in DetailedReportGenerator");
        return null;
    }
}
