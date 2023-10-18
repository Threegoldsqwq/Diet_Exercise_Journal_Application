package Visualizer;

import DatabaseOperation.RuntimeDatabase;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 * This class generate JFree chart for exercise
 */
public class ExerciseDataVisualizer {
    static String[][] CBurned;
    static String[][] Cintake;
    static CategoryDataset dataset;

    /**
     * This method generate bar chart
     */
    public static void getChart() {
        getDataSet();
        JFreeChart chart = ChartFactory.createBarChart(
                "Exercise Data", //title
                "Date",
                "Calory",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer3D renderer = new BarRenderer3D();

        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);  //show numbers


        renderer.setItemMargin(0.1); //adjust margin
        plot.setRenderer(renderer);

        //display using awt
        ChartFrame chartFrame = new ChartFrame("Test", chart);
        chartFrame.pack();
        chartFrame.setVisible(true);
    }

    /**
     * This method get the data set for creating the chart
     */
    public static void getDataSet(){
        CBurned=RuntimeDatabase.CaloryBurnedDataReader();
        Cintake=RuntimeDatabase.CaloryIntakeDataReader();

        double[][] data = new double[2][CBurned.length];
        String[] columnKeys = new String[CBurned.length];

        int i,j;
        for (i=0;i<CBurned.length;i++){
            data[0][i]= Double.parseDouble(Cintake[i][1]);
            data[1][i]= Double.parseDouble(CBurned[i][1]);
            columnKeys[i]= String.valueOf(Cintake[i][0]);
        }


        String[] rowKeys = {"Intake", "Burned"};
        dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
    }

}
