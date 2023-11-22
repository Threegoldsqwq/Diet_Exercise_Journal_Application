package NewUi;

// CreateProfileUI.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Represents a graphical user interface for creating a user profile.
 * Users can input their personal information, such as name, date of birth,
 * gender, weight, height, and measurement system. The information can be
 * retrieved using the provided getter methods.
 */
public class CreateProfilePage {
    private JPanel panel;
    private JTextField nameField;
    private JTextField dobField;
    private JTextField genderField;
    private JTextField weightField;
    private JTextField heightField;
    private JComboBox<String> measurementComboBox;

    /**
     * Constructs a CreateProfilePage with ActionListener for the "Save" button
     * and the "Back" button.
     *
     * @param saveButtonListener   ActionListener for the "Save" button.
     * @param backButtonListener   ActionListener for the "Back" button.
     */
    public CreateProfilePage(ActionListener saveButtonListener, ActionListener backButtonListener) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.setBackground(new Color(187, 201, 211, 255));

        //UI components
        nameField = new JTextField();
        dobField = new JTextField();
        genderField = new JTextField();
        weightField = new JTextField();
        heightField = new JTextField();
        String[] measurementOptions = {"Metric", "Imperial"};
        measurementComboBox = new JComboBox<>(measurementOptions);

        //layout
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date of Birth(xxxx-xx-xx):"));
        panel.add(dobField);
        panel.add(new JLabel("Gender(M/F):"));
        panel.add(genderField);
        panel.add(new JLabel("Weight(kg/lb):"));
        panel.add(weightField);
        panel.add(new JLabel("Height(cm/feet):"));
        panel.add(heightField);
        panel.add(new JLabel("Measurement:"));
        panel.add(measurementComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(saveButtonListener);
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(backButtonListener);
        panel.add(backButton);
        addComponentListener();
    }

    /**
     * Gets the JPanel representing the CreateProfilePage.
     *
     * @return The JPanel representing the CreateProfilePage.
     */
    public JPanel getPanel() {
        return panel;
    }
    /**
     * Gets the user's name from the name field.
     *
     * @return The user's name.
     */
    public String getName() {
        return nameField.getText();
    }
    /**
     * Gets the user's date of birth from the DOB field.
     *
     * @return The user's date of birth.
     */
    public String getDOB() {
        return dobField.getText();
    }
    /**
     * Gets the user's gender from the gender field.
     *
     * @return The user's gender.
     */
    public String getGender() {
        return genderField.getText();
    }
    /**
     * Gets the user's weight from the weight field.
     *
     * @return The user's weight.
     */
    public String getWeight() {
        return weightField.getText();
    }
    /**
     * Gets the user's height from the height field.
     *
     * @return The user's height.
     */
    public String getHeight() {
        return heightField.getText();
    }
    /**
     * Gets the selected measurement system from the measurement combo box.
     *
     * @return The selected measurement system (Metric or Imperial).
     */
    public String getMeasurement() {
        return (String) measurementComboBox.getSelectedItem();
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
     * Adjusts the font size of buttons, text fields, and labels based on the height of the panel.
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


