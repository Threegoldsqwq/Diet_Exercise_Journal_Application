package NewUi;// ChooseProfileUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChooseProfilePage {
    private JPanel panel;
    private JTextField usernameField;
    private JTextField userIdField;

    public ChooseProfilePage(ActionListener chooseButtonListener) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        usernameField = new JTextField();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        userIdField = new JTextField();
        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);

        JButton chooseButton = new JButton("Choose Profile");
        chooseButton.addActionListener(chooseButtonListener);
        panel.add(chooseButton);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getUserId() {
        return userIdField.getText();
    }
}
