package Visualizer;

import Core.RuntimeDatabase;

import jdk.dynalink.beans.StaticClass;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

import java.lang.reflect.Array;


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



