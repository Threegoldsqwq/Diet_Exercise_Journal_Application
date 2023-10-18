package Main_Classes;

/**
 * Testing main classes for you to run
 * Will be changed to real implementation in the future
 * Will implement real JUnit test cases in the future
 */
public class MealLogMainClass {
    public static void main(String[] args) {

        System.out.println("What date is your meal?");
        String date = "2023-10-15";
        System.out.println("What meal is it? (breakfast/lunch/dinner/snack)");
        System.out.println("What ingredients you have for your meal?");
        System.out.println("How many quantity in gram you used for that ingredient?\n");
        String breakfast = "breakfast", ingredients = "tomato, potato, bread";
        String[] breakfastIngredients = ingredients.split(", ");
        double[] breakfastQuantity = {1.1, 1.2, 1.3};
        String lunch = "lunch";
        ingredients = "orange, banana";
        String[] lunchIngredients = ingredients.split(", ");
        double[] lunchQuantity = {1.2, 1.3};
        String dinner = "dinner";
        ingredients = "grape";
        String[] dinnerIngredients = ingredients.split(", ");
        double[] dinnerQuantity = {2};

        System.out.println("Meal data log successfully");
        System.out.println("Log date: " + date);
        System.out.println("Meal: " + breakfast);
        System.out.print("Ingredient and quantity: ");
        for (int i = 0; i < breakfastIngredients.length; i++){
            if(i == breakfastIngredients.length - 1){
                System.out.println(breakfastIngredients[i] + "(" + breakfastQuantity[i] + "g)");
            }
            else{
                System.out.print(breakfastIngredients[i] + "(" + breakfastQuantity[i] + "g), ");
            }
        }
        System.out.println("Total Caloric: will be calculated by the database");

        System.out.println("\nMeal: " + lunch);
        System.out.print("Ingredient and quantity: ");
        for (int i = 0; i < lunchIngredients.length; i++){
            if(i == lunchIngredients.length - 1){
                System.out.println(lunchIngredients[i] + "(" + lunchQuantity[i] + "g)");
            }
            else{
                System.out.print(lunchIngredients[i] + "(" + lunchQuantity[i] + "g), ");
            }
        }
        System.out.println("Total Caloric: will be calculated by the database");

        System.out.println("\nMeal: " + dinner);
        System.out.print("Ingredient and quantity: ");
        for (int i = 0; i < dinnerIngredients.length; i++){
            if(i == dinnerIngredients.length - 1){
                System.out.println(dinnerIngredients[i] + "(" + dinnerQuantity[i] + "g)");
            }
            else{
                System.out.print(dinnerIngredients[i] + "(" + dinnerQuantity[i] + "g), ");
            }
        }
        System.out.println("Total Caloric: will be calculated by the database");

    }
}
