package Visualizer;

import DatabaseOperation.RuntimeDatabase;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

/**
 * This class generate JFree chart for diet
 */
public class DietDataVisualizer extends DataVisualizer{
    /**
     * This method create pie chart
     * @param NumberOfNutrients is the number of nutrients
     */
    public static void getChart(int NumberOfNutrients,String startDate,String endDate) {
        // create a dataset...
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (int i=0;i<NumberOfNutrients+1;i++){
            dataset.setValue(getData(NumberOfNutrients,startDate,endDate)[i][0], Double.parseDouble(getData(NumberOfNutrients,startDate,endDate)[i][1]));
        }

        // create a chart...
        JFreeChart chart = ChartFactory.createPieChart(
                "Sample Pie Chart -Yves", // chart title
                dataset, // data set
                true, // legend (image example)
                true, // tooltips
                false // URLs: URLS
        );

        // create and display a frame...
        ChartFrame frame = new ChartFrame("First", chart);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

    private static void deepCopyInfo(String[][] temp){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        for(int i = 0; i < runtimeDatabase.getOtherNutrientInfo().length; i++){
            for(int j = 0; j < runtimeDatabase.getOtherNutrientInfo()[0].length; j++){
                temp[i][j] = runtimeDatabase.getOtherNutrientInfo()[i][j];
            }
        }
    }

    private static String[][] getAverage(double carb, double fat, double protein, double glucose, double vitaminC, double other, String[][] s, int length){
        for(int i = 0; i < s.length; i++){

            if(i == 0){
                s[i][0] = "Carbohydrate";
                s[i][1] = String.valueOf(Math.round(carb / length * 100) / 100.0);
            }
            else if(i == 1){
                s[i][0] = "Fat";
                s[i][1] = String.valueOf(Math.round(fat / length * 100) / 100.0);
            }
            else if(i == 2){
                s[i][0] = "Protein";
                s[i][1] = String.valueOf(Math.round(protein / length * 100) / 100.0);
            }
            else if(i == 3){
                s[i][0] = "Glucose";
                s[i][1] = String.valueOf(Math.round(glucose / length * 100) / 100.0);
            }
            else if(i == 4){
                s[i][0] = "Vitamin C";
                s[i][1] = String.valueOf(Math.round(vitaminC / length * 100) / 100.0);
            }
            else {
                s[i][0] = "Other";
                s[i][1] = String.valueOf(Math.round(other / length * 100) / 100.0);
            }
        }
        return s;
    }
    /**
     * This class reads all nutrients in a time period
     * DEVELOPING
     * @param number is the number of data to appear (5 or 10)
     * @return an array in [nutrient][value], [nutrient][value], ...
     */
    public static String[][] NutrientsDataReader(int number, String startDate, String endDate){

        double carb = 0, fat = 0, protein = 0, glucose = 0, vitaminC = 0, other = 0;
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        String[][] data = new String[6][2];
        String[][] temp = new String[runtimeDatabase.getOtherNutrientInfo().length][runtimeDatabase.getOtherNutrientInfo()[0].length];

        deepCopyInfo(temp);

        int start = 0;
        int end = 0;

        //get the index of start date and end date
        for(int i = 0; i < temp.length; i++){
            if(temp[i][0].equalsIgnoreCase(startDate)){
                start = i;
            }
            else if(temp[i][0].equalsIgnoreCase(endDate)){
                end = i;
            }
        }
        if(start > end){
            System.out.println("invalid input, start > end");
            return null;
        }
        int length = end + 1 - start;
        String[][] dateRange = new String[end + 1 - start][runtimeDatabase.getOtherNutrientInfo()[0].length];

        //copy string
        for(int i = start; i < end + 1; i++){
            for (int j = 0; j < temp[i].length; j++){
                if(temp[i][j] == null){
                    dateRange[i][j] = null;
                }
                else{
                    dateRange[i][j] = temp[i][j];
                }
            }
        }

        //get a new array for store data from start date to end date
        for(int i = 0; i < dateRange.length; i++){
            for (int j = 1; j < dateRange[i].length; j++){
                if(dateRange[i][j] != null){
                    String[] allAmountAndValue = dateRange[i][j].split("; ");

                    for(int k = 0; k < allAmountAndValue.length; k++){
                        String[] singleAmountAndValue = allAmountAndValue[k].split(" - ");

                        if(singleAmountAndValue[0].equalsIgnoreCase("CARBOHYDRATE, TOTAL (BY DIFFERENCE)")){
                            carb += Double.parseDouble(singleAmountAndValue[1]);
                        }
                        else if(singleAmountAndValue[0].equalsIgnoreCase("FAT (TOTAL LIPIDS)")){
                            fat += Double.parseDouble(singleAmountAndValue[1]);
                        }
                        else if(singleAmountAndValue[0].equalsIgnoreCase("PROTEIN")){
                            protein += Double.parseDouble(singleAmountAndValue[1]);
                        }
                        else if(singleAmountAndValue[0].equalsIgnoreCase("GLUCOSE")){
                            glucose += Double.parseDouble(singleAmountAndValue[1]);
                        }
                        else if(singleAmountAndValue[0].equalsIgnoreCase("VITAMIN C")){
                            vitaminC += Double.parseDouble(singleAmountAndValue[1]);
                        }
                        else{
                            other += Double.parseDouble(singleAmountAndValue[1]);
                        }
                    }
                }
            }
        }

        return getAverage(carb, fat, protein, glucose, vitaminC, other, data, length);
    }

    /**
     * This class gets the data set for creating the chart
     * @param NumberOfNutrients NumberOfNutrients is the number of nutrients
     */
    public static String[][] getData(int NumberOfNutrients,String startDate,String endDate){
        return NutrientsDataReader(NumberOfNutrients,startDate,endDate); //change here
    }
}
