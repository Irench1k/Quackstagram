package quackstagram.views.prelogin;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import quackstagram.controllers.prelogin.SignInController;
import quackstagram.views.ColorID;

public class SignInUI extends AbstractPreLogin {
    private JTextField txtUsername;
    private JTextField txtPassword;
    private SignInController controller;

    public SignInUI() {
        super("Sign-In");
        this.controller = new SignInController(this);
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
        txtUsername.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtPassword.setForeground(getColor(ColorID.TEXT_SECONDARY));

        txtUsername.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtPassword.setBackground(getColor(ColorID.ENTER_COMPONENT));

        // Remove the borders of the text fields
        txtUsername.setBorder(null);
        txtPassword.setBorder(null);

        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createVerticalStrut(10));

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
