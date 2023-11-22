package Operator;

import java.text.ParseException;

/**
 * Interface for
 */
public interface DataOperator {
    public String[][] calculateCalorieInfo(String[][] meal);
    public double calculateCalorieBurnt(String type, String intensity, String duration) throws ParseException;
}
