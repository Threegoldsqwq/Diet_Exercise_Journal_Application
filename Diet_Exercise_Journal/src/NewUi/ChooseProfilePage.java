package NewUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents a graphical user interface for choosing a user profile from a list.
 * The user can select a profile by clicking on the list item or by double-clicking
 * to trigger a selection event. The selected user can be retrieved using the
 * {@link #getSelectedUser()} method.
 */
public class ChooseProfilePage {
    private JPanel panel;
    private JList<String> userList;
    private JButton backButton;

    /**
     * Constructs a ChooseProfilePage with the given user names, back button listener,
     * and select button listener.
     *
     * @param users          An array of user names to be displayed in the list.
     * @param backListener   ActionListener for the "Back" button.
     * @param selectListener ActionListener for the user selection event.
     */
    public ChooseProfilePage(String[] users, ActionListener backListener, ActionListener selectListener) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //create Jlist
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

    //make panel looks nicer
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
     * Adjusts the font size of buttons and the user list based on the height of the panel.
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
                userList.setForeground(new Color(24, 115, 189));
                userList.setFont(updatedFont);
            }
        }
    }

    /**
     * Gets the panel containing the user list and back button.
     *
     * @return The JPanel representing the ChooseProfilePage.
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Gets the selected user from the user list.
     *
     * @return The selected user name.
     */
    public String getSelectedUser() {
        return userList.getSelectedValue();
    }
}