package NewUi;

import DatabaseOperation.RuntimeDatabase;
import Facade.NutritionServiceFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Main {

    public static JFrame mainFrame;
    private static CreateProfilePage createProfilePage;
    private static ChooseProfilePage chooseProfilePage;
    private static DietLogPage dietLogPage;
    private static ExericiseLogPage exercisePage;

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

        //showLandingPage();
        SwingUtilities.invokeLater(Main::showMainPage);
    }

    private static void showLandingPage() {
        mainFrame.getContentPane().removeAll();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LandingPage landingPage = new LandingPage(
                //create profile button
                e -> showCreateProfilePage(),
                //choose profile button
                e -> {
                    try {
                        showChooseProfilePage();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

        );

        mainFrame.add(landingPage.getPanel());
        mainFrame.setVisible(true);
    }

    private static void showCreateProfilePage() {
        mainFrame.getContentPane().removeAll();
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        //not working with local variable
        createProfilePage = new CreateProfilePage(
                e -> {
                    // Handle the Save button action in CreateProfileUI
                    String name = createProfilePage.getName();
                    String dob = createProfilePage.getDOB();
                    String gender = createProfilePage.getGender();
                    String weight = createProfilePage.getWeight();
                    String height = createProfilePage.getHeight();
                    String measurement = createProfilePage.getMeasurement();
                    String[] ymd = dob.split("-");
                    try {
                        //挪到facade change here
                        runtimeDatabase.createProfile(name, gender, Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]), Double.parseDouble(height), Double.parseDouble(weight), measurement);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    // Add your logic to save or process the profile information

                    // After saving the profile, transition to the next layer\
                    JOptionPane.showMessageDialog(null, "Profile create successfully");
                },
                e -> showLandingPage());

        mainFrame.add(createProfilePage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    //direct to choose profile page
    private static void showChooseProfilePage() throws Exception {
        mainFrame.getContentPane().removeAll();
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        //display all profiles
        ArrayList<String> profile = runtimeDatabase.extractProfile();
        String[] users = new String[profile.size()];//api for runtime data base plug in
        //add to array
        for (int i = 0; i < profile.size(); i++) {
            users[i] = profile.get(i);
        }

        chooseProfilePage = new ChooseProfilePage(users, e -> showLandingPage(), e -> {
            String selectedUser = chooseProfilePage.getSelectedUser();
            String[] temp = selectedUser.split("id: ");//extract user ID
            //挪到facade
            runtimeDatabase.setId(Integer.parseInt(temp[1].substring(0, temp[1].length() - 1)));//set id to do further works
            runtimeDatabase.setMealInfo(runtimeDatabase.readAllMealInfo(Integer.parseInt(temp[1].substring(0, temp[1].length() - 1))));//read all meal info of the user

            showMainPage();
        });
        mainFrame.add(chooseProfilePage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showMainPage() {
        mainFrame.getContentPane().removeAll();

        //reset size and location of window
        mainFrame.setSize(1500, 1000);
        mainFrame.setMinimumSize(new Dimension(1000, 666));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screenSize.width - mainFrame.getWidth()) / 2;
        int yPos = (screenSize.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(xPos, yPos);

        ActionListener detailedDataListener = e -> {
            // Handle diet data button action
            showDetailedDietPage();
        };

        ActionListener inputDataListener = e -> {
            //show choose to promote for diet or exercise
            String[] options = {"Diet", "Exercise"};
            //create promote
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
            // Check the user's choice
            if (result == JOptionPane.YES_OPTION) {
                // User clicked "Select Diet"
                showDietLogPage();
            } else if (result == JOptionPane.NO_OPTION) {
                // User clicked "Select Exercise"
                showExerciseLogPage();
            }
        };

        ActionListener editProfileListener = e -> {
            // Handle create profile button action
            showCreateProfilePage();
        };

        ActionListener DietVisualizerListener = e -> {
            // Handle edit profile button action
            showDateRangeSelectionDialog(mainFrame);
            NutritionServiceFacade.displayDietChart("1","1");

        };

        ActionListener ExerciseVisualizerListener = e -> {
            // Handle edit profile button action
            String[] helper= new String[2];
            helper=showDateRangeSelectionDialog(mainFrame);
            NutritionServiceFacade.displayCalorieChart(helper[0],helper[1]);

        };
        ActionListener WeightLossForecastListener = e -> {
            // Handle edit profile button action
            //change here
            JOptionPane.showMessageDialog(null, "<html><font size='5'>Estimate Weight Change is: <br>" +
                    "<font size='7'>" + NutritionServiceFacade.getWeightForecast("12/20/2023") + " KG</font></font></html>");
        };
        ActionListener CFGListener = e -> {
            // Handle edit profile button action
            //change here
            NutritionServiceFacade.getCFGchart(new double[]{30.0, 25.0, 20.0, 15.0, 10.0},
        new double[]{30.0, 30.0, 20.0, 15.0, 5.0});
        };


        ActionListener[] buttonListeners = {editProfileListener, inputDataListener, detailedDataListener, DietVisualizerListener, ExerciseVisualizerListener, WeightLossForecastListener, CFGListener};

        MainPage mainPage = new MainPage(buttonListeners);

        mainFrame.add(mainPage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showExerciseLogPage() {
        mainFrame.getContentPane().removeAll();
        exercisePage = new ExericiseLogPage(e -> {
            String intensity = exercisePage.getIntensity();
            String length = exercisePage.getLength();
            String date = exercisePage.getDate();
            String exerciseType = exercisePage.getExerciseType();

            //link function here

        }, e -> showMainPage());

        mainFrame.add(exercisePage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }


    private static void showDietLogPage() {
        mainFrame.getContentPane().removeAll();
        dietLogPage = new DietLogPage(e -> {
            String Ingredient = dietLogPage.getIngredient();
            String mealType = dietLogPage.getMealType();
            String date = dietLogPage.getDate();
            String quantity = dietLogPage.getQuantity();

            //link function here

        }, e -> showMainPage());

        //print page
        mainFrame.add(dietLogPage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showDetailedDietPage() {
        mainFrame.getContentPane().removeAll();
        //change here
        String[] date = {"11/16", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17", "11/17"};
        DetailedDietPage detailedDietPage = new DetailedDietPage(date,
                e -> {
                    String[] mealOptions = {"Breakfast", "Lunch", "Dinner", "Snack"};

                    // Show the second JOptionPane with four options for meals
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
                    // use the user's choice for meals
                    showDetailedMealPage(mealResult);
                },
                e -> showMainPage());
        mainFrame.add(detailedDietPage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showDetailedMealPage(int type/*0=breakfast,1=lunch,2=dinner,3=snack*/){
        mainFrame.getContentPane().removeAll();

        DetailedMealPage detailedMealPage=new DetailedMealPage(new String[]{"1"}, new String[]{"2"},e -> showDetailedDietPage());

        mainFrame.add(detailedMealPage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static String[] showDateRangeSelectionDialog(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        panel.add(new JLabel("Start Date (MM/DD):"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date (MM/DD):"));
        panel.add(endDateField);

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

        String[] result1 = new String[2];

        String startDate = null;
        String endDate = null;
        if (result == JOptionPane.OK_OPTION) {
            startDate = startDateField.getText();
            endDate = endDateField.getText();
            JOptionPane.showMessageDialog(parentFrame, "You selected: " + startDate + " - " + endDate);
        }

        result1[0] = startDate;
        result1[1] = endDate;
        return result1;
    }

}