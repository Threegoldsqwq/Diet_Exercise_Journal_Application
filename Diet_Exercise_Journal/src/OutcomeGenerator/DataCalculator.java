package OutcomeGenerator;

/**
 * TBD
 */
public abstract class DataCalculator {

    public static double calculateBMR() {
        return 0;
    }


    public abstract double calculateBMR(double weightKg, double heightCm, int age, char gender);

    public String[][] calculateCalorieInfo(String[][] meal) {
        return new String[0][];
    }
}
