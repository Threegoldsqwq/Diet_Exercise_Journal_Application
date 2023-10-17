package OutcomeGenerator;

public class Calculator implements DataCalculator{
    @Override
    public double calculateBMR(double weightKg, double heightCm, int age, char gender) {
        double bmr;

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
