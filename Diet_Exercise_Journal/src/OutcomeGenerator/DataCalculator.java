package OutcomeGenerator;

import java.text.ParseException;

/**
 * Abstract class from encapsulation
 */
public abstract class DataCalculator {

    public abstract double getActivityLevel(String intensity);
    public abstract double getCalorieBurntPerMinute(String type);
}
