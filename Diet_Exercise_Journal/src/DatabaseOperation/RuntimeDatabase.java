package DatabaseOperation;


import java.sql.*;
import java.util.*;
import java.util.Date;

public class RuntimeDatabase {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    private static RuntimeDatabase runtimeDatabase = new RuntimeDatabase();
    private RuntimeDatabase(){

    }
    public static RuntimeDatabase getInstance(){
        if(runtimeDatabase == null){
            return new RuntimeDatabase();
        }
        return runtimeDatabase;
    }
    private int id;
    private String userName;
    private String sex;
    private String DOB;
    private double height;
    private double weight;
    private String measurement;

    //private String date;
//    private ArrayList<String> ingredients;
//    private ArrayList<String> quantities;

    /**
     * This class display all profiles in the table (database)
     * @throws Exception if sql error
     */
    public void readDatabase() throws Exception{//测试用
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Diet_Exercise_Journal_UserProfile.UserProfile");
            //上面4行只要创新方法就要重新加上
            while (resultSet.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1

                System.out.println("UserID：" + resultSet.getInt(1)); //  获取第一列的数据
                System.out.println("UserName：" + resultSet.getString("UserName"));  //获取字段为name的数据
                System.out.println("Sex：" + resultSet.getString(3)); //  获取第3列的数据
                System.out.println("DOB：" + resultSet.getDate(4)); //  获取第4列的数据
                System.out.println("Height：" + resultSet.getDouble(5)); //  获取第5列的数据
                System.out.println("Weight：" + resultSet.getDouble(6)); //  获取第6列的数据
                System.out.println("Measurement：" + resultSet.getString(7)); //  获取第7列的数据
                System.out.println("-----------------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }



    public void displayDietData(String mealType, String date, int userID) throws Exception{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            if(mealType.equals("breakfast")){
                resultSet = statement.executeQuery("select Ingredients, Quantity, CalorieIntake from Diet_Exercise_Journal_UserProfile.Breakfast where Date = '" + date + "' and UserID = " + userID);

            }
            else if(mealType.equalsIgnoreCase("lunch")){
                resultSet = statement.executeQuery("select Ingredients, Quantity, CalorieIntake from Diet_Exercise_Journal_UserProfile.Lunch where Date = '" + date + "'" + " and UserID = " + userID);

            }
            else if(mealType.equalsIgnoreCase("dinner")){
                resultSet = statement.executeQuery("select Ingredients, Quantity, CalorieIntake from Diet_Exercise_Journal_UserProfile.Dinner where Date = '" + date + "'" + " and UserID = " + userID);

            }
            else if(mealType.equalsIgnoreCase("snack")){
                resultSet = statement.executeQuery("select Ingredients, Quantity, CalorieIntake from Diet_Exercise_Journal_UserProfile.Dinner where Date = '" + date + "'" + " and UserID = " + userID);

            }
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getDouble(3));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public ArrayList<String> extractProfile() throws Exception{
        ArrayList<String> profiles = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Diet_Exercise_Journal_UserProfile.UserProfile");

            int i = 1;
            while(resultSet.next()){
                System.out.println(i + ": " + resultSet.getString("UserName") + " (id: " + resultSet.getInt(1) + ")");
                profiles.add(i + ": " + resultSet.getString("UserName") + " (id: " + resultSet.getInt(1) + ")");
                i++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
        return profiles;
    }

    /**
     * This class close the stream
     */
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

//-----------------------------------------------------------------------------------------------------------
    //below are setters and getters for chosen profile
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
        //System.out.println(this.id);
        setUserName(id);
        setSex(id);
        setDOB(id);
        setHeight(id);
        setWeight(id);
        setMeasurement(id);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(int userID) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select UserName from Diet_Exercise_Journal_UserProfile.UserProfile where UserID = " + userID);
            resultSet.next();
            this.userName = resultSet.getString(1);
            //System.out.println(userName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public String getSex() {
        return sex;
    }

    public void setSex(int userID) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select Sex from Diet_Exercise_Journal_UserProfile.UserProfile where UserID = " + userID);
            resultSet.next();
            this.sex = resultSet.getString(1);
            //System.out.println(sex);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(int userID) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select Date_of_Birth from Diet_Exercise_Journal_UserProfile.UserProfile where UserID = " + userID);
            resultSet.next();
            this.DOB = resultSet.getString(1);
            //System.out.println(DOB);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int userID) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select Height from Diet_Exercise_Journal_UserProfile.UserProfile where UserID = " + userID);
            resultSet.next();
            this.height = resultSet.getDouble(1);
            //System.out.println(height);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int userID) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select Weight from Diet_Exercise_Journal_UserProfile.UserProfile where UserID = " + userID);
            resultSet.next();
            this.weight = resultSet.getDouble(1);
            //System.out.println(weight);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int userID) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select Measurement from Diet_Exercise_Journal_UserProfile.UserProfile where UserID = " + userID);
            resultSet.next();
            this.measurement = resultSet.getString(1);
            //System.out.println(measurement);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

//-----------------------------------------------------------------------------------------------------

    /**
     * This method read all diet data (breakfast, lunch, dinner, snacks) of the user
     * in terms of date and meal
     * store all the data in a 2D array
     * @param userID is the user with the data
     */
    public void readAllMealInfo(int userID){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();

            resultSet = statement.executeQuery("select count(Date) from Diet_Exercise_Journal_UserProfile.Meal where UserID = " + userID);
            resultSet.next();
            String[][] mealInfo = new String[resultSet.getInt(1)][5];
            resultSet = statement.executeQuery("select Date from Diet_Exercise_Journal_UserProfile.Meal where UserID = " + userID);

            ArrayList<Date> dates = new ArrayList<>();
            while(resultSet.next()){
                dates.add(resultSet.getDate(1));
            }
            Date currentDate;
            String[] meals = {"Breakfast", "Lunch", "Dinner"};
            int i = 0;
            while(i < mealInfo.length){

                currentDate = dates.get(i);
                int j = 1, type = 0;
                mealInfo[i][0] = currentDate.toString();
                while(j < 4){

                    resultSet = statement.executeQuery("select Ingredients, Quantity from Diet_Exercise_Journal_UserProfile." + meals[type] + " where UserID = " + userID + " and Date = '" + currentDate + "'");
                    resultSet.next();
                    String[] ingredientTemp = resultSet.getString(1).split(", ");
                    String[] quantityTemp = resultSet.getString(2).split(", ");
                    for(int a = 0; a < ingredientTemp.length; a++){
                        if(mealInfo[i][j] == null && ingredientTemp.length != 1){
                            mealInfo[i][j] = ingredientTemp[a] + ", " + quantityTemp[a] + " +";
                        }
                        else if(mealInfo[i][j] == null){
                            mealInfo[i][j] = ingredientTemp[a] + ", " + quantityTemp[a];
                        }
                        else{
                            if(a != ingredientTemp.length - 1){
                                mealInfo[i][j] = mealInfo[i][j] + " " + ingredientTemp[a] + ", " + quantityTemp[a] + " +";
                            }
                            else{
                                mealInfo[i][j] = mealInfo[i][j] + " " + ingredientTemp[a] + ", " + quantityTemp[a];
                            }
                        }
                    }
                    j++; type++;
                }
                i++;
            }

            for(int x = 0; x < mealInfo.length; x++){
                Date snackDate = dates.get(x);
                String snackTemp = "";
                resultSet = statement.executeQuery("select Ingredients, Quantity from Diet_Exercise_Journal_UserProfile.Snack where UserID = " + userID + " and Date = '" + snackDate + "'");

                while(resultSet.next()){
                    String[] ingredientTemp2 = resultSet.getString(1).split(", ");
                    String[] quantityTemp2 = resultSet.getString(2).split(", ");
                    if(!snackTemp.equals("")){
                        snackTemp = snackTemp + " +";
                    }
                    for(int b = 0; b < ingredientTemp2.length; b++){
                        if(snackTemp.equals("") && ingredientTemp2.length != 1){
                            snackTemp = ingredientTemp2[b] + ", " + quantityTemp2[b] + " +";
                        }
                        else if(snackTemp.equals("")){
                            snackTemp = ingredientTemp2[b] + ", " + quantityTemp2[b];
                        }
                        else{
                            if(b != ingredientTemp2.length - 1){
                                snackTemp = snackTemp + " " + ingredientTemp2[b] + ", " + quantityTemp2[b] + " +";
                            }
                            else{
                                snackTemp = snackTemp + " " + ingredientTemp2[b] + ", " + quantityTemp2[b];
                            }
                        }
                    }
                }
                mealInfo[x][4] = snackTemp;
            }

            for(int k = 0; k < mealInfo.length; k++){
                for(int l = 0; l < mealInfo[k].length;l++){
                    System.out.println(mealInfo[k][l]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }
//    public ArrayList<String> getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(ArrayList<String> ingredients) {
//        this.ingredients = ingredients;
//        for(int i = 0; i < runtimeDatabase.getIngredients().size(); i++){
//            System.out.println(runtimeDatabase.getIngredients().get(i));
//        }
//    }
//
//
//    public void setQuantities(ArrayList<String> quantities) {
//        this.quantities = quantities;
//    }

    //-----------------------------------------------------------------------------------------------------------
    /**
     * This class create a profile for user and store it to the database
     * @param UserName is the username
     * @param sex is the sex
     * @param year is the year of birth
     * @param month is the month of birth
     * @param day is the day of birth
     * @param height is the height in meter
     * @param weight is the weight in kg
     * @param measurement is the measurement the user want
     * @throws Exception if sql command has syntax error
     */
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

    /**
     * This method reads the calorie burnt based on the date
     * @return an array in [date][data], [date][data]...
     */
    public static String[][] CaloryBurnedDataReader(){
        //just for testing, will be modified
        String[][] data;
        data= new String[][]{{"1/1", "1230"}, {"1/2", "1240"},{"1/3", "1230"}, {"1/4", "1240"},{"1/5", "1230"}, {"1/7", "1240"},{"1/8", "1230"}, {"1/9", "1240"}};  // those code for test only
        return data;
    }

    /**
     * This method reads the calorie intake based on the date
     * @return an array in [date][data], [date][data]...
     */
    public static String[][] CaloryIntakeDataReader(){
        //just for testing, will be modified
        String[][] data;
        data= new String[][]{{"1/1", "1030"}, {"1/2", "1040"},{"1/3", "1230"}, {"1/4", "1240"},{"1/6", "1230"}, {"1/7", "1240"},{"1/8", "1230"}, {"1/9", "1240"}};
        return data;
    }

    /**
     * This class reads all nutrients in a time period
     * @param number is the
     * @return an array
     */
    public static String[][] NutrientsDataReader(int number){
        //just for testing, will be modified
        //calculate average here


        //return the top 5 or 10 nutrients, add the rest to be the 6th or 11th data
        //code for test only
        String[][] data;
        data= new String[][]{{"Carb", "1230"}, {"Fat", "1240"},{"Salt", "1230"},{"Sugar", "1250"},{"Calcium", "800"}, {"Others", "1240"}};  // those code for test only
        return data;
    }

}

//ignore the comment below
//返还整理好的按量排序的前5或10个nutrients 并将剩下的全部加起来作为第6/11项加在末尾 返回值为[类型][量] 第二阶长度为1
//根据输入的日期(再加一个attribute)启止读出所有的nutrients