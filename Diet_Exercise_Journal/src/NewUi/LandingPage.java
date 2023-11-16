package NewUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LandingPage {
    private static JPanel panel;
    private JButton createProfileButton;
    private JButton chooseProfileButton;

    public LandingPage(ActionListener createProfileListener, ActionListener chooseProfileListener) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 1, 1));

        createProfileButton = new JButton("Create Profile");
        createProfileButton.addActionListener(createProfileListener);
        panel.add(createProfileButton);

        chooseProfileButton = new JButton("Choose Profile");
        chooseProfileButton.addActionListener(chooseProfileListener);
        panel.add(chooseProfileButton);

        addComponentListener();
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
        int fontSize = Math.max(panel.getHeight() / 25, panel.getWidth()/25 );// Adjust the divisor as needed
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
    }

    public JPanel getPanel() {
        return panel;
    }
}
