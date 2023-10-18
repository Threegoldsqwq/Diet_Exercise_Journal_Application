package Main_Classes;

import Operator.DataOperator;
import Operator.DietDataOperator;
import OutcomeGenerator.Calculator;
import OutcomeGenerator.DataCalculator;

/**
 * Testing main classes for you to run
 * Will be changed to real implementation in the future
 * Will implement real JUnit test cases in the future
 */
public class BMRCalculationMainClass {
    public static void main(String[] args) {

        DataCalculator calculator = new Calculator();
        System.out.println(calculator.calculateBMR(78,178,21,'M'));
    }
}
