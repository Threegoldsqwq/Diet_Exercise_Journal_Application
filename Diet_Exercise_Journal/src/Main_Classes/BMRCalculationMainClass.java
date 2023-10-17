package Main_Classes;

import Operator.DataOperator;
import Operator.DietDataOperator;
import OutcomeGenerator.Calculator;
import OutcomeGenerator.DataCalculator;

public class BMRCalculationMainClass {
    public static void main(String[] args) {

        DataCalculator calculator = new Calculator();
        System.out.println(calculator.calculateBMR(78,178,21,'M'));
    }
}
