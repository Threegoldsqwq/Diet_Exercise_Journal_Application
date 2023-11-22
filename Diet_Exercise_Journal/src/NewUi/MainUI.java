package NewUi;

import OutcomeGenerator.CFG;
import Facade.NutritionServiceFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

/**
 * The main class for the nutrition and exercise tracking application.
 * This class manages the GUI components and transitions between different pages.
 * It utilizes the NutritionServiceFacade to interact with the underlying services.
 * The application allows users to create profiles, log diet and exercise data, visualize data,
 * and perform various other functions related to nutrition and exercise tracking.
 *
 * If you want to START the application, YOU CAN RUN THIS CLASS :), but make sure you have connected to the database.
 *
 * @author DD
 * @version 1.0
 * @since 11/20
 */
public class MainUI {

    public static JFrame mainFrame;
    private static CreateProfilePage createProfilePage;
    private static ChooseProfilePage chooseProfilePage;
    private static DietLogPage dietLogPage;
    private static ExericiseLogPage exercisePage;
    private static NutritionServiceFacade facade = new NutritionServiceFacade();
    private static DetailedDietPage detailedDietPage;
    /**
     * The main method that starts the application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {

        //create default frame
        mainFrame = new JFrame("Profile Management");
        mainFrame.setSize(500, 350);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int xPos = (screenSize.width - mainFrame.getWidth()) / 2;
        int yPos = (screenSize.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(xPos, yPos);
        mainFrame.setVisible(true);

        //add a listener for frame closed
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call your function or perform actions when the window is closing
                facade.writeBack();
                System.out.println("Window is closing. Activate your function here!");
            }
        });

        //showLandingPage();
        SwingUtilities.invokeLater(MainUI::showLandingPage);
    }

    /**
     * Displays the landing page of the application.
     * The landing page includes options to create a new profile or choose an existing one.
     * It sets up the corresponding action listeners for the create and choose profile buttons.
     */
    private static void showLandingPage() {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Set default close operation for the main frame
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a LandingPage instance with action listeners for create and choose profile buttons
        LandingPage landingPage = new LandingPage(
                // Action listener for the create profile button
                e -> showCreateProfilePage(),
                // Action listener for the choose profile button
                e -> {
                    try {
                        showChooseProfilePage();
                    } catch (Exception ex) {
                        // Handle any exceptions by throwing a runtime exception
                        throw new RuntimeException(ex);
                    }
                }
        );

        // Add the landing page panel to the main frame
        mainFrame.add(landingPage.getPanel());

        // Make the main frame visible
        mainFrame.setVisible(true);
    }


    /**
     * Displays the create profile page of the application.
     * This page allows users to input their profile information and create a new profile.
     * The method sets up the corresponding action listeners for the save and cancel buttons.
     */
    private static void showCreateProfilePage() {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Create an instance of the CreateProfilePage with action listeners for save and cancel buttons
        createProfilePage = new CreateProfilePage(
                // Action listener for the save button
                e -> {
                    // Handle the Save button action in CreateProfileUI

                    // Extract profile information from the UI components
                    String name = createProfilePage.getName();
                    String dob = createProfilePage.getDOB();
                    String gender = createProfilePage.getGender();
                    String weight = createProfilePage.getWeight();
                    String height = createProfilePage.getHeight();
                    String measurement = createProfilePage.getMeasurement();
                    String[] ymd = dob.split("-");

                    // Use the facade to create a new profile with the extracted information
                    facade.createProfile(name, gender, Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]),
                            Integer.parseInt(ymd[2]), Double.parseDouble(height), Double.parseDouble(weight), measurement);

                    // Add your logic to save or process the profile information

                    // After saving the profile, display a success message
                    JOptionPane.showMessageDialog(null, "Profile created successfully");
                },
                // Action listener for the cancel button
                e -> showLandingPage()
        );

        // Add the create profile page panel to the main frame
        mainFrame.add(createProfilePage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }


    /**
     * Displays the choose profile page of the application.
     * This page allows users to select an existing profile from a list.
     * The method retrieves the list of profiles from the NutritionServiceFacade,
     * sets up the corresponding action listeners for the back and select buttons,
     * and uses the facade to select the chosen profile for further interaction.
     *
     * @throws Exception If there is an error while displaying the choose profile page.
     */
    private static void showChooseProfilePage() throws Exception {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Create an instance of NutritionServiceFacade to interact with the profile data
        NutritionServiceFacade facade = new NutritionServiceFacade();

        // Display all profiles and retrieve them as an array
        String[] users = facade.displayProfile();

        // Create an instance of ChooseProfilePage with action listeners for back and select buttons
        chooseProfilePage = new ChooseProfilePage(users,
                // Action listener for the back button
                e -> showLandingPage(),
                // Action listener for the select button
                e -> {
                    // Get the selected user from the ChooseProfilePage
                    String selectedUser = chooseProfilePage.getSelectedUser();

                    // Extract the user ID from the selected user string
                    String[] temp = selectedUser.split("id: ");
                    int selectedUserId = Integer.parseInt(temp[1].substring(0, temp[1].length() - 1));

                    // Use the facade to select the chosen profile
                    facade.selectProfile(selectedUserId);

                    // Transition to the main page after selecting the profile
                    showMainPage();
                });

        // Add the choose profile page panel to the main frame
        mainFrame.add(chooseProfilePage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    /**
     * Displays the main page of the application after selecting a profile.
     * This page includes various buttons for actions such as logging diet or exercise data,
     * visualizing data, and editing the profile.
     * The method sets up action listeners for each button to handle corresponding actions.
     */
    private static void showMainPage() {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Reset size and location of the window
        mainFrame.setSize(1500, 1000);
        mainFrame.setMinimumSize(new Dimension(1000, 666));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screenSize.width - mainFrame.getWidth()) / 2;
        int yPos = (screenSize.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(xPos, yPos);

        // Action listener for the button to show detailed diet data
        ActionListener detailedDataListener = e -> {
            // Handle diet data button action
            showDetailedDietPage();
        };

        // Action listener for the button to input diet or exercise data
        ActionListener inputDataListener = e -> {
            // Show a dialog to choose between diet and exercise input
            String[] options = {"Diet", "Exercise"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "Please choose Diet or Exercise data you want to input",
                    "Choose input type",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0] // Default selection
            );

            // Check the user's choice and show the corresponding page
            if (result == JOptionPane.YES_OPTION) {
                // User clicked "Select Diet"
                showDietLogPage();
            } else if (result == JOptionPane.NO_OPTION) {
                // User clicked "Select Exercise"
                showExerciseLogPage();
            }
        };

        // Action listener for the button to edit the profile
        ActionListener editProfileListener = e -> {
            // Handle edit profile button action
            showEditProfilePage();
        };

        // Action listener for the button to visualize diet data
        ActionListener dietVisualizerListener = e -> {
            // Handle diet visualizer button action
            String[] helper;
            helper = showDateRangeSelectionDialog(mainFrame);
            NutritionServiceFacade.displayDietChart(helper[0], helper[1]);
        };

        // Action listener for the button to visualize exercise data
        ActionListener exerciseVisualizerListener = e -> {
            // Handle exercise visualizer button action
            String[] helper;
            helper = showDateRangeSelectionDialog(mainFrame);
            try {
                NutritionServiceFacade.displayCalorieChart(helper[0], helper[1]);
            } catch (ParseException ex) {
                // Handle any parse exception by throwing a runtime exception
                throw new RuntimeException(ex);
            }
        };

        // Action listener for the button to forecast weight loss
        ActionListener weightLossForecastListener = e -> {
            // Handle weight loss forecast button action
            try {
                String helper=showDateSelectionDialog(mainFrame);
                JOptionPane.showMessageDialog(null, "<html><font size='5'>Estimate Weight Change is: <br>" +
                        "<font size='7'>" + NutritionServiceFacade.getWeightForecast(helper) + " KG</font></font></html>");
            } catch (ParseException ex) {
                // Handle any parse exception by throwing a runtime exception
                throw new RuntimeException(ex);
            }
        };

        // Action listener for the button to view Canadian Food Guide chart
        ActionListener cfgListener = e -> {
            // Handle CFG chart button action
            NutritionServiceFacade.getCFGchart(facade.getTotalFoodGroupIntake(),
                    CFG.getCFGRecommends(facade.getTotalFoodGroupIntake()));
        };

        // Array of all button listeners
        ActionListener[] buttonListeners = {editProfileListener, inputDataListener, detailedDataListener,
                dietVisualizerListener, exerciseVisualizerListener, weightLossForecastListener, cfgListener};

        // Create an instance of MainPage with the specified button listeners
        MainPage mainPage = new MainPage(buttonListeners);

        // Add the main page panel to the main frame
        mainFrame.add(mainPage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    /**
     * Displays the exercise log page of the application.
     * This page allows users to input exercise data, including intensity, duration, date, and exercise type.
     * The method sets up action listeners for the save and cancel buttons,
     * and uses the NutritionServiceFacade to log the exercise data.
     *
     * @throws RuntimeException If there is an error while logging exercise data, such as a parse exception.
     */
    private static void showExerciseLogPage() {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Create an instance of NutritionServiceFacade to interact with exercise data
        NutritionServiceFacade facade = new NutritionServiceFacade();

        // Create an instance of ExerciseLogPage with action listeners for save and cancel buttons
        exercisePage = new ExericiseLogPage(
                // Action listener for the save button
                e -> {
                    // Extract exercise information from the UI components
                    String intensity = exercisePage.getIntensity();
                    String length = exercisePage.getLength();
                    String date = exercisePage.getDate();
                    String exerciseType = exercisePage.getExerciseType();

                    // Log exercise data using the facade
                    try {
                        facade.logExercise(date, exerciseType, length, intensity);
                    } catch (ParseException ex) {
                        // Handle any parse exception by throwing a runtime exception
                        throw new RuntimeException(ex);
                    }

                },
                // Action listener for the cancel button
                e -> showMainPage()
        );

        // Add the exercise log page panel to the main frame
        mainFrame.add(exercisePage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    /**
     * Displays the diet log page of the application.
     * This page allows users to input diet data, including ingredients, meal type, date, and quantity.
     * The method sets up action listeners for the save and cancel buttons,
     * and uses the NutritionServiceFacade to log the diet data.
     */
    private static void showDietLogPage() {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Create an instance of NutritionServiceFacade to interact with diet data
        NutritionServiceFacade facade = new NutritionServiceFacade();

        // Create an instance of DietLogPage with action listeners for save and cancel buttons
        dietLogPage = new DietLogPage(
                // Action listener for the save button
                e -> {
                    // Extract diet information from the UI components
                    String ingredient = dietLogPage.getIngredient();
                    String mealType = dietLogPage.getMealType();
                    String date = dietLogPage.getDate();
                    String quantity = dietLogPage.getQuantity();

                    // Log diet data using the facade
                    facade.logDiet(date, mealType, ingredient, quantity);
                },
                // Action listener for the cancel button
                e -> showMainPage()
        );

        // Add the diet log page panel to the main frame
        mainFrame.add(dietLogPage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    /**
     * Displays the detailed diet page of the application.
     * This page allows users to view detailed information about their diet on specific dates and meals.
     * The method sets up action listeners for date selection and uses the NutritionServiceFacade
     * to retrieve and display detailed diet information.
     */
    private static void showDetailedDietPage() {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Create an instance of NutritionServiceFacade to interact with diet data
        NutritionServiceFacade facade = new NutritionServiceFacade();

        // Retrieve an array of dates for which diet data is available
        String[] date = facade.showDates();

        // Create an instance of DetailedDietPage with action listeners for date selection
        detailedDietPage = new DetailedDietPage(date,
                // Action listener for selecting a date
                e -> {
                    String[] mealOptions = {"Breakfast", "Lunch", "Dinner", "Snack"};
                    String selectedDate = detailedDietPage.getSelectedDate();

                    // Show a dialog with meal options for the selected date
                    int mealResult = JOptionPane.showOptionDialog(
                            null,
                            "Please choose which meal you want to see",
                            "Choose meal",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            mealOptions,
                            mealOptions[0] // Default selection
                    );

                    // Use the user's choice to show detailed information for the selected meal
                    showDetailedMealPage(mealResult, selectedDate);
                },
                // Action listener for returning to the main page
                e -> showMainPage()
        );

        // Add the detailed diet page panel to the main frame
        mainFrame.add(detailedDietPage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    /**
     * Displays the detailed meal page of the application.
     * This page allows users to view detailed information about a specific meal on a given date.
     * The method sets up action listeners and uses the NutritionServiceFacade to retrieve and display
     * detailed information about the selected meal, including ingredients, quantity, and other nutrients.
     *
     * @param type The type of meal (0=breakfast, 1=lunch, 2=dinner, 3=snack).
     * @param date The date of the meal in the format "YYYY-MM-DD".
     */
    private static void showDetailedMealPage(int type, String date) {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Create an instance of DetailedMealPage with information about the selected meal
        DetailedMealPage detailedMealPage = new DetailedMealPage(
                date,
                // Retrieve ingredients and quantity for the selected meal from the facade
                facade.getIngredientsAndQuantity(type, date),
                // Retrieve other nutrients for the selected meal from the facade
                facade.getOtherNutrients(type, date),
                // Action listener for returning to the detailed diet page
                e -> showDetailedDietPage()
        );

        // Add the detailed meal page panel to the main frame
        mainFrame.add(detailedMealPage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    /**
     * Displays a date range selection dialog for the user to input start and end dates.
     * The method sets up a panel with text fields for start and end dates, and shows an option dialog.
     * The user can input the date range, and the selected dates are returned as an array of strings.
     *
     * @param parentFrame The parent JFrame to which the dialog is attached.
     * @return An array of two strings representing the selected start and end dates respectively.
     */
    private static String[] showDateRangeSelectionDialog(JFrame parentFrame) {
        // Create a panel with a grid layout for the date range selection
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Create text fields for start and end dates
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        // Add labels and text fields to the panel
        panel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date (YYYY-MM-DD):"));
        panel.add(endDateField);

        // Show an option dialog with OK and Cancel options
        int result = JOptionPane.showOptionDialog(
                parentFrame,
                panel,
                "Select Date Range",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        // Create an array to store the selected start and end dates
        String[] resultArray = new String[2];

        // Initialize start and end dates to null
        String startDate = null;
        String endDate = null;

        // If the user clicks OK, retrieve the input dates and display a message
        if (result == JOptionPane.OK_OPTION) {
            startDate = startDateField.getText();
            endDate = endDateField.getText();
            JOptionPane.showMessageDialog(parentFrame, "You selected: " + startDate + " - " + endDate);
        }

        // Set the start and end dates in the result array
        resultArray[0] = startDate;
        resultArray[1] = endDate;

        // Return the result array
        return resultArray;
    }
    /**
     * Displays the profile editing page of the application.
     * This page allows users to edit their existing profile information, including name, date of birth, gender,
     * weight, height, and measurement units. The method sets up action listeners for the save and cancel buttons,
     * and uses the NutritionServiceFacade to edit and save the modified profile information.
     */
    private static void showEditProfilePage() {
        // Remove any existing components from the content pane
        mainFrame.getContentPane().removeAll();

        // Create an instance of CreateProfilePage for profile editing
        createProfilePage = new CreateProfilePage(
                // Action listener for the save button
                e -> {
                    // Extract modified profile information from the UI components
                    String name = createProfilePage.getName();
                    String dob = createProfilePage.getDOB();
                    String gender = createProfilePage.getGender();
                    String weight = createProfilePage.getWeight();
                    String height = createProfilePage.getHeight();
                    String measurement = createProfilePage.getMeasurement();
                    String[] ymd = dob.split("-");

                    // Edit and save the modified profile using the facade
                    facade.editProfile(
                            name,
                            gender,
                            Integer.parseInt(ymd[0]),
                            Integer.parseInt(ymd[1]),
                            Integer.parseInt(ymd[2]),
                            Double.parseDouble(height),
                            Double.parseDouble(weight),
                            measurement
                    );

                    // After saving the modified profile, transition to the landing page
                    JOptionPane.showMessageDialog(null, "Profile changed successfully");
                },
                // Action listener for the cancel button
                e -> showLandingPage()
        );

        // Add the profile editing page panel to the main frame
        mainFrame.add(createProfilePage.getPanel());

        // Update the frame to reflect changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static String showDateSelectionDialog(JFrame parentFrame) {
        // Create a panel with a grid layout for the date range selection
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Create text fields for start and end dates
        JTextField DateField = new JTextField();

        // Add labels and text fields to the panel
        panel.add(new JLabel(" Date (YYYY-MM-DD):"));
        panel.add(DateField);


        // Show an option dialog with OK and Cancel options
        int result = JOptionPane.showOptionDialog(
                parentFrame,
                panel,
                "Select Date",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );


        // Initialize start and end dates to null
        String Date = null;

        // If the user clicks OK, retrieve the input dates and display a message
        if (result == JOptionPane.OK_OPTION) {
            Date = DateField.getText();
            JOptionPane.showMessageDialog(parentFrame, "You selected: " + Date);
        }

        // Return the result array
        return Date;
    }


}