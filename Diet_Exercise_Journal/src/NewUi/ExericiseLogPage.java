package NewUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
/**
 * Represents a graphical user interface for logging exercise information.
 * Users can input the date, exercise type, intensity, and length for a specific exercise.
 * The logged information can be retrieved using the provided getter methods.
 */
public class ExericiseLogPage {
    private JTextField exerciseTypeField, lenthField, date;
    private JComboBox<String> intensity;
    private JPanel panel;
    /**
     * Constructs an ExerciseLogPage with ActionListener for the "Log Exercise" button
     * and the "Back" button.
     *
     * @param saveButtonListener ActionListener for the "Log Exercise" button.
     * @param backButtonListener ActionListener for the "Back" button.
     */
    public ExericiseLogPage(ActionListener saveButtonListener, ActionListener backButtonListener) {
        //set panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBackground(new Color(187, 201, 211, 255));

        // UI components
        exerciseTypeField = new JTextField(10);
        intensity = new JComboBox<>(new String[]{"very low", "low", "medium", "high", "very high"});
        lenthField = new JTextField(15);
        date = new JTextField(10);

        // Buttons
        JButton logButton = new JButton("Log Exercise");
        logButton.addActionListener(saveButtonListener);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(backButtonListener);

        // Layout
        panel.add(new JLabel("Date:"));
        panel.add(date);
        panel.add(new JLabel("Exercise Type:"));
        panel.add(exerciseTypeField);
        panel.add(new JLabel("intensity"));
        panel.add(intensity);
        panel.add(new JLabel("Length:"));
        panel.add(lenthField);
        panel.add(logButton);
        panel.add(backButton);

        //active componetListener
        addComponentListener();

    }
    /**
     * Gets the JPanel representing the ExerciseLogPage.
     *
     * @return The JPanel representing the ExerciseLogPage.
     */
    public JPanel getPanel() {
        return panel;
    }
    /**
     * Gets the entered date from the date field.
     *
     * @return The entered date.
     */
    public String getDate() {
        return date.getText();
    }
    /**
     * Gets the selected intensity from the intensity combo box.
     *
     * @return The selected intensity.
     */
    public String getIntensity() {
        return (String) intensity.getSelectedItem();
    }
    /**
     * Gets the entered length from the length field.
     *
     * @return The entered length.
     */
    public String getLength() {
        return lenthField.getText();
    }
    /**
     * Gets the entered exercise type from the exercise type field.
     *
     * @return The entered exercise type.
     */
    public String getExerciseType() {
        return exerciseTypeField.getText();
    }

    /**
     * Adds a ComponentListener to adjust the font size based on the panel's height.
     */
    private void addComponentListener() {
        ComponentAdapter componentAdapter = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFont();
            }
        };
        // Attach the ComponentListener to the panel
        panel.addComponentListener(componentAdapter);
    }
    /**
     * Adjusts the font size and color of buttons, text fields, and labels based on the panel's size.
     */
    private void adjustFont() {
        // Adjust font size based on the height of the panel
        int fontSize = Math.min(panel.getHeight() / 25, panel.getWidth() / 25);// Adjust the divisor as needed
        System.out.println(panel.getWidth());
        // Apply the adjusted font size to all buttons
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                Font updatedFont = button.getFont().deriveFont(Font.BOLD, fontSize);
                button.setForeground(new Color(255, 255, 255));
                button.setBackground(new Color(0x1A77BD));
                button.setFont(updatedFont);
            }
        }
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                Font updatedFont = textField.getFont().deriveFont(Font.BOLD, fontSize);
                textField.setForeground(new Color(0, 0, 0));
                textField.setFont(updatedFont);
            }
        }
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                Font updatedFont = label.getFont().deriveFont(Font.BOLD, fontSize);
                label.setForeground(new Color(0, 0, 0));
                label.setFont(updatedFont);
            }
        }
    }
}


