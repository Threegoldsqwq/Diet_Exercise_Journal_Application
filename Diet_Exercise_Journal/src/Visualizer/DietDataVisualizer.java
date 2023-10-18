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
    static DefaultPieDataset dataset = new DefaultPieDataset();
    static String[][] Data;

    /**
     * This method create pie chart
     * @param NumberOfNutrients is the number of nutrients
     */
    public static void getChart(int NumberOfNutrients) {
        getDataSet(NumberOfNutrients);
                // create a dataset...
                DefaultPieDataset dataset = new DefaultPieDataset();

                for (int i=0;i<NumberOfNutrients+1;i++){
                    dataset.setValue(Data[i][0], Double.parseDouble(Data[i][1]));
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
    public static void getDataSet(int NumberOfNutrients){
        Data=RuntimeDatabase.NutrientsDataReader(NumberOfNutrients);
    }
}



