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
            dataset.setValue(geData(NumberOfNutrients,startDate,endDate)[i][0], Double.parseDouble(geData(NumberOfNutrients,startDate,endDate)[i][1]));
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

    /**
     * This class gets the data set for creating the chart
     * @param NumberOfNutrients NumberOfNutrients is the number of nutrients
     */
    public static String[][] geData(int NumberOfNutrients,String startDate,String endDate){
        return RuntimeDatabase.NutrientsDataReader(NumberOfNutrients,startDate,endDate); //change here
    }
}



