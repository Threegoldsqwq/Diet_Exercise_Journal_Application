package Generator;

/**
 * TBD
 */
public interface ReportGenerator {


    String[] getIngredientsAndQuantity(int type, String selectedDate);
    String[] getOtherNutrients(int type, String selectedDate);

    String[][] getTotalCalorieIntake();
}
