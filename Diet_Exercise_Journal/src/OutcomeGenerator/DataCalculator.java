package OutcomeGenerator;

import java.text.ParseException;

/**
 * TBD
 */
public abstract class DataCalculator {

    public static double calculateBMR() throws ParseException {
        return 0;
    }

    public abstract double getActivityLevel(String intensity);
}
