package GUI;// ProfileOptionsUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileOptionsUI {
    private JPanel panel;
    private JButton inputTodayDataButton;
    private JButton dietDataButton;
    private JButton calorieDataButton;
    private JButton editProfileButton;

    public ProfileOptionsUI(
            ActionListener dietDataListener,
            ActionListener calorieDataListener,
            ActionListener inputTodayDataListener,
            ActionListener editProfileListener
    ) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        dietDataButton = new JButton("Diet Data");
        dietDataButton.addActionListener(dietDataListener);
        panel.add(dietDataButton);

        calorieDataButton = new JButton("Calorie Data");
        calorieDataButton.addActionListener(calorieDataListener);
        panel.add(calorieDataButton);

        inputTodayDataButton = new JButton("Input Today's Data");
        inputTodayDataButton.addActionListener(inputTodayDataListener);
        panel.add(inputTodayDataButton);

        editProfileButton = new JButton("Edit Profile");
        editProfileButton.addActionListener(editProfileListener);
        panel.add(editProfileButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
