package OutcomeGenerator;

import java.text.ParseException;

/**
 * Abstract class from encapsulation
 */
interface DataCalculator {
    double getActivityLevel(String intensity);
    double getCalorieBurntPerMinute(String type);
}
