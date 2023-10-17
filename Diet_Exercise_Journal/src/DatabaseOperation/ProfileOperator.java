package DatabaseOperation;

import java.sql.*;

/**
 * This class manage the profile
 * create and display
 */
public class ProfileOperator {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private static int id = 100001;

//    private String UserName;
//    private String sex;
//    private int year, month, day;
//    private double height;
//    private double weight;
//    private String measurement;


    //创建profile
    public void createProfile(String UserName, String sex, int year, int month, int day, double height, double weight, String measurement) throws Exception{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Diet_Exercise_Journal_UserProfile.UserProfile");

            //以下为创建profile
            preparedStatement = connect.prepareStatement("insert into Diet_Exercise_Journal_UserProfile.UserProfile values (default, ?, ?, ?, ?, ?, ?)");
            //preparedStatement.setInt(1, ++id);
            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, sex);
            preparedStatement.setString(3, year + "-" + month + "-" + day);
            preparedStatement.setDouble(4, height);
            preparedStatement.setDouble(5, weight);
            preparedStatement.setString(6, measurement);//metric vs imperial
            preparedStatement.executeUpdate();

            preparedStatement = connect.prepareStatement("SELECT UserID, UserName, Sex, Date_of_birth, Height, Weight, Measurement from Diet_Exercise_Journal_UserProfile.UserProfile");
            resultSet = preparedStatement.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    //only display username and id
    public void displayProfile() throws Exception{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Diet_Exercise_Journal_UserProfile.UserProfile");

            int i = 1;
            while(resultSet.next()){
                System.out.println(i + ": " + resultSet.getString("UserName") + " (id: " + resultSet.getInt(1) + ")");
                i++;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//不要管这个
//以下为创建profile
//            preparedStatement = connect.prepareStatement("insert into Diet_Exercise_Journal_UserProfile.UserProfile values (?, ?, ?, ?, ?, ?, ?)");
//            preparedStatement.setInt(1, 100001);
//            preparedStatement.setString(2, "User-1");
//            preparedStatement.setString(3, "Male");
//            preparedStatement.setDate(4, new java.sql.Date(2003, 10, 22));
//            preparedStatement.setDouble(5, 1.83);
//            preparedStatement.setDouble(6, 70);
//            preparedStatement.setString(7, "Metric");
//            preparedStatement.executeUpdate();
//
//            preparedStatement = connect
//                    .prepareStatement("SELECT UserID, UserName, Sex, Date_of_birth, Height, Weight, Measurement from Diet_Exercise_Journal_UserProfile.UserProfile");
//            resultSet = preparedStatement.executeQuery();
//
//            displayResultSet(resultSet);