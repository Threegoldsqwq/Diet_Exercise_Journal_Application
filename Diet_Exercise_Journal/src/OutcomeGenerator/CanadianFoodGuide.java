package OutcomeGenerator;

import Facade.NutritionServiceFacade;

public class CanadianFoodGuide {
    public static double[] getCFGRecommends(double[] meal){
        NutritionServiceFacade facade = new NutritionServiceFacade();
        double total = 0;
        double[] recommend = new double[3];
        for(int i = 0; i < facade.getTotalFoodGroupIntake().length; i++){
            total = total + facade.getTotalFoodGroupIntake()[i];
        }
        recommend[0] = total / 2;
        recommend[1] = total / 4;
        recommend[2] = total / 4;
        return recommend;
    }

    // Function to calculate recommended quantities based on gender and weight
//    private static double[] getRecommendedQuantities(String userGender, double userWeightInKg, double userWeightInLb) {
//        // Daily intake factors based on dietary guidelines
//        double[] femaleIntakeFactors = {5.0, 1.5, 3.0, 2.0, 1.0}; // Arbitrary values for the example
//        double[] maleIntakeFactors = {6.0, 1.8, 3.5, 2.5, 1.0}; // Arbitrary values for the example
//
//        // Determine the appropriate intake factors based on user's gender
//        double[] intakeFactors;
//        if ("Female".equalsIgnoreCase(userGender)) {
//            intakeFactors = femaleIntakeFactors;
//        } else if ("Male".equalsIgnoreCase(userGender)) {
//            intakeFactors = maleIntakeFactors;
//        } else {
//            System.out.println("Invalid gender");
//            return new double[0];
//        }
//
//        // Convert weight to kg if it is provided in pounds
//        double userWeight;
//        if (userWeightInLb > 0) {
//            userWeight = userWeightInLb * 0.453592; // 1 lb = 0.453592 kg
//        } else {
//            userWeight = userWeightInKg;
//        }
//
//        // Calculate recommended quantities based on gender, weight, and intake factors
//        double[] recommendedQuantities = new double[intakeFactors.length];
//        for (int i = 0; i < intakeFactors.length; i++) {
//            recommendedQuantities[i] = intakeFactors[i] * userWeight;
//        }
//
//        return recommendedQuantities;
//    }

    // Function to print CFG information
    private static void printCFGInformation(double[] recommendedQuantities) {
        // Create an array to store CFG information in g/ml
        String[] foodGroups = {"Vegetables", "Protein", "Whole Grain"};

        // Print CFG information
        System.out.println("\nCanadian Food Guide Information (in g/ml):");
        for (int i = 0; i < foodGroups.length; i++) {
            System.out.println(foodGroups[i] + ": " + recommendedQuantities[i] + " g/ml per day");
        }
    }
}
