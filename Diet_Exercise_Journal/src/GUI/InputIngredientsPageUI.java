package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InputIngredientsPageUI {
    private JPanel panel;
    private JTextField ingredientTextField;
    private JTextField quantityTextField;
    private JButton addButton;
    private JButton backToPreviousButton;

    public InputIngredientsPageUI(ActionListener addButtonListener, ActionListener backToPreviousButtonListener) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        ingredientTextField = new JTextField();
        ingredientTextField.setBorder(BorderFactory.createTitledBorder("Ingredient"));
        panel.add(ingredientTextField);

        quantityTextField = new JTextField();
        quantityTextField.setBorder(BorderFactory.createTitledBorder("Quantity"));
        panel.add(quantityTextField);

        addButton = new JButton("Add");
        addButton.addActionListener(addButtonListener);
        panel.add(addButton);

        backToPreviousButton = new JButton("Back to Previous Page");
        backToPreviousButton.addActionListener(backToPreviousButtonListener);
        panel.add(backToPreviousButton);
        addComponentListener();
    }

    public String getIngredient() {
        return ingredientTextField.getText();
    }

    public String getQuantity() {
        return quantityTextField.getText();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void addComponentListener() {
        ComponentAdapter componentAdapter = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize();
            }
        };

        // Attach the ComponentListener to the panel
        panel.addComponentListener(componentAdapter);
    }

    private void adjustFontSize() {
        // Adjust font size based on the height of the panel
        int fontSize = panel.getHeight() / 25; // Adjust the divisor as needed

        // Apply the adjusted font size to all buttons and text fields
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton || component instanceof JTextField ) {
                JComponent jComponent = (JComponent) component;
                Font updatedFont = jComponent.getFont().deriveFont(Font.BOLD, fontSize);
                jComponent.setFont(updatedFont);
                if (jComponent.getBorder() instanceof TitledBorder) {
                    TitledBorder titledBorder = (TitledBorder) jComponent.getBorder();
                    titledBorder.setTitleFont(updatedFont);
                }
            }

        }
    }
}
