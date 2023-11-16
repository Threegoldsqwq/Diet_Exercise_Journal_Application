package NewUi;

// CreateProfileUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        panel.setLayout(new GridLayout(10, 2, 10, 10));

        nameField = new JTextField();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        dobField = new JTextField();
        panel.add(new JLabel("Date of Birth:"));
        panel.add(dobField);

        genderField = new JTextField();
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);

        weightField = new JTextField();
        panel.add(new JLabel("Weight:"));
        panel.add(weightField);

        heightField = new JTextField();
        panel.add(new JLabel("Height:"));
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
}

