package DatabaseOperation;

import Operator.DataOperator;
import Operator.DietDataOperator;
import Operator.ExerciseDataOperator;
import OutcomeGenerator.Calculator;

import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.Date;

/**
 * This class is the principle class that:
 * 1: gets data from the database
 * 2: hold the information the need to be changed
 * 3: hold the information that are added
 * 4: write necessary data back to the database when application closes
 * This class is a singleton class which only have one instance in the whole life cycle
 */
public class RuntimeDatabase {

    //Below are the attribution from database operation
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    /*Below is the implementation of singleton class*/
    private static RuntimeDatabase runtimeDatabase = new RuntimeDatabase();
    private RuntimeDatabase(){

    }
    public static RuntimeDatabase getInstance(){
        if(runtimeDatabase == null){
            return new RuntimeDatabase();
        }
        return runtimeDatabase;
    }

    //Below are attributes of the user profile (hold the information from the database)
    private int id;
    private String userName;
    private String sex;
    private String DOB;
    private double height;
    private double weight;
    private String measurement;


    /*Below are arrays that holds value about the meals, exercise, calorie intake and burnt*/
    String[][] mealInfo = readAllMealInfo(getId());//holds all meal info in {{date, breakfast, lunch, dinner}, {...} }
    String[][] calorieInfo;//holds all calorie intake of meals {{date, breakfast, lunch, dinner}, {...} }

    //holds all others nutrient values for each meal {{date, breakfast, lunch, dinner}, {...} }
    //rank all top 10 nutrients and rest will be other
    String[][] otherNutrientInfo;
    String[][] exerciseInfo;//holds exercise info in {{date, type, duration, intensity, calorie burnt}, {...} }


    private void connectToDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This class display all profiles that exist in the database
     * @return an array of profiles
     * @throws Exception sql exception
     */
    public ArrayList<String> extractProfile() throws Exception{
        ArrayList<String> profiles = new ArrayList<>();
        try{
            //note: below are sql connection codes
            connectToDatabase();
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

    //when id is set, all other information are set
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
            connectToDatabase();
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
            connectToDatabase();
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
            connectToDatabase();
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
            connectToDatabase();
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
            connectToDatabase();
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
            connectToDatabase();
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

    //below are setter and getters for meal, exercise, nutrient info
    public void setMealInfo(String[][] mealInfo) {
        this.mealInfo = mealInfo;
        DataOperator operator = new DietDataOperator();
        //calorie intake array update along with the meal
        this.calorieInfo = new String[this.mealInfo.length][this.mealInfo[0].length];

        //other nutrient values update along with the meal
        this.otherNutrientInfo = new String[mealInfo.length][mealInfo[0].length];

        //calculate calorie intake, store in a 2d array
        this.calorieInfo = operator.calculateCalorieInfo(this.mealInfo);

        //set other nutrient values
        for(int i = 0; i < mealInfo.length; i++){
            this.otherNutrientInfo[i][0] = mealInfo[i][0];
            for(int j = 1; j < mealInfo[i].length; j++){
                this.otherNutrientInfo[i][j] = getOtherNutrientValues(mealInfo[i][j]);
            }
        }

        for (String[] strings : this.mealInfo) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }

    public String[][] getMealInfo() {
        return mealInfo;
    }

    public String[][] getCalorieInfo() {
        return calorieInfo;
    }

    public String[][] getOtherNutrientInfo() {
        return otherNutrientInfo;
    }

    public String[][] getExerciseInfo() {
        return exerciseInfo;
    }

    public void setExerciseInfo(String[][] exerciseInfo) {
        this.exerciseInfo = exerciseInfo;

        for (String[] strings : this.exerciseInfo) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }

    //-----------------------------------------------------------------------------------------------------
    /**
     * This method read all diet data (date, breakfast, lunch, dinner, snacks) of the user
     * in terms of date and meal
     * store all the data in a 2D array
     * @param userID is the user with the data
     * @return a string with format [date][breakfast][lunch][dinner][snack]
     * the inner string format in each meal block is [ingredient, quantity - 2nd ingredient, quantity...]
     */
    public String[][] readAllMealInfo(int userID){
        try {
            connectToDatabase();

            //first get the number of dates in the user profile to form the array
            resultSet = statement.executeQuery("select count(Date) from Diet_Exercise_Journal_UserProfile.Meal where UserID = " + userID);
            resultSet.next();
            //if no data in the database
            if(resultSet.getInt(1) == 0){
                return new String[1][5];
            }
            String[][] mealInfo = new String[resultSet.getInt(1)][5];
            resultSet = statement.executeQuery("select Date from Diet_Exercise_Journal_UserProfile.Meal where UserID = " + userID);

            //get the total number of date and store in the array
            ArrayList<Date> dates = new ArrayList<>();
            while(resultSet.next()){
                dates.add(resultSet.getDate(1));
            }
            Date currentDate;
            String[] meals = {"Breakfast", "Lunch", "Dinner"};

            int i = 0;

            //Start form the string
            while(i < mealInfo.length){
                //form the string by date numbers, the first element is always the date
                currentDate = dates.get(i);
                int j = 1, type = 0;
                mealInfo[i][0] = currentDate.toString();
                while(j < 4){
                    //get information from the database
                    resultSet = statement.executeQuery("select Ingredients, Quantity from Diet_Exercise_Journal_UserProfile." + meals[type] + " where UserID = " + userID + " and Date = '" + currentDate + "'");
                    resultSet.next();
                    if(resultSet.getString(1).equals("")){
                        mealInfo[i][j] = null;
                    }
                    else{
                        String[] ingredientTemp = resultSet.getString(1).split(", ");
                        String[] quantityTemp = resultSet.getString(2).split(", ");
                        for(int a = 0; a < ingredientTemp.length; a++){
                            if(mealInfo[i][j] == null && ingredientTemp.length != 1){
                                mealInfo[i][j] = ingredientTemp[a] + ", " + quantityTemp[a] + " -";
                            }

                            else if(mealInfo[i][j] == null){
                                mealInfo[i][j] = ingredientTemp[a] + ", " + quantityTemp[a];
                            }
                            else{
                                if(a != ingredientTemp.length - 1){
                                    mealInfo[i][j] = mealInfo[i][j] + " " + ingredientTemp[a] + ", " + quantityTemp[a] + " -";
                                }
                                else{
                                    mealInfo[i][j] = mealInfo[i][j] + " " + ingredientTemp[a] + ", " + quantityTemp[a];
                                }
                            }
                        }
                    }
                    //the ingredients are stored in the first column, quantity stored in the second (of the result of the select statement)

                    j++; type++;
                }
                i++;
            }

            //form the string for the snack, because you can have many snacks that taken in the same day
            //The logistic is the same as other meals
            for(int x = 0; x < mealInfo.length; x++){
                Date snackDate = dates.get(x);
                String snackTemp = "";
                resultSet = statement.executeQuery("select Ingredients, Quantity from Diet_Exercise_Journal_UserProfile.Snack where UserID = " + userID + " and Date = '" + snackDate + "'");

                while(resultSet.next()){
                    if(resultSet.getString(1).equals("")){
                        mealInfo[x][4] = null;
                    }
                    else{
                        String[] ingredientTemp2 = resultSet.getString(1).split(", ");
                        String[] quantityTemp2 = resultSet.getString(2).split(", ");
                        if(!snackTemp.equals("")){
                            snackTemp = snackTemp + " -";
                        }
                        for(int b = 0; b < ingredientTemp2.length; b++){
                            if(snackTemp.equals("") && ingredientTemp2.length != 1){
                                snackTemp = ingredientTemp2[b] + ", " + quantityTemp2[b] + " -";
                            }
                            else if(snackTemp.equals("")){
                                snackTemp = ingredientTemp2[b] + ", " + quantityTemp2[b];
                            }
                            else{
                                if(b != ingredientTemp2.length - 1){
                                    snackTemp = snackTemp + " " + ingredientTemp2[b] + ", " + quantityTemp2[b] + " -";
                                }
                                else{
                                    snackTemp = snackTemp + " " + ingredientTemp2[b] + ", " + quantityTemp2[b];
                                }
                            }
                        }
                    }

                }
                if(snackTemp.equals("")){
                    mealInfo[x][4] = null;
                }
                else{
                    mealInfo[x][4] = snackTemp;
                }
            }

//            for(int k = 0; k < mealInfo.length; k++){
//                for(int l = 0; l < mealInfo[k].length;l++){
//                    System.out.println(mealInfo[k][l]);
//                }
//            }
            return mealInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
        return  null;
    }


    /**
     * This class reads all exercise info (date, type, duration, intensity, calorie burnt) of the user
     * store all the data in a 2D array
     * @param userID is the user with the data
     * @return a string with format [date][type, duration, intensity, calorie burnt]
     * the inner string format is {{date, type, duration, intensity, calorie burnt - 2nd exercise type, 2nd duration......}}
     */
    public String[][] readAllExerciseInfo(int userID){
        try{
            connectToDatabase();

            DataOperator operator = new ExerciseDataOperator();

            //first get the number of dates in the user profile to form the array
            resultSet = statement.executeQuery("select count(distinct Date) from Diet_Exercise_Journal_UserProfile.Exercise where UserID = " + userID);
            resultSet.next();
            //if there is no data in the database
            if(resultSet.getInt(1) == 0){
                return new String[1][2];
            }

            String[][] exerciseInfo = new String[resultSet.getInt(1)][2];
            resultSet = statement.executeQuery("select distinct Date from Diet_Exercise_Journal_UserProfile.Exercise where UserID = " + userID);

            //get the dates
            ArrayList<Date> dates = new ArrayList<>();
            Date currentDate;
            while(resultSet.next()){
                dates.add(resultSet.getDate(1));
            }

            //form the string by date
            for(int i = 0; i < exerciseInfo.length; i++){
                currentDate = dates.get(i);
                exerciseInfo[i][0] = dates.get(i).toString();

                //select data from the database
                resultSet = statement.executeQuery("select Type, Duration, Intensity from Diet_Exercise_Journal_UserProfile.Exercise where UserID = " + userID + " and Date = '" + currentDate + "'");
                while(resultSet.next()){
                    if(exerciseInfo[i][1] == null){
                        exerciseInfo[i][1] = resultSet.getString(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3) + ", " + operator.calculateCalorieBurnt(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                    }
                    else{
                        exerciseInfo[i][1] = exerciseInfo[i][1] + " - " + resultSet.getString(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3) + ", " + operator.calculateCalorieBurnt(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                    }
                }
            }
            return exerciseInfo;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
        return null;
    }


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
     */
    public void createProfile(String UserName, String sex, int year, int month, int day, double height, double weight, String measurement) {
        try {
            connectToDatabase();
            resultSet = statement.executeQuery("select * from Diet_Exercise_Journal_UserProfile.UserProfile");

            //prepare the statement
            preparedStatement = connect.prepareStatement("insert into Diet_Exercise_Journal_UserProfile.UserProfile values (default, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, sex);
            preparedStatement.setString(3, year + "-" + month + "-" + day);
            preparedStatement.setDouble(4, height);
            preparedStatement.setDouble(5, weight);
            preparedStatement.setString(6, measurement);//metric vs imperial
            //execute the query
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
     * This method log the meal
     * store the information in the mealInfo[][] array
     * @param date is the date of the meal
     * @param mealType whether it is breakfast, lunch, dinner or snack
     * @param ingredients is the ingredient
     * @param quantity is the quantity in g/ml, or how many eggs
     */
    public void logMeal(String date, String mealType, ArrayList<String> ingredients, ArrayList<String> quantity){

        String[][] mealInfo = new String[getMealInfo().length][getMealInfo()[0].length];
        for(int i = 0; i < mealInfo.length; i++){
            for(int j = 0; j < mealInfo[i].length; j++){
                mealInfo[i][j] = getMealInfo()[i][j];
            }
        }
        boolean isChange = false;//see if the log is a change to exist data
        int type = 1;
        //based on the type, see what meal type it is. Store the information in corresponding column (index)
        if(mealType.equalsIgnoreCase("Breakfast")){
            type = 1;
        }
        else if(mealType.equalsIgnoreCase("Lunch")){
            type = 2;
        }
        else if(mealType.equalsIgnoreCase("Dinner")){
            type = 3;
        }
        else if (mealType.equalsIgnoreCase("Snack")){
            type = 4;
        }
        //traverse to see if the data exist
        //form the string in ingredient, quantity - 2nd ingredient, 2nd quantity, ...
        for(int i = 0; i < mealInfo.length; i++){
            if(mealInfo[i][0] == null){
                break;
            }
            //if the date is found, means it is a change to existing data
            if(mealInfo[i][0].equals(date)){
                isChange = true;
                if(type == 4){
                    //if it is a snack, add the new information behind the existing info
                    if(mealInfo[i][type] == null){
                        mealInfo[i][type] = null;
                    }
                    else{
                        //if it is a snack, add the ingredients behind
                        mealInfo[i][type] = mealInfo[i][type] + " -";
                    }
                }
                else{
                    //if the meal is not a snack, means it is a rewrite, reset the info
                    mealInfo[i][type] = null;
                }
                for(int j = 0; j < ingredients.size(); j++){
                    if(mealInfo[i][type] == null && ingredients.size() != 1){
                        mealInfo[i][type] = ingredients.get(j) + ", " + quantity.get(j) + " -";
                    }
                    else if(mealInfo[i][type] == null){
                        mealInfo[i][type] = ingredients.get(j) + ", " + quantity.get(j);
                    }
                    else{
                        if(j == ingredients.size() - 1){
                            mealInfo[i][type] = mealInfo[i][type] + " " + ingredients.get(j) + ", " + quantity.get(j);
                        }
                        else{
                            mealInfo[i][type] = mealInfo[i][type] + " " + ingredients.get(j) + ", " + quantity.get(j) + " -";
                        }
                    }
                }
            }
        }
        //update the array
        setMealInfo(mealInfo);
        //if it is a new data, extend the array
        if(!isChange){

            String[][] newMealInfo = new String[mealInfo.length+1][mealInfo[0].length];
            if(mealInfo.length == 1 && mealInfo[0][0] == null){
                newMealInfo = new String[mealInfo.length][mealInfo[0].length];
            }
            for(int i = 0; i < mealInfo.length; i++){
                for(int j = 0; j < mealInfo[i].length; j++){
                    newMealInfo[i][j] = mealInfo[i][j];
                }
            }

            newMealInfo[newMealInfo.length-1][0] = date;
            for(int j = 0; j < ingredients.size(); j++){
                if(newMealInfo[newMealInfo.length-1][type] == null && ingredients.size() != 1){
                    newMealInfo[newMealInfo.length-1][type] = ingredients.get(j) + ", " + quantity.get(j) + " -";
                }
                else if(newMealInfo[newMealInfo.length-1][type] == null){
                    newMealInfo[newMealInfo.length-1][type] = ingredients.get(j) + ", " + quantity.get(j);
                }
                else{
                    if(j != ingredients.size() - 1){
                        newMealInfo[newMealInfo.length-1][type] =  newMealInfo[newMealInfo.length-1][type] + " " + ingredients.get(j) + ", " + quantity.get(j) + " -";
                    }
                    else{
                        newMealInfo[newMealInfo.length-1][type] =  newMealInfo[newMealInfo.length-1][type] + " " + ingredients.get(j) + ", " + quantity.get(j);
                    }
                }
            }
            setMealInfo(newMealInfo);
        }
    }

    /**
     * This method log the exercise
     * store the information in the exerciseInfo[][] array
     * @param date is the date of the meal
     * @param exerciseType whether it is breakfast, lunch, dinner or snack
     * @param duration is the ingredient
     * @param intensity is the quantity in g/ml, or how many eggs
     * @throws ParseException if date input failed
     */
    public void logExercise(String date, String exerciseType, String duration, String intensity) throws ParseException {

        DataOperator operator = new ExerciseDataOperator();
        String[][] exerciseInfo = new String[getExerciseInfo().length][getExerciseInfo()[0].length];
        for(int i = 0; i < exerciseInfo.length; i++){
            for(int j = 0; j < exerciseInfo[i].length; j++){
                exerciseInfo[i][j] = getExerciseInfo()[i][j];
            }
        }

        boolean isModify = false;
        //check if it is a modify of existing data
        for(int i = 0; i < exerciseInfo.length; i++){
            if(exerciseInfo[i][0] == null){
                break;
            }
            //if it is, add the info behind
            if(exerciseInfo[i][0].equals(date)){
                isModify = true;
                exerciseInfo[i][1] = exerciseInfo[i][1] + " - " + exerciseType + ", " + duration + ", " + intensity + ", " + operator.calculateCalorieBurnt(exerciseType, duration, intensity);
            }
        }

        setExerciseInfo(exerciseInfo);//update the array

        //if it is not a change, add a new row
        if(!isModify){
            String[][] newExerciseInfo = new String[getExerciseInfo().length + 1][getExerciseInfo()[0].length];
            if(getExerciseInfo().length == 1 && getExerciseInfo()[0][0] == null){
                newExerciseInfo = new String[exerciseInfo.length][exerciseInfo[0].length];
            }
            for(int i = 0; i < exerciseInfo.length; i++){
                for(int j = 0; j < exerciseInfo[i].length; j++){
                    newExerciseInfo[i][j] = exerciseInfo[i][j];
                }
            }

            newExerciseInfo[newExerciseInfo.length-1][0] = date;
            newExerciseInfo[newExerciseInfo.length-1][1] = exerciseType + ", " + duration + ", " + intensity + ", " + operator.calculateCalorieBurnt(exerciseType, duration, intensity);

            setExerciseInfo(newExerciseInfo);//update the array
        }

    }

    /**
     * This class update the profile information
     * @pre IMPORTANT PRE CONDITION: You can not change your height/weight and the measurement at the same time
     * you can ONLY change height/weight or change measurement in one single change action.
     * @param UserName is the new username
     * @param sex is the new sex
     * @param year is the year of birth
     * @param month is the month of birth
     * @param day is the day of birth
     * @param height is the new height
     * @param weight is the new weight
     * @param measurement is the new measurement
     */
    public void modifyProfile(String UserName, String sex, int year, int month, int day, double height, double weight, String measurement){
        try{

            connectToDatabase();
            this.userName = UserName;
            this.sex = sex;
            this.DOB = year + "-" + month + "-" + day;

            //you can not change them at the same time
            if(this.measurement.equalsIgnoreCase("Imperial") && measurement.equalsIgnoreCase("Metric")){
                this.weight = this.weight / 2.204;
                this.height = this.height / 0.032;
            }
            else if(this.measurement.equalsIgnoreCase("Metric") && measurement.equalsIgnoreCase("Imperial")){
                this.weight = this.weight * 2.204;
                this.height = this.height * 0.032;
            }
            else{
                this.height = height;
                this.weight = weight;
            }
            this.measurement = measurement;

            //write back to database right away
            preparedStatement = connect.prepareStatement("update UserProfile set UserName = ?, Sex = ?, Date_of_Birth = ?, Height = ?, " +
                    "Weight = ?, Measurement = ? where UserID = ?;");

            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, sex);
            preparedStatement.setString(3, this.DOB);
            preparedStatement.setDouble(4, this.height);
            preparedStatement.setDouble(5, this.weight);
            preparedStatement.setString(6, measurement);
            preparedStatement.setInt(7, getId());
            preparedStatement.executeUpdate();
            //System.out.println(weight);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

//--------------------------------------------------------------------------------------------------------------

    /**
     * This class read the calorie information of an ingredient in cal/g or egg in amount
     * @param food is the food search for calorie
     * @return the calorie of the food
     */
    public double extractCalorieInfo(String food){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select FoodID from Diet_Exercise_Journal_UserProfile.FOOD_NAME where Abbreviation = '" + food.toLowerCase() + "'");
            //resultSet.next();
            double foodCalorie = 0.0;
            while(resultSet.next()){
                int foodID = resultSet.getInt(1);
                resultSet = statement.executeQuery("select NutrientValue from Diet_Exercise_Journal_UserProfile.NUTRIENT_AMOUNT where FoodID = " + foodID + " and NutrientID = 208");
                resultSet.next();

                if(foodID == 83){//if it is an egg
                    foodCalorie = resultSet.getDouble(1) / 4;
                }
                else if(foodID == 2062){//if it is a coleslaw
                    foodCalorie = resultSet.getDouble(1) * 2 / 100;
                }
                else{
                    foodCalorie = resultSet.getDouble(1) / 100;
                }
            }
            return foodCalorie;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
        return 0.00;
    }

    /**
     * This class get other nutrient values (no Calorie) of a meal
     * It ranks top 10 of the nutrients and the rest added together and labelled as other
     * @param meal is the meal from the mealInfo array
     * @return a string in a format of {N1, amount - N2, amount - ...}
     */
    public String getOtherNutrientValues(String meal){
        try {
            connectToDatabase();
            if(meal == null || meal.equalsIgnoreCase("")){
                return null;
            }
            String[] temp = meal.split(" - ");
            String[] ingredients = new String[temp.length];
            String[] quantity = new String[temp.length];

            for(int i = 0; i < temp.length; i++){
                ingredients[i] = temp[i].split(", ")[0];
                quantity[i] = temp[i].split(", ")[1];
            }
            String query = "SELECT nutrientID, SUM(nutrientValue) from (";

            /*Issue a table that is a union of many tables, it also sum the quantity and do unit exchange*/
            for(int i = 0; i < ingredients.length; i++){
                resultSet = statement.executeQuery("select FoodID from Diet_Exercise_Journal_UserProfile.FOOD_NAME where Abbreviation = '" + ingredients[i].toLowerCase() + "'");
                while(resultSet.next()){

                    //handle eggs and coleslaw
                    if(i == ingredients.length - 1){
                        if(resultSet.getInt(1) == 83){
                            query = query + "select NutrientID, NutrientValue / 4 * " + quantity[i] + " AS nutrientValue from NUTRIENT_AMOUNT where FoodID = " + resultSet.getInt(1) + " and NutrientID != 208 ";
                        }
                        else if(resultSet.getInt(1) == 2062){
                            query = query + "select NutrientID, NutrientValue * 2 / 100 * " + quantity[i] + " AS nutrientValue from NUTRIENT_AMOUNT where FoodID = " + resultSet.getInt(1) + " and NutrientID != 208 ";
                        }
                        else{
                            query = query + "select NutrientID, NutrientValue / 100 * " + quantity[i] + " AS nutrientValue from NUTRIENT_AMOUNT where FoodID = " + resultSet.getInt(1) + " and NutrientID != 208 ";
                        }

                    }
                    else{
                        if(resultSet.getInt(1) == 83){
                            query = query + "select NutrientID, NutrientValue / 4 * " + quantity[i] + " AS nutrientValue from NUTRIENT_AMOUNT where FoodID = " + resultSet.getInt(1) + " and NutrientID != 208 union all ";
                        }
                        else if(resultSet.getInt(1) == 2062){
                            query = query + "select NutrientID, NutrientValue * 2 / 100 * " + quantity[i] + " AS nutrientValue from NUTRIENT_AMOUNT where FoodID = " + resultSet.getInt(1) + " and NutrientID != 208 union all";
                        }
                        else{
                            query = query + "select NutrientID, NutrientValue / 100 * " + quantity[i] + " AS nutrientValue from NUTRIENT_AMOUNT where FoodID = " + resultSet.getInt(1) + " and NutrientID != 208 union all ";
                        }
                    }
                }
            }

            query = query + ") as combined_tables GROUP BY nutrientID order by SUM(nutrientValue) desc;";

            //after select the table, access it
            ArrayList<Double> nutrientAmount = new ArrayList<>();
            ArrayList<Integer> nutrientID = new ArrayList<>();
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            //store all ids and values in two arrays
            while(resultSet.next()){
                nutrientAmount.add((double) Math.round(resultSet.getDouble(2) * 100) / 100.0);
                nutrientID.add(resultSet.getInt(1));
            }
            for(int i = 0; i < nutrientID.size(); i++){
                resultSet = statement.executeQuery("select NutrientName, NutrientUnit from Diet_Exercise_Journal_UserProfile.NUTRIENT_NAME where NutrientID = " + nutrientID.get(i));
                resultSet.next();
                //do unit exchange
                if(resultSet.getString(2).equals("mg")){
                    nutrientAmount.set(i, nutrientAmount.get(i) / 1000);
                }
                else if(resultSet.getString(2).equals("µg")){
                    nutrientAmount.set(i, nutrientAmount.get(i) / 1000000);
                }
            }

            //sort the array, the id array is also sorted
            double temp1 = 0;
            int temp2 = 0;
            for(int i = 0; i < nutrientID.size() ; i++){
                for(int j = 1; j < nutrientID.size() - i; j++){
                    if(nutrientAmount.get(j - 1) < nutrientAmount.get(j)){
                        temp1 = nutrientAmount.get(j - 1);
                        nutrientAmount.set(j - 1, nutrientAmount.get(j));
                        nutrientAmount.set(j, temp1);

                        temp2 = nutrientID.get(j - 1);
                        nutrientID.set(j - 1, nutrientID.get(j));
                        nutrientID.set(j, temp2);
                    }
                }
            }
            //after sorting, leave top 10 and add the rest
            double other = 0;
            for(int i = 10; i < nutrientAmount.size() ; i++){
                other = other + nutrientAmount.get(i);
            }
            nutrientAmount.set(10, Math.round(other * 100) / 100.0);

            //extract the nutrient name from the database
            ArrayList<Double> newNutrientAmount = new ArrayList<>();
            ArrayList<String> newNutrientID = new ArrayList<>();
            for(int i = 0; i < 11; i++){
                newNutrientAmount.add(nutrientAmount.get(i));
                if(i < 10){
                    resultSet = statement.executeQuery("select NutrientName from Diet_Exercise_Journal_UserProfile.NUTRIENT_NAME where NutrientID = " + nutrientID.get(i));
                    resultSet.next();
                    newNutrientID.add(resultSet.getString(1));
                }
            }
            newNutrientID.add("Other");

            //form the string
            String result = "";
            for(int i = 0; i < newNutrientAmount.size() ; i++){
                if(i == newNutrientAmount.size() - 1){
                    result = result + newNutrientID.get(i) + " - " + newNutrientAmount.get(i);
                }
                else{
                    result = result + newNutrientID.get(i) + " - " + newNutrientAmount.get(i) + "; ";
                }
            }
            //return the string
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
        return "";
    }

    /**
     * This class get the quantity of foods that the user eat in each food group
     * Note: we only have 5 groups in the database
     * @return an array that store average quantities of food groups ("Dairy and Egg Products", "Spices and Herbs", "Fats and Oils", "Vegetables and Vegetable Products", "Baked Products")
     * that user eat each day
     */
    public double[] getFoodGroup(){
        try {
            connectToDatabase();

            String[][] temp = new String[this.mealInfo.length][this.mealInfo[0].length];
            for(int i = 0; i < temp.length; i++) {
                for(int j = 0; j < temp[i].length; j++){
                    temp[i][j] = this.mealInfo[i][j];
                }
            }
            String[] foodGroups = {"Dairy and Egg Products", "Spices and Herbs", "Fats and Oils", "Vegetables and Vegetable Products", "Baked Products"};

            double[] totalQuantity = new double[5];

            ArrayList<String> allIngredients = new ArrayList<>();
            ArrayList<Double> allQuantity = new ArrayList<>();

            //get all ingredients and quantities of the whole diet record
            for(int i = 0; i < temp.length; i++){
                for(int j = 1; j < temp[0].length; j++){
                    if(temp[i][j] != null){
                        String[] ingredientsQuantity = temp[i][j].split(" - ");
                        String[] ingredients = new String[ingredientsQuantity.length];
                        String[] quantity = new String[ingredientsQuantity.length];
                        for(int k = 0; k < ingredientsQuantity.length; k++){

                            ingredients[k] = ingredientsQuantity[k].split(", ")[0];
                            quantity[k] = ingredientsQuantity[k].split(", ")[1];

                            allIngredients.add(ingredients[k]);
                            allQuantity.add(Double.parseDouble(quantity[k]));
                        }
                    }
                }
            }


            for(int i = 0; i < allIngredients.size(); i++){

                resultSet = statement.executeQuery("select FoodGroupID from Diet_Exercise_Journal_UserProfile.FOOD_NAME where Abbreviation = '" + allIngredients.get(i).toLowerCase() + "'");
                resultSet.next();
                int foodGroupId = resultSet.getInt(1);
                if(foodGroupId == 1){
                    totalQuantity[0] = totalQuantity[0] + allQuantity.get(i);
                }
                else if(foodGroupId == 2){
                    totalQuantity[1] = totalQuantity[1] + allQuantity.get(i);
                }
                else if(foodGroupId == 4){
                    totalQuantity[2] = totalQuantity[2] + allQuantity.get(i);
                }
                else if(foodGroupId == 11){
                    totalQuantity[3] = totalQuantity[3] + allQuantity.get(i);
                }
                else if(foodGroupId == 18){
                    totalQuantity[4] = totalQuantity[4] + allQuantity.get(i);
                }
            }

            //get an average value of the food that the user eat each day
            for(int i = 0; i < totalQuantity.length; i++){
                totalQuantity[i] = totalQuantity[i] / this.mealInfo.length;
            }

            return totalQuantity;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
        return null;
    }
//---------------------------------------------------------------------------------------------------------------

//-------------------------------------------------------------------------------------------------------------------------

    /**
     * This method write all meal information back to the database on application close
     */
    public void writeAllMealBack(){
        try{

            connectToDatabase();

            String type = "";
            for(int i = 0; i < this.mealInfo.length; i++){

                preparedStatement = connect.prepareStatement("insert into Diet_Exercise_Journal_UserProfile.Meal(Date, userID, breakfast, lunch, dinner, snack) values (?, ?, ?, ?, ?, ?) on duplicate key update breakfast = values(breakfast), lunch = values(lunch), dinner = values(dinner), snack = values(snack);");
                preparedStatement.setString(1, mealInfo[i][0]);
                preparedStatement.setInt(2, getId());
                preparedStatement.setString(3, mealInfo[i][0]);
                preparedStatement.setString(4, mealInfo[i][0]);
                preparedStatement.setString(5, mealInfo[i][0]);
                preparedStatement.setString(6, mealInfo[i][0]);
                preparedStatement.executeUpdate();
                for(int j = 1; j < this.mealInfo[i].length; j++){
                    if(j == 1){
                        type = "Breakfast";
                    }
                    else if(j == 2){
                        type = "Lunch";
                    }
                    else if(j == 3){
                        type = "Dinner";
                    }
                    else if(j == 4){
                        type = "Snack";
                    }
                    String ingredients = "";
                    String quantity = "";
                    //sort data and format a string, same as before, in ingredient, ingredient, ...
                    if(mealInfo[i][j] == null || mealInfo[i][j].equalsIgnoreCase("")){
                        ingredients = "";
                        quantity = "";
                    }
                    else{
                        String[] temp = mealInfo[i][j].split(" - ");

                        for(int k = 0; k < temp.length; k++){
                            String[] ingredientsQuantity = temp[k].split(", ");
                            if(k == temp.length - 1){
                                ingredients = ingredients + ingredientsQuantity[0];
                                quantity = quantity + ingredientsQuantity[1];
                            }
                            else{
                                ingredients = ingredients + ingredientsQuantity[0] + ", ";
                                quantity = quantity + ingredientsQuantity[1] + ", ";
                            }
                        }
                    }
                    //System.out.println(ingredients + "->: " + quantity);

                    //If the row exist, update it
                    //If it not, create a new row
                    if(type.equalsIgnoreCase("Snack")){
                        preparedStatement = connect.prepareStatement("insert into Diet_Exercise_Journal_UserProfile.Snack (Date, userID, ingredients, quantity)" +
                                " values (?, ?, ?, ?) on duplicate key update ingredients = values(ingredients), quantity = values(quantity);");
                        //preparedStatement.setString(1, type);
                        preparedStatement.setString(1, mealInfo[i][0]);
                        preparedStatement.setInt(2, getId());
                        preparedStatement.setString(3, ingredients);
                        preparedStatement.setString(4, quantity);
                    }
                    else{
                        preparedStatement = connect.prepareStatement("insert into Diet_Exercise_Journal_UserProfile." + type + "(Date, userID, ingredients, quantity)" +
                                " values (?, ?, ?, ?) on duplicate key update ingredients = values(ingredients), quantity = values(quantity);");

                        preparedStatement.setString(1, mealInfo[i][0]);
                        preparedStatement.setInt(2, getId());
                        preparedStatement.setString(3, ingredients);
                        preparedStatement.setString(4, quantity);
                    }
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    /**
     * This method write all exercise information back to the database on application close
     */
    public void writeExerciseBack(){
        try {
            connectToDatabase();

            preparedStatement = connect.prepareStatement("drop table Exercise;");
            preparedStatement.executeUpdate();

            preparedStatement = connect.prepareStatement("CREATE TABLE Exercise (" +
                    "ExID INT NOT NULL AUTO_INCREMENT, Date date NOT NULL, UserID INT NOT NULL, Type VARCHAR(255), Duration VARCHAR(255), Intensity VARCHAR(255), CalorieBurnt double," +
                    " Constraint exercise_PK Primary key (ExID));");
            preparedStatement.executeUpdate();

            String type = "";
            String duration = "";
            String intensity = "";
            double calorieBurnt = 0.0;
            for(int i = 0; i < exerciseInfo.length; i++){

                for(int j = 1; j < exerciseInfo[i].length; j++){
                    preparedStatement = connect.prepareStatement("insert into Diet_Exercise_Journal_UserProfile.Exercise values (default, ?, ?, ?, ?, ?, ?)");

                    //if the data is empty
                    if(exerciseInfo[i][j] == null || exerciseInfo[i][j].equalsIgnoreCase("")){
                        type = "";
                        duration = "";
                        intensity = "";
                        calorieBurnt = 0.0;
                        preparedStatement.setString(1, exerciseInfo[i][0]);
                        preparedStatement.setInt(2, getId());
                        preparedStatement.setString(3, type);
                        preparedStatement.setString(4, duration);
                        preparedStatement.setString(5, intensity);
                        preparedStatement.setDouble(6, calorieBurnt);
                        preparedStatement.executeUpdate();
                    }
                    else{
                        //split the string and store information separately
                        String[] temp = exerciseInfo[i][j].split(" - ");
                        for(int k = 0; k < temp.length; k++){
                            type = temp[k].split(", ")[0];
                            duration = temp[k].split(", ")[1];
                            intensity = temp[k].split(", ")[2];
                            calorieBurnt = Double.parseDouble(temp[k].split(", ")[3]);
                            preparedStatement.setString(1, exerciseInfo[i][0]);
                            preparedStatement.setInt(2, getId());
                            preparedStatement.setString(3, type);
                            preparedStatement.setString(4, duration);
                            preparedStatement.setString(5, intensity);
                            preparedStatement.setDouble(6, calorieBurnt);
                            preparedStatement.executeUpdate();
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

}
//------------------------------------------------------------------------------------------------------------------------
