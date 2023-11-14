package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LandingPageUI {
    private JPanel panel;
    private JButton createProfileButton;
    private JButton chooseProfileButton;

    public LandingPageUI(ActionListener createProfileListener, ActionListener chooseProfileListener) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        createProfileButton = new JButton("Create Profile");
        createProfileButton.addActionListener(createProfileListener);
        panel.add(createProfileButton);

        chooseProfileButton = new JButton("Choose Profile");
        chooseProfileButton.addActionListener(chooseProfileListener);
        panel.add(chooseProfileButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
