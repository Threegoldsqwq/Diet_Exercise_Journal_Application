package Main_Classes;
import Operator.DietDataOperator;
import Visualizer.DietDataVisualizer;
import Visualizer.ExerciseDataVisualizer;
import org.jfree.data.category.CategoryDataset;

import Visualizer.DataVisualizer;
import org.jfree.data.general.DatasetUtilities;
/**
 * Testing main classes for you to run
 * Will be changed to real implementation in the future
 * Will implement real JUnit test cases in the future
 */
public class JFreeChartVisualizationMainClass {
    public static void main(String[] args) {
        ExerciseDataVisualizer.getChart();
        DietDataVisualizer.getChart(5);
    }
}
