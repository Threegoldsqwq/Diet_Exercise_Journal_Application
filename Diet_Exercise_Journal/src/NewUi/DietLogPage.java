package NewUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
/**
 * Represents a graphical user interface for logging diet information.
 * Users can input the date, meal type, ingredient, and quantity for a specific meal.
 * The logged information can be retrieved using the provided getter methods.
 */
public class DietLogPage {
    private JTextField dateField, ingredientField, quantityField;
    private JComboBox<String> mealTypeComboBox;
    private JPanel panel;
    /**
     * Constructs a DietLogPage with ActionListener for the "Log Meal" button
     * and the "Back" button.
     *
     * @param saveButtonListener ActionListener for the "Log Meal" button.
     * @param backButtonListener ActionListener for the "Back" button.
     */
    public DietLogPage(ActionListener saveButtonListener, ActionListener backButtonListener) {
        //set panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBackground(new Color(187, 201, 211, 255));

        // UI components
        dateField = new JTextField(10);
        mealTypeComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner", "Snack"});
        ingredientField = new JTextField(15);
        quantityField = new JTextField(5);

        // Buttons
        JButton logButton = new JButton("Log Meal");
        logButton.addActionListener(saveButtonListener);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(backButtonListener);

        // Layout
        panel.add(new JLabel("Date:(yyyy-mm-dd)"));
        panel.add(dateField);
        panel.add(new JLabel("Meal Type:"));
        panel.add(mealTypeComboBox);
        panel.add(new JLabel("Ingredient:"));
        panel.add(ingredientField);
        panel.add(new JLabel("Quantity:(in Gram/ML,Egg in amount)"));
        panel.add(quantityField);
        panel.add(logButton);
        panel.add(backButton);

        //active componentListener
        addComponentListener();
    }
    /**
     * Gets the JPanel representing the DietLogPage.
     *
     * @return The JPanel representing the DietLogPage.
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
        return dateField.getText();
    }
    /**
     * Gets the selected meal type from the meal type combo box.
     *
     * @return The selected meal type.
     */
    public String getMealType() {
        return (String) mealTypeComboBox.getSelectedItem();
    }
    /**
     * Gets the entered ingredient from the ingredient field.
     *
     * @return The entered ingredient.
     */
    public String getIngredient() {
        return ingredientField.getText();
    }
    /**
     * Gets the entered quantity from the quantity field.
     *
     * @return The entered quantity.
     */
    public String getQuantity() {
        return quantityField.getText();
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