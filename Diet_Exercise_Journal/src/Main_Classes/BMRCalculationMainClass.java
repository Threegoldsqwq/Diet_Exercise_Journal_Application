package Main_Classes;

import Operator.DietDataOperator;

public class BMRCalculationMainClass {
    public static void main(String[] args) {
        System.out.println(DietDataOperator.calculateBMR(78,178,21,'M'));
    }
}
