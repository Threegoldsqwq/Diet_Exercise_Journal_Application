package NewUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
/**
 * Represents a graphical user interface for displaying detailed meal information.
 * Users can view the ingredients, amounts, nutrients, and their amounts for a selected meal.
 */
public class DetailedMealPage {
    private JPanel panel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel topPanel=new JPanel();
    private JList<String> mealList=new JList<>();
    private JList<String> nutrisionList=new JList<>();
    private JButton backButton;
    /**
     * Constructs a DetailedMealPage with a list of meal ingredients and amounts,
     * a list of nutrients and their amounts, and an ActionListener for the "Back" button.
     *
     * @param meal           An array of meal ingredients and amounts to be displayed.
     * @param nutrision      An array of nutrients and their amounts to be displayed.
     * @param backListener   ActionListener for the "Back" button.
     */
    public DetailedMealPage(
            String[] meal,
            String[] nutrision,//modify here
            ActionListener backListener
    ) {
        //create panel
        panel.setLayout(new BorderLayout());

        mealList = new JList<>(meal);
        nutrisionList= new JList<>(nutrision);

        topPanel.setLayout(new GridLayout(1, 2, 1, 10));

        topPanel.add(new JLabel("Ingredients and amount"));
        topPanel.add(new JLabel("Nutrients and amount"));

        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(new JScrollPane(mealList), BorderLayout.CENTER);
        leftPanel.add(mealList);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(new JScrollPane(nutrisionList), BorderLayout.CENTER);
        rightPanel.add(nutrisionList);

        backButton = new JButton("Back");
        backButton.addActionListener(backListener);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel,BorderLayout.EAST);
        panel.add(backButton,BorderLayout.SOUTH);
        panel.add(topPanel,BorderLayout.NORTH);
        addComponentListener();
    }

    /**
     * Adds a ComponentListener to adjust the font size and panel dimensions based on resizing.
     */
    private void addComponentListener() {
        ComponentAdapter componentAdapter = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFont();
                adjustPanel();
            }
        };

        // Attach the ComponentListener to the panel
        panel.addComponentListener(componentAdapter);
    }

    /**
     * Adjusts the panel dimensions based on the top panel's width.
     */
    private void adjustPanel(){
        leftPanel.setPreferredSize(new Dimension(topPanel.getWidth()/2, 1)); // Adjust the width as needed
        rightPanel.setPreferredSize(new Dimension(topPanel.getWidth()/2, 1));
    }
    /**
     * Adjusts the font size, color, and other properties of UI elements based on the panel's size.
     */
    private void adjustFont() {
        // Adjust font size based on the height of the panel
        int fontSize = Math.min(panel.getHeight() / 25, panel.getWidth() / 40); // Adjust the divisor as needed
        // Adjust color
        mealList.setBackground(new Color(187, 201, 211, 255));
        mealList.setBorder(BorderFactory.createLineBorder(Color.white));
        nutrisionList.setBorder(BorderFactory.createLineBorder(Color.white));
        nutrisionList.setBackground(new Color(187, 201, 211, 255));
        topPanel.setBackground(new Color(0x1A77BD));
        backButton.setForeground(new Color(255, 255, 255));
        backButton.setBackground(new Color(0x1A77BD));
        // Apply the adjusted font size to all elements
        Font updatedFont = mealList.getFont().deriveFont(Font.BOLD, fontSize);
        mealList.setFont(updatedFont);
        mealList.setForeground(new Color(24, 115, 189));
        nutrisionList.setFont(updatedFont);
        nutrisionList.setForeground(new Color(24, 115, 189));
        backButton.setFont(updatedFont);

        Component[] components1 = topPanel.getComponents();
        for (Component component : components1) {
            if (component instanceof JLabel label) {
                label.setForeground(new Color(255, 255, 255));
                label.setBackground(new Color(0x1A77BD));
                label.setBorder(BorderFactory.createLineBorder(Color.white));
                label.setFont(updatedFont);
            }
        }
    }

    /**
     * Gets the JPanel representing the DetailedMealPage.
     *
     * @return The JPanel representing the DetailedMealPage.
     */
    public JPanel getPanel() {
        return panel;
    }
}