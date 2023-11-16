package NewUi;

// CreateProfileUI.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CreateProfilePage {
    private JPanel panel;
    private JTextField nameField;
    private JTextField dobField;
    private JTextField genderField;
    private JTextField weightField;
    private JTextField heightField;
    private JComboBox<String> measurementComboBox;

    public CreateProfilePage(ActionListener saveButtonListener, ActionListener backButtonListener) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.setBackground(new Color(187, 201, 211, 255));

        nameField = new JTextField();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        dobField = new JTextField();
        panel.add(new JLabel("Date of Birth(xxxx/xx/xx):"));
        panel.add(dobField);

        genderField = new JTextField();
        panel.add(new JLabel("Gender(M/F):"));
        panel.add(genderField);

        weightField = new JTextField();
        panel.add(new JLabel("Weight(cm/feet):"));
        panel.add(weightField);

        heightField = new JTextField();
        panel.add(new JLabel("Height(kg/lb):"));
        panel.add(heightField);

        String[] measurementOptions = {"Metric", "Imperial"};
        measurementComboBox = new JComboBox<>(measurementOptions);
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

    public JPanel getPanel() {
        return panel;
    }

    public String getName() {
        return nameField.getText();
    }

    public String getDOB() {
        return dobField.getText();
    }

    public String getGender() {
        return genderField.getText();
    }

    public String getWeight() {
        return weightField.getText();
    }

    public String getHeight() {
        return heightField.getText();
    }

    public String getMeasurement() {
        return (String) measurementComboBox.getSelectedItem();
    }

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


