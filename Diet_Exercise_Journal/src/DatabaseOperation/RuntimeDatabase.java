package DatabaseOperation;


import java.sql.*;

public class RuntimeDatabase {
    private Connection connect = null;
    private Statement statement = null;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    //显示所有profile包括具体数据
    public void readDatabase() throws Exception{//测试用
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Diet_Exercise_Journal_UserProfile", "root", "zxcv6509");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Diet_Exercise_Journal_UserProfile.UserProfile");
            //上面4行只要创新方法就要重新加上
            displayResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    private void displayResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
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

    public static String[][] CaloryBurnedDataReader(){ //根据输入的日期启止读出所有的卡路里消耗数据 [[日期][数据]],[[日期][数据]],[[日期][数据]]
        String[][] data;
        data= new String[][]{{"1/1", "1230"}, {"1/2", "1240"},{"1/3", "1230"}, {"1/4", "1240"},{"1/5", "1230"}, {"1/7", "1240"},{"1/8", "1230"}, {"1/9", "1240"}};  // those code for test only
        return data;
    }

    public static String[][] CaloryIntakeDataReader(){ //..
        String[][] data;
        data= new String[][]{{"1/1", "1030"}, {"1/2", "1040"},{"1/3", "1230"}, {"1/4", "1240"},{"1/6", "1230"}, {"1/7", "1240"},{"1/8", "1230"}, {"1/9", "1240"}};
        return data;
    }

    public static String[][] NutrientsDataReader(int number){ //根据输入的日期(再加一个attribute)启止读出所有的nutrients

        //在此处计算avg

        //返还整理好的按量排序的前5或10个nutrients 并将剩下的全部加起来作为第6/11项加在末尾 返回值为[类型][量] 第二阶长度为1

        //code for test only
        String[][] data;
        data= new String[][]{{"Carb", "1230"}, {"Fat", "1240"},{"Salt", "1230"},{"Sugar", "1250"},{"Calcium", "800"}, {"Others", "1240"}};  // those code for test only
        return data;
    }

}
