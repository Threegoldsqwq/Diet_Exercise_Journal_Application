package Main_Classes;
import Operator.DietDataOperator;
import Visualizer.DietDataVisualizer;
import Visualizer.ExerciseDataVisualizer;
import org.jfree.data.category.CategoryDataset;

import Visualizer.DataVisualizer;
import org.jfree.data.general.DatasetUtilities;
public class JFreeChartVisualizationMainClass {
    public static void main(String[] args) {
        ExerciseDataVisualizer.getChart();
        DietDataVisualizer.getChart(5);

    }
}
