package src.main.java.quackstagram;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SignInUI extends AbstractLogin {
    private JTextField txtUsername; // TODO: Primitive Obsession: using simple text fields for sensitive information.
    private JTextField txtPassword;
    private User newUser;

    public SignInUI() {
        super("Sign-In");
    }

    @Override
    protected JPanel createMainContentPanel() {
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
       newUser = new User(txtUsername.getText(), "", txtPassword.getText());
        System.out.println(newUser.getUsername() + " <-> " + newUser.getPassword());
        InstagramReader reader = new InstagramReader();
        if (reader.verifyCredentials(newUser)) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignInUI frame = new SignInUI();
            frame.setVisible(true);
        });
    }
}
