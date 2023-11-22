package OutcomeGenerator;

import Facade.NutritionServiceFacade;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class CFG extends JFrame {
    public CFG(String title, double[] userIntake, double[] cfgRecommendations) {
        super(title);
        NutritionServiceFacade facade = new NutritionServiceFacade();
        CategoryDataset dataset = createDataset(facade.getTotalFoodGroupIntake(), cfgRecommendations);
        JFreeChart chart = ChartFactory.createBarChart(
                "Diet Comparison",
                "Food Groups",
                "Quantity",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        setContentPane(chartPanel);
    }

    public static double[] getCFGRecommends(double[] meal){
        NutritionServiceFacade facade = new NutritionServiceFacade();
        double total = 0;
        double[] recommend = new double[3];
        for(int i = 0; i < facade.getTotalFoodGroupIntake().length; i++){
            total = total + facade.getTotalFoodGroupIntake()[i];
        }
        recommend[0] = total / 2;
        recommend[1] = total / 4;
        recommend[2] = total / 4;
        return recommend;
    }

    private CategoryDataset createDataset(double[] userIntake, double[] cfgRecommendations) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String[] columnName = {"Vegetables", "Protein", "Whole Grain"};
        String series = "User's Intake";
        for (int i = 0; i < userIntake.length; i++) {
            dataset.addValue(userIntake[i], series, columnName[i]);
        }

        series = "CFG Recommendations";
        for (int i = 0; i < cfgRecommendations.length; i++) {
            dataset.addValue(cfgRecommendations[i], series, columnName[i]);
        }

        return dataset;
    }

    public void displayChart() {
        SwingUtilities.invokeLater(() -> {
            setSize(800, 600);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }


}
