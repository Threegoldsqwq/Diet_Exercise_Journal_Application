package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputDataPageUI {
    private JPanel panel;
    private JButton backButton;

    public InputDataPageUI(
            ActionListener breakfastListener,
            ActionListener lunchListener,
            ActionListener dinnerListener,
            ActionListener snackListener,
            ActionListener exerciseListener,
            ActionListener backButtonListener
    ) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(34, 40, 49)); // Dark Blue-Gray color

        addButton("Breakfast", breakfastListener);
        addButton("Lunch", lunchListener);
        addButton("Dinner", dinnerListener);
        addButton("Snack", snackListener);
        addButton("Exercise", exerciseListener);

        addSpacing(20);

        addButton("Back", backButtonListener);


        // Add a ComponentListener to the frame to handle resizing
        addComponentListener();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void addButton(String text, ActionListener listener) {
        JButton button = createButton(text, listener);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        panel.add(button);
        addSpacing(10);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setBackground(new Color(75, 92, 107)); // Darker Blue-Gray color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Helvetica", Font.PLAIN, 16));
        return button;
    }

    private void addSpacing(int height) {
        panel.add(Box.createRigidArea(new Dimension(0, height)));
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

        // Apply the adjusted font size to all buttons
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                Font updatedFont = button.getFont().deriveFont(Font.PLAIN, fontSize);
                button.setFont(updatedFont);
            }
        }
    }
}
