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
import org.jfree.ui.RefineryUtilities;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * This class generate JFree chart for exercise
 */
public class CalorieDataVisualizer {
    static CategoryDataset dataset;

    /**
     * This method generate bar chart
     */
    public static void getChart(String startDate, String endDate) throws ParseException {
        getData(startDate,endDate);
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
        RefineryUtilities.centerFrameOnScreen(chartFrame);
        chartFrame.setVisible(true);
    }

    /**
     * This method get the data set for creating the chart
     */
    public static void getData(String startDate, String endDate) throws ParseException {
        String[][] CBurned;
        String[][] Cintake;
        CBurned=RuntimeDatabase.CaloryBurnedDataReader();
        Cintake=RuntimeDatabase.CaloryIntakeDataReader();

        //deal with date range
        //changed
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the start and end dates
        LocalDate startDateObj = LocalDate.parse(startDate, dateFormatter);
        LocalDate endDateObj = LocalDate.parse(endDate, dateFormatter);

        // Calculate the difference in days
        int Length= (int) ChronoUnit.DAYS.between(startDateObj, endDateObj);


        //read data into the data sheet for chart
        //could be optimized
        double[][] data = new double[2][Length];
        String[] columnKeys = new String[Length];
        int k=0;
        int i;
        for (i=0;i<CBurned.length;i++){
            if (Objects.equals(String.valueOf(Cintake[i][0]), startDate)){
                while (!Objects.equals(String.valueOf(Cintake[i][0]), endDate)) {
                    data[0][k]= Double.parseDouble(Cintake[i][1]);
                    data[1][k]= Double.parseDouble(CBurned[i][1]);
                    columnKeys[k]= String.valueOf(Cintake[i][0]);
                    System.out.println(Double.parseDouble(Cintake[i][1]));
                    k++;
                    i++;
                }
            }
        }

        //set chart
        String[] rowKeys = {"Intake", "Burned"};
        dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
    }

}
