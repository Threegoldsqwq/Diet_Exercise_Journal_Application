package NewUi;

import javax.swing.*;
import java.awt.*;

public class DietLog {
    private JTextField dateField, ingredientField, quantityField;
    private JComboBox<String> mealTypeComboBox;
    private JPanel panel=new JPanel();

    public DietLog() {
        // UI components
        dateField = new JTextField(10);
        mealTypeComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner", "Snack"});
        ingredientField = new JTextField(15);
        quantityField = new JTextField(5);


        // Buttons
        JButton logButton = new JButton("Log Meal");
        JButton viewButton = new JButton("View Journal");

        // Layout
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Meal Type:"));
        inputPanel.add(mealTypeComboBox);
        inputPanel.add(new JLabel("Ingredient:"));
        inputPanel.add(ingredientField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logButton);
        buttonPanel.add(viewButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

    }

    public JPanel getPanel(){
        return panel;
    }
}