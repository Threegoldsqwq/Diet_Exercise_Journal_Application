package Main_Classes;

public class ExerciseLogMainClass {
    public static void main(String[] args) {
        System.out.println("Enter the date of you exercise ");
        String date = "2023-10-15";
        System.out.println("What type of exercise did you do?");
        String exercise = "running";
        System.out.println("How long did you exercise (in minute)?");
        double duration = 90;
        System.out.println("What is the intensity?\n");
        String intensity = "medium";
        System.out.println("Exercise logged successfully");
        System.out.println("Date: " + date);
        System.out.println("Type: " + exercise);
        System.out.println("Duration: " + duration + " min");
        System.out.println("Intensity: " + intensity);
        System.out.println("Caloric burnt: will be calculated soon");
    }
}
