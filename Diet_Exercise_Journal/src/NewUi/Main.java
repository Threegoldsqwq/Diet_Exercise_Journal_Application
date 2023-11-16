package NewUi;

import DatabaseOperation.RuntimeDatabase;
import Facade.NutritionServiceFacade;
import GUI.ChooseProfileUI;
import GUI.CreateProfileUI;
import GUI.LandingPageUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Main {

    private static JFrame mainFrame;
    private static CreateProfilePage createProfilePage;
    private static ChooseProfilePage chooseProfilePage;

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

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createMainFrame();
            }
        });
    }

    private static void createMainFrame() {
        mainFrame.getContentPane().removeAll();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LandingPageUI landingPageUI = new LandingPageUI(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showCreateProfilePage();
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showChooseProfilePage();
                    }
                }
        );

        mainFrame.add(landingPageUI.getPanel());
        mainFrame.setVisible(true);
    }

    private static void showCreateProfilePage() {
        mainFrame.getContentPane().removeAll();

        //not working with local variable
        createProfilePage = new CreateProfilePage(
                e -> {
            RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
            // Handle the Save button action in CreateProfileUI
            String name = createProfilePage.getName();
            String dob = createProfilePage.getDOB();
            String gender = createProfilePage.getGender();
            String weight = createProfilePage.getWeight();
            String height = createProfilePage.getHeight();
            String measurement = createProfilePage.getMeasurement();
           // String[] ymd = dob.split("-");
           // try {
           //     runtimeDatabase.createProfile(name, gender,Integer.parseInt(ymd[0]),Integer.parseInt(ymd[1]),Integer.parseInt(ymd[2]),Double.parseDouble(height),Double.parseDouble(weight),measurement);
           // } catch (Exception ex) {
          //      throw new RuntimeException(ex);
          //  }
            // Add your logic to save or process the profile information

            // After saving the profile, transition to the next layer
        },
                e->{
            createMainFrame();
        });

        mainFrame.add(createProfilePage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private static void showChooseProfilePage() {
        mainFrame.getContentPane().removeAll();

        chooseProfilePage = new ChooseProfilePage(e -> {
            NutritionServiceFacade nutritionServiceFacade = new NutritionServiceFacade();
            // Handle the Choose Profile button action in ChooseProfileUI
            String username = chooseProfilePage.getUsername();
            String userId = chooseProfilePage.getUserId();
            //runtimeDatabase.setId(Integer.parseInt(userId));
            // Add your logic to validate the username and userId
//                try {
//                    runtimeDatabase.displayDietData(runtimeDatabase.getId());
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
 //           nutritionServiceFacade.displayDiet(Integer.parseInt(userId));
            // After choosing the profile, transition to the next layer
            showProfileOptionsPage();
        });

        mainFrame.add(chooseProfilePage.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showProfileOptionsPage() {
        //reset size and location of window
        mainFrame.setSize(1500, 1000);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screenSize.width - mainFrame.getWidth()) / 2;
        int yPos = (screenSize.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(xPos, yPos);


    }
}