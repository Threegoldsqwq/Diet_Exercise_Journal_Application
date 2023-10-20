package GUI;

import DatabaseOperation.ProfileOperator;
import DatabaseOperation.RuntimeDatabase;

import java.util.Scanner;

/**
 * This class for design main GUI
 * Not implemented GUI yet
 */
public class MainUI {
    public static void main(String[] args) throws Exception {
        System.out.println("Here are the profiles you have: ");
        Scanner sc = new Scanner(System.in);

        ProfileOperator profileOperator = new ProfileOperator();
        profileOperator.displayProfile();
        System.out.println("Choose a profile or you want to create a new one: choose the number, or 0 to create");
        String input = sc.next();

        String UserName;
        String sex;
        int year;
        int month;
        int day;
        double height;
        double weight;
        String measurement;
        //create profile
        if(input.equals("0")){
            System.out.println();
            UserName = sc.next();
            System.out.println();
            sex = sc.next();
            System.out.println();
            year = sc.nextInt();
            System.out.println();
            month = sc.nextInt();
            System.out.println();
            day = sc.nextInt();
            System.out.println();
            height = sc.nextDouble();
            System.out.println();
            weight = sc.nextDouble();
            System.out.println();
            measurement = sc.next();

            profileOperator.createProfile(UserName, sex, year, month, day, height, weight, measurement);
        }

    }
}
