package OutcomeGenerator;

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

        CategoryDataset dataset = createDataset(userIntake, cfgRecommendations);
        JFreeChart chart = ChartFactory.createBarChart(
                "Diet Comparison",
                "Food Groups",
                "Percentage",
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

    private CategoryDataset createDataset(double[] userIntake, double[] cfgRecommendations) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String series = "User's Intake";
        for (int i = 0; i < userIntake.length; i++) {
            dataset.addValue(userIntake[i], series, "Food Group " + (i + 1));
        }

        series = "CFG Recommendations";
        for (int i = 0; i < cfgRecommendations.length; i++) {
            dataset.addValue(cfgRecommendations[i], series, "Food Group " + (i + 1));
        }

        return dataset;
    }

    public void displayChart() {
        SwingUtilities.invokeLater(() -> {
            setSize(800, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        });
    }
}
