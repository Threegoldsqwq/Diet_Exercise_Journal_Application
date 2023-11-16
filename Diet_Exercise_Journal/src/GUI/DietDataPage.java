package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class DietDataPage {
    private JPanel panel;
    private JTextField usernameField;
    private JTextField userIdField;

    public DietDataPage(ActionListener backButtonListener) {
        JFrame frame = new JFrame("Data Sheet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Assuming you have some data to display
        List<String> data = Arrays.asList("Data 1", "Data 2", "Data 3");

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Data");

        // Add data to the table model
        for (String item : data) {
            tableModel.addRow(new Object[]{item});
        }

        // Create a JTable with the table model
        JTable table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        frame.add(scrollPane);

        // Set frame properties
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(backButtonListener);
        panel.add(backButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}

