package quackstagram.views.prelogin;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import quackstagram.controllers.prelogin.SignInController;
import quackstagram.views.ColorID;

/**
 * The {@code SignInUI} class provides the graphical user interface for the sign-in process in the Quackstagram application.
 * It extends {@code AbstractPreLogin} to leverage common pre-login features such as title formatting and header text.
 * This UI includes text fields for entering username and password, as well as a button for users with two-factor authentication (2FA) enabled.
 */
public class SignInUI extends AbstractPreLogin {
    protected JTextField txtUsername;
    protected JTextField txtPassword;
    protected SignInController controller;

    /**
     * Constructs a new {@code SignInUI} instance, initializing the UI components and setting up the sign-in controller.
     */
    public SignInUI() {
        super("Sign-In");
        this.controller = new SignInController(this);
    }

    /**
     * Creates and returns the main content panel for the sign-in screen, including text fields for username and password,
     * and a button for users with 2FA enabled.
     *
     * @return A {@code JPanel} containing the sign-in form components.
     */
    @Override
    protected JPanel createMainContentPanel() {
        // Text fields panel
        JPanel fieldsPanel = new JPanel();
        JPanel photoPanel = getDuckIcon();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        txtUsername = new JTextField("Username");
        txtPassword = new JTextField("Password");
        txtUsername.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtPassword.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtUsername.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtPassword.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtUsername.setBorder(null);
        txtPassword.setBorder(null);
        
        JButton twoFAButton = new JButton("Click if you've enabled 2FA");
        twoFAButton.setForeground(getColor(ColorID.TEXT_PRIMARY));
        twoFAButton.setBackground(getColor(ColorID.ENTER_COMPONENT));
        
        twoFAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.view.dispose();
                controller.view = new SignInUIDecorator(controller.view);
                controller.showSignIn();
            }
        });
    
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(twoFAButton);
        
        JPanel twoFAPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        twoFAPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));
        twoFAPanel.add(twoFAButton);

        fieldsPanel.add(twoFAPanel);
        fieldsPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));

        return fieldsPanel;
    }

    @Override
    protected String getSecondButtonText() {
        return "No Account? Register Now";
    }

    @Override
    protected void onPrimaryButtonClick(ActionEvent event) {
        controller.logIn(txtUsername.getText(), txtPassword.getText());
    }

    @Override
    protected void onSecondaryButtonCLick(ActionEvent event) {
        controller.showSignUp();
    }
}
