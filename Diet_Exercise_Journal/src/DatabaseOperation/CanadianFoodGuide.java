package DatabaseOperation;

public class CanadianFoodGuide {
    public static void main(String[] args) {
        // User information
        String userGender = "Male"; // Replace with actual user gender
        double userWeightInKg = 70.0; // Replace with actual user weight in kg
        double userWeightInLb = 154.0; // Replace with actual user weight in pounds

        // Get recommended quantities
        double[] recommendedQuantities = getRecommendedQuantities(userGender, userWeightInKg, userWeightInLb);

        // Print user information
        System.out.println("User Information:");
        System.out.println("Gender: " + userGender);
        System.out.println("Weight: " + userWeightInKg + " kg");

        // Print CFG information
        printCFGInformation(recommendedQuantities);
    }

    // Function to calculate recommended quantities based on gender and weight
    private static double[] getRecommendedQuantities(String userGender, double userWeightInKg, double userWeightInLb) {
        // Daily intake factors based on dietary guidelines
        double[] femaleIntakeFactors = {5.0, 1.5, 3.0, 2.0, 1.0}; // Arbitrary values for the example
        double[] maleIntakeFactors = {6.0, 1.8, 3.5, 2.5, 1.0}; // Arbitrary values for the example

        // Determine the appropriate intake factors based on user's gender
        double[] intakeFactors;
        if ("Female".equalsIgnoreCase(userGender)) {
            intakeFactors = femaleIntakeFactors;
        } else if ("Male".equalsIgnoreCase(userGender)) {
            intakeFactors = maleIntakeFactors;
        } else {
            System.out.println("Invalid gender");
            return new double[0];
        }

        // Convert weight to kg if it is provided in pounds
        double userWeight;
        if (userWeightInLb > 0) {
            userWeight = userWeightInLb * 0.453592; // 1 lb = 0.453592 kg
        } else {
            userWeight = userWeightInKg;
        }

        // Calculate recommended quantities based on gender, weight, and intake factors
        double[] recommendedQuantities = new double[intakeFactors.length];
        for (int i = 0; i < intakeFactors.length; i++) {
            recommendedQuantities[i] = intakeFactors[i] * userWeight;
        }

        return recommendedQuantities;
    }

    // Function to print CFG information
    private static void printCFGInformation(double[] recommendedQuantities) {
        // Create an array to store CFG information in g/ml
        String[] foodGroups = {
                "Dairy and Egg Products", "Spices and Herbs", "Fats and Oils", "Vegetables and Vegetable Products", "Baked Products"
        };

        // Print CFG information
        System.out.println("\nCanadian Food Guide Information (in g/ml):");
        for (int i = 0; i < foodGroups.length; i++) {
            System.out.println(foodGroups[i] + ": " + recommendedQuantities[i] + " g/ml per day");
        }
    }
}
