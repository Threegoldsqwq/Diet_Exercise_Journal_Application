package OutcomeGenerator;


/**
 * This class is for calculating data
 */
public class Calculator implements DataCalculator{

    /**
     * This method calculate the BMR
     * @param weightKg is the weight in kg
     * @param heightCm is the height in cm
     * @param age is the age
     * @param gender is the gender
     * @return the BMR
     */
    @Override
    public double calculateBMR(double weightKg, double heightCm, int age, char gender) {
        double bmr;

        //calculate BMR based on gender
        if (gender == 'M') {
            // BMR calculation for men
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age + 5;
        } else if (gender == 'F') {
            // BMR calculation for women
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age - 161;
        } else {
            throw new IllegalArgumentException("Invalid gender. Use 'M' for male or 'F' for female.");
        }

        return bmr;
    }
}
