package Visualizer;

import DatabaseOperation.RuntimeDatabase;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;


public class DietDataVisualizer extends DataVisualizer{
    static DefaultPieDataset dataset = new DefaultPieDataset();
    static String[][] Data;

    public static void getChart(int NumberOfNutrients) {
        getDataSet(NumberOfNutrients);
                // create a dataset...
                DefaultPieDataset dataset = new DefaultPieDataset();

                for (int i=0;i<NumberOfNutrients+1;i++){
                    dataset.setValue(Data[i][0], Double.parseDouble(Data[i][1]));
                }


                // create a chart...
                JFreeChart chart = ChartFactory.createPieChart(
                        "Sample Pie Chart -Yves", // chart title: 图表标题
                        dataset, // data: 数据集
                        true, // legend: 图例
                        true, // tooltips: 图表工具条
                        false // URLs: URLS
                );

                // create and display a frame...
                ChartFrame frame = new ChartFrame("First", chart);
                frame.pack();
                RefineryUtilities.centerFrameOnScreen(frame);
                frame.setVisible(true);
    }

    public static void getDataSet(int NumberOfNutrients){
        Data=RuntimeDatabase.NutrientsDataReader(NumberOfNutrients);
    }
}



