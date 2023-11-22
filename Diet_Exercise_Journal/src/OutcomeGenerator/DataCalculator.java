package OutcomeGenerator;

import java.text.ParseException;

/**
 * TBD
 */
public abstract class DataCalculator {

    public abstract double getActivityLevel(String intensity);
    public abstract double getCalorieBurntPerMinute(String type);
}
