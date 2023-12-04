package Visualizer;

import java.text.ParseException;

/**
 * interface for visualize data
 */
public interface DataVisualizer {
  default void getChart() throws ParseException {
  }

  default void getData() throws ParseException {
  }
}