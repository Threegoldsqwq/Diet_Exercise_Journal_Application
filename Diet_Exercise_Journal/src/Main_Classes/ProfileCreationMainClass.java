package Main_Classes;

import java.util.Scanner;

/**
 * Testing main classes for you to run
 * Will be changed to real implementation in the future
 * Will implement real JUnit test cases in the future
 */
public class ProfileCreationMainClass {
    public static void main(String[] args) {
        //System.out.println("Here are the profiles you have: ");
        Scanner sc = new Scanner(System.in);
        //DatabaseOperation.ProfileOperator profileOperator = new DatabaseOperation.ProfileOperator();
        //profileOperator.displayProfile();
        System.out.println("Choose a profile or you want to create a new one: choose the number, or 0 to create");
        System.out.println("assume you chose 0");
        String input = "0";

        String UserName;
        String sex;
        int year;
        int month;
        int day;
        double height;
        double weight;
        String measurement;
        //create profile
        System.out.println("Please enter the username: ");
        UserName = sc.nextLine();
        UserName += sc.nextLine();
        //This could be replaced to selection bar
        System.out.println("Please enter your sex (Male/Female): ");
        sex = sc.next();
        System.out.println("Please enter your date of birth: ");
        System.out.println("Enter year: ");
        year = sc.nextInt();
        System.out.println("Enter month: ");
        month = sc.nextInt();
        System.out.println("Enter day: ");
        day = sc.nextInt();
        System.out.println("Please enter your height in meter (e.g. 1.83): ");
        height = sc.nextDouble();
        System.out.println("Please enter your weight in kg (e.g. 70): ");
        weight = sc.nextDouble();
        //This could be replaced to selection bar
        System.out.println("Please enter the measurement you want (metric/imperial): ");
        measurement = sc.next();

        System.out.println("Profile create successfully");
        System.out.println("UserID：will be determined by the database" );
        System.out.println("UserName：" + UserName);
        System.out.println("Sex：" + sex);
        System.out.println("DOB：" + year + "-" + month + "-" + day);
        System.out.println("Height：" + height + " m");
        System.out.println("Weight：" + weight + " kg");
        System.out.println("Measurement：" + measurement);
        System.out.println("-----------------------------------------------");
        //profileOperator.createProfile(UserName, sex, year, month, day, height, weight, measurement);
    }
}
