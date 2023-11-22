package Generator;

/**
 * TBD
 */
public interface ReportGenerator {


    public String[] getIngredientsAndQuantity(int type, String selectedDate);
    public String[] getOtherNutrients(int type, String selectedDate);

    public String[][] getTotalCalorieIntake();
}
