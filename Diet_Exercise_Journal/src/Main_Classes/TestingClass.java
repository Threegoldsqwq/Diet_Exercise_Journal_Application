package Main_Classes;

import DatabaseOperation.RuntimeDatabase;

import java.sql.Date;

/**
 * This class for design main GUI
 * Not implemented GUI yet
 */
//测试用
public class TestingClass {
    public static void main(String[] args) throws Exception {

        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.displayDietData("breakfast", "2023-11-13", 100001);

        //runtimeDatabase.readDatabase();
        //runtimeDatabase.displayProfile();



//        System.out.println("Here are the profiles you have: ");
//        Scanner sc = new Scanner(System.in);
//
//        ProfileOperator profileOperator = new ProfileOperator();
//        profileOperator.displayProfile();
//        System.out.println("Choose a profile or you want to create a new one: choose the number, or 0 to create");
//        String input = sc.next();
//
//        String UserName;
//        String sex;
//        int year;
//        int month;
//        int day;
//        double height;
//        double weight;
//        String measurement;
//        //create profile
//        if(input.equals("0")){
//            System.out.println("Please enter the username: ");
//            UserName = sc.nextLine();
//            UserName += sc.nextLine();
//            System.out.println("Please enter your sex (Male/Female): ");
//            sex = sc.next();
//            System.out.println("Please enter your date of birth:\nEnter year: ");
//            year = sc.nextInt();
//            System.out.println("Enter month: ");
//            month = sc.nextInt();
//            System.out.println("Enter day: ");
//            day = sc.nextInt();
//            System.out.println("Please enter your height in meter (e.g. 1.83): ");
//            height = sc.nextDouble();
//            System.out.println("Please enter your weight in kg (e.g. 70): ");
//            weight = sc.nextDouble();
//            System.out.println("Please enter the measurement you want (metric/imperial): ");
//            measurement = sc.next();
//
//            profileOperator.createProfile(UserName, sex, year, month, day, height, weight, measurement);
//        }

    }
}