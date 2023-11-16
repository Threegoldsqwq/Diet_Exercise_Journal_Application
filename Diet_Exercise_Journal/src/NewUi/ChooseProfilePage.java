package NewUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseProfilePage {
    private JPanel panel;
    private JList<String> userList;
    private JButton backButton;
    public ChooseProfilePage(String[] users, ActionListener backListener, ActionListener selectListener) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        userList = new JList<>(users);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add a mouse listener to handle item selection
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click to select
                    int selectedIndex = userList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        // Trigger the selectListener with the selected item
                        selectListener.actionPerformed(null);
                    }
                }
            }
        });

        panel.add(new JScrollPane(userList), BorderLayout.CENTER);

        //add button
        backButton = new JButton("Back");
        backButton.addActionListener(backListener);
        panel.add(backButton, BorderLayout.SOUTH);
        //import realtime reschedule panel
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
                userList.setForeground(new Color(24, 115, 189));
                userList.setFont(updatedFont);
            }
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    // Getter method to retrieve the selected user
    public String getSelectedUser() {
        return userList.getSelectedValue();
    }
}