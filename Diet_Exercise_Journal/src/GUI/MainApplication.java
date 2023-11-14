package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplication {

    private static JFrame mainFrame;
    private static CreateProfileUI createProfileUI;
    private static ChooseProfileUI chooseProfileUI;
    private static ProfileOptionsUI profileOptionsUI;
    private static InputDataPageUI inputDataPageUI;
    private static InputIngredientsPageUI inputIngredientsPageUI;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createMainFrame();
            }
        });
    }

    private static void createMainFrame() {
        mainFrame = new JFrame("Profile Management");
        mainFrame.setSize(500, 350);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LandingPageUI landingPageUI = new LandingPageUI(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showCreateProfileUI();
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showChooseProfileUI();
                    }
                }
        );

        mainFrame.add(landingPageUI.getPanel());
        mainFrame.setVisible(true);
    }

    private static void showCreateProfileUI() {
        mainFrame.getContentPane().removeAll();
        mainFrame.revalidate();
        mainFrame.repaint();

        createProfileUI = new CreateProfileUI(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the Save button action in CreateProfileUI
                String name = createProfileUI.getName();
                String dob = createProfileUI.getDOB();
                String gender = createProfileUI.getGender();
                String weight = createProfileUI.getWeight();
                String height = createProfileUI.getHeight();
                String measurement = createProfileUI.getMeasurement();

                // Add your logic to save or process the profile information

                // After saving the profile, transition to the next layer
                showProfileOptionsUI();
            }
        });

        mainFrame.add(createProfileUI.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showChooseProfileUI() {
        mainFrame.getContentPane().removeAll();
        mainFrame.revalidate();
        mainFrame.repaint();

        chooseProfileUI = new ChooseProfileUI(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the Choose Profile button action in ChooseProfileUI
                String username = chooseProfileUI.getUsername();
                String userId = chooseProfileUI.getUserId();

                // Add your logic to validate the username and userId

                // After choosing the profile, transition to the next layer
                showProfileOptionsUI();
            }
        });

        mainFrame.add(chooseProfileUI.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showProfileOptionsUI() {
        mainFrame.getContentPane().removeAll();
        mainFrame.revalidate();
        mainFrame.repaint();

        profileOptionsUI = new ProfileOptionsUI(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Diet Data button action
                        showInputDataPageUI();
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Calorie Data button action
                        JOptionPane.showMessageDialog(null, "Calorie Data button clicked");
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Input Today's Data button action
                        showInputDataPageUI();
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Edit Profile button action
                        JOptionPane.showMessageDialog(null, "Edit Profile button clicked");
                    }
                }
        );

        mainFrame.add(profileOptionsUI.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void showInputDataPageUI() {
        mainFrame.getContentPane().removeAll();
        mainFrame.revalidate();
        mainFrame.repaint();

        inputDataPageUI = new InputDataPageUI(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Breakfast button action
                        showInputIngredientsPageUI("Breakfast");
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Lunch button action
                        showInputIngredientsPageUI("Lunch");
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Dinner button action
                        showInputIngredientsPageUI("Dinner");
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Snack button action
                        showInputIngredientsPageUI("Snack");
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Exercise button action
                        showInputIngredientsPageUI("Exercise");
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Back button action
                        showProfileOptionsUI();
                    }
                }
        );

        mainFrame.add(inputDataPageUI.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }


    private static void showInputIngredientsPageUI(String mealType) {
        mainFrame.getContentPane().removeAll();
        mainFrame.revalidate();
        mainFrame.repaint();

        inputIngredientsPageUI = new InputIngredientsPageUI(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Add button action in InputIngredientsPageUI
                String ingredient = inputIngredientsPageUI.getIngredient();
                String quantity = inputIngredientsPageUI.getQuantity();

                // Add your logic to process the ingredient and quantity for the specific meal type

                // After processing, you can navigate back to the previous page or perform other actions
                showInputIngredientsPageUI(mealType);
            }
        }, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Back to Previous Page button action
                showInputDataPageUI();
            }
        });

        mainFrame.add(inputIngredientsPageUI.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
