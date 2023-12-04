package Operator;

import java.text.ParseException;

/**
 * Interface for
 */
public interface DataOperator {
    String[][] calculateCalorieInfo(String[][] meal);
    double calculateCalorieBurnt(String type, String intensity, String duration) throws ParseException;
}
