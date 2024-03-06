import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SignInUI extends AbstractLogin {
    private JTextField txtUsername; // TODO: Primitive Obsession: using simple text fields for sensitive information.
    private JTextField txtPassword;
    private User newUser;

    public SignInUI() {
        super("Sign-In");
    }

    @Override
    protected JPanel createFieldPanel() {
        // Text fields panel
        JPanel fieldsPanel = new JPanel();
        JPanel photoPanel = getDuckIcon();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        txtUsername = new JTextField("Username");
        txtPassword = new JTextField("Password");
        txtUsername.setForeground(Color.GRAY);
        txtPassword.setForeground(Color.GRAY);

        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createVerticalStrut(10));

        return fieldsPanel;
    }

    @Override
    protected String getSecondButtonText() {
        return "No Account? Register Now";
    }

    @Override
    protected void onPrimaryButtonClick(ActionEvent event) {
        // TODO: Shotgun Surgery: Similar logic appears in the SignUpUI class. Changes here may require changes there.
        String enteredUsername = txtUsername.getText();
        String enteredPassword = txtPassword.getText();
        System.out.println(enteredUsername + " <-> " + enteredPassword);
        if (verifyCredentials(enteredUsername, enteredPassword)) {
            System.out.println("It worked");
            // Close the SignUpUI frame
            dispose();

            // Open the SignInUI frame
            SwingUtilities.invokeLater(() -> {
                InstagramProfileUI profileUI = new InstagramProfileUI(newUser);
                profileUI.setVisible(true);
            });
        } else {
            System.out.println("It Didn't");
        }
    }

    @Override
    protected void onSecondaryButtonCLick(ActionEvent event) {
        // TODO: Duplicated Code: Similar logic appears in SignUpUI class (openSignInUI)
        // Close the SignInUI frame
        dispose();

        // Open the SignUpUI frame
        SwingUtilities.invokeLater(() -> {
            SignUpUI signUpFrame = new SignUpUI();
            signUpFrame.setVisible(true);
        });
    }

    private boolean verifyCredentials(String username, String password) {
        // TODO: Data Clumps: `username`, `password`, and later `bio` are often passed together.
        try (BufferedReader reader = new BufferedReader(new FileReader("data/credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    String bio = credentials[2];
                    // Create User object and save information
                    newUser = new User(username, bio, password); // Assuming User constructor takes these parameters
                    saveUserInformation(newUser);

                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Error Handling: Directly printing stack trace.
        }
        return false;
    }

    private void saveUserInformation(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/users.txt", false))) {
            writer.write(user.toString()); // Implement a suitable toString method in User class
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Here again, improper error handling.
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignInUI frame = new SignInUI();
            frame.setVisible(true);
        });
    }
}
