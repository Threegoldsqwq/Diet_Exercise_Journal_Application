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
 * The CalorieDataVisualizer class generates a JFreeChart for visualizing exercise data, specifically calorie intake and burned calories.
 * It provides methods to create a bar chart based on the specified date range and displays the chart using JFreeChart's components.
 * The data for the chart is obtained from the RuntimeDatabase.
 *
 * @version 1.0
 */
public class CalorieDataVisualizer {
    static CategoryDataset dataset;

    /**
     * Generates a bar chart for exercise data within the specified date range and displays it using JFreeChart.
     *
     * @param startDate the start date of the date range
     * @param endDate   the end date of the date range
     * @throws ParseException if there is an error parsing the date strings
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
     * Retrieves exercise data from the RuntimeDatabase for the specified date range and prepares the dataset for chart creation.
     *
     * @param startDate the start date of the date range
     * @param endDate   the end date of the date range
     * @throws ParseException if there is an error parsing the date strings
     */
    public static void getData(String startDate, String endDate) throws ParseException {
        String[][] CBurned;
        String[][] Cintake;
        //get the data
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

        //compare the length, choose the bigger one
        int length = 0;
        if(Cintake.length > CBurned.length){
            length = Cintake.length;
        }
        else if(Cintake.length < CBurned.length){
            length = CBurned.length;
        }
        else{
            length = CBurned.length;
        }

        //read data into the data sheet for chart
        //could be optimized
        double[][] data = new double[2][Length];
        String[] columnKeys = new String[Length];
        int k=0;
        int i;
        for (i=0;i<length;i++){
            if (Objects.equals(String.valueOf(Cintake[i][0]), startDate)){
                while (!Objects.equals(String.valueOf(Cintake[i][0]), endDate)) {

                    //Handle the situation the length of two set of data is not equal
                    if(i > CBurned.length - 1 && i < length){
                        data[1][k] = 0;
                        data[0][k]= Double.parseDouble(Cintake[i][1]);
                    }
                    else if(i > Cintake.length - 1 && i < length){
                        data[0][k] = 0;
                        data[1][k]= Double.parseDouble(CBurned[i][1]);
                    }
                    else if(Cintake[i][1] == null){
                        data[0][k] = 0;
                    }
                    else if(CBurned[i][1] == null){
                        data[1][k] = 0;
                    }
                    else{
                        data[0][k]= Double.parseDouble(Cintake[i][1]);
                        data[1][k]= Double.parseDouble(CBurned[i][1]);
                    }

                    columnKeys[k]= String.valueOf(Cintake[i][0]);
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
