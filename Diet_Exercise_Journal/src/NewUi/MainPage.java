package NewUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents the main page of the application with various buttons and information.
 */
public class MainPage {
    private JPanel panel =new JPanel();
    private JPanel RightPanel;
    private JPanel LeftPanel;
    private JPanel topPanel;
    private JLabel timeLabel;
    private int D=10; //date we want to show

    /**
     * Constructs a MainPage with ActionListeners for various buttons.
     *
     * @param buttonListeners ActionListeners for the buttons on the main page.
     */
    public MainPage(
            ActionListener[] buttonListeners
    ) {

        // Create a Timer that updates the time every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
        //create sub panel
        topPanel=new JPanel(new BorderLayout());
        RightPanel =new JPanel(new GridLayout(buttonListeners.length, 1, 10, 0));
        LeftPanel=new JPanel(new GridLayout(D+1, 1, 10, 0));

        //set panel color
        topPanel.setBackground(new Color(3, 3, 8));
        LeftPanel.setBackground(new Color(187, 201, 211, 255));

        //set panel elements size
        panel.setLayout(new BorderLayout());
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(RightPanel, BorderLayout.EAST);
        panel.add(LeftPanel, BorderLayout.WEST);


        //create leftpanel labels
        JLabel label = new JLabel("Recent Calorie Intake");
            label.setHorizontalAlignment(JLabel.CENTER);
            LeftPanel.add(label);
        String[] result = getPastNDays(D);
        for (String day : result) {
            label = new JLabel(day);
            //在这里加数据
            label.setBorder(BorderFactory.createLineBorder(Color.white, 1));
            LeftPanel.add(label);
        }

        //create timeLabel
        timeLabel = new JLabel();
        timeLabel.setForeground(Color.white);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        topPanel.add(timeLabel, BorderLayout.NORTH);

        //create buttons we need
        // Attach component listener before creating buttons
        String[] buttonNames = {"Edit Profile","Input Data", "Detailed Diet Data", "Diet Visualizer","Calorie Visualizer","Weightloss Forecast","CFG"};
        JButton buttons[] = new JButton[buttonNames.length];

        for (int i = 0; i < buttonNames.length; i++) {
            buttons[i] = new JButton(buttonNames[i]);
            buttons[i].addActionListener(buttonListeners[i]);
            RightPanel.add(buttons[i]);
        }

        addComponentListener();
    }

    /**
     * Gets an array of formatted strings representing the past N days.
     *
     * @param n The number of past days to retrieve.
     * @return An array of formatted strings representing the past N days.
     */
    public static String[] getPastNDays(int n) {
            // Get today's date
            LocalDate currentDate = LocalDate.now();

            // Create a DateTimeFormatter for the desired date format
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd");

            // Create an array to store the result
            String[] result = new String[n];

            // Loop through the past N days and store formatted dates in the array
            for (int i = 0; i < n; i++) {
                result[i] = currentDate.format(dateFormatter);

                // Move to the previous day
                currentDate = currentDate.minusDays(1);
            }
            return result;
    }
    /**
     * Updates the timeLabel with the current date and time.
     */
    private void updateTime() {
        // Get the current time
        Date now = new Date();

        // Format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd (E) HH:mm:ss");
        String formattedTime = dateFormat.format(now);

        // Update the label text
        timeLabel.setText("Current Time: " + formattedTime);
    }

    /**
     * Adds a ComponentListener to adjust the panel size and font based on resizing.
     */
    private void addComponentListener() {
        ComponentAdapter componentAdapter = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustPanel();
                adjustFont();
            }
        };

        // Attach the ComponentListener to the panel
        panel.addComponentListener(componentAdapter);
    }
    /**
     * Adjusts the size of LeftPanel and RightPanel based on the topPanel's width.
     */
    //set panel
    private void adjustPanel(){
        LeftPanel.setPreferredSize(new Dimension(topPanel.getWidth()*2/3, 1)); // Adjust the width as needed
        RightPanel.setPreferredSize(new Dimension(topPanel.getWidth()/3, 1));
    }
    /**
     * Adjusts the font size and color of buttons and labels based on the panel's size.
     */
    //set font
    private void adjustFont() {
        // Adjust font size based on the height of the panel
        int fontSize = Math.min(panel.getHeight() / 25, panel.getWidth()/40 ); // Adjust the divisor as needed
        // Apply the adjusted font size to all buttons
        Component[] components = RightPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                Font updatedFont = button.getFont().deriveFont(Font.BOLD, fontSize);
                button.setForeground(new Color(255, 255, 255));
                button.setBackground(new Color(0x1A77BD));
                button.setFont(updatedFont);
            }
        }
        components = LeftPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                Font updatedFont = label.getFont().deriveFont(Font.BOLD, fontSize);
                label.setForeground(new Color(0, 0, 0));
                label.setFont(updatedFont);
            }
        }
    }

    /**
     * Gets the JPanel representing the MainPage.
     *
     * @return The JPanel representing the MainPage.
     */
    public JPanel getPanel() {
        return panel;
    }
}