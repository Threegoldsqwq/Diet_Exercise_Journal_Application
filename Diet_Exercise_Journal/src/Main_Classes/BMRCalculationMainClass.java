package Main_Classes;

import Operator.DataOperator;
import Operator.DietDataOperator;

public class BMRCalculationMainClass {
    public static void main(String[] args) {

        DataOperator operator = new DietDataOperator();
        System.out.println(operator.calculateBMR(78,178,21,'M'));
    }
}
