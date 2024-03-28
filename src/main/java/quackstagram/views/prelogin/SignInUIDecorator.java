package quackstagram.views.prelogin;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import quackstagram.views.ColorID;

import java.awt.event.ActionEvent;

/**
 * The {@code SignInUIDecorator} class enhances the {@link SignInUI} by adding an additional field for two-factor authentication (2FA) passcode input.
 * This class demonstrates the Decorator Design Pattern, allowing dynamic addition of functionalities to the sign-in process.
 */
public class SignInUIDecorator extends SignInUI {
    @SuppressWarnings("unused")
    private SignInUI signInUI;
    JTextField txtPassCode;

    /**
     * Constructs a new {@code SignInUIDecorator} with an enhanced sign-in interface, adding a passcode field for 2FA.
     *
     * @param signInUI The original sign-in UI to be enhanced.
     */
    public SignInUIDecorator(SignInUI signInUI) {
        super();
        this.signInUI = signInUI;
    }

    /**
     * Overrides the method to create the main content panel, incorporating additional UI components specific to the decorator.
     * Adds a text field for the 2FA passcode to the sign-in form.
     *
     * @return A {@code JPanel} that includes username, password, and passcode fields.
     */
    protected JPanel createMainContentPanel() {
        // Text fields panel
        JPanel fieldsPanel = new JPanel();
        JPanel photoPanel = getDuckIcon();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        txtUsername = new JTextField("Username");
        txtPassword = new JTextField("Password");
        txtPassCode = new JTextField("Passcode");
        txtPassCode.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtPassCode.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtUsername.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtUsername.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtPassword.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtPassword.setBackground(getColor(ColorID.ENTER_COMPONENT));

        txtPassCode.setBorder(BorderFactory.createEmptyBorder());
        txtPassword.setBorder(BorderFactory.createEmptyBorder());
        txtUsername.setBorder(BorderFactory.createEmptyBorder());
        
        
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassCode);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));

        return fieldsPanel;
    }

    /**
     * Overrides the action to be performed on the primary button click to include the passcode field in the sign-in process.
     * This method invokes the {@code logIn} method on the {@code controller} with the username, password, and passcode.
     *
     * @param event The {@code ActionEvent} triggered by clicking the button.
     */
    @Override
    protected void onPrimaryButtonClick(ActionEvent event) {
        controller.logIn(txtUsername.getText(), txtPassword.getText(), txtPassCode.getText());
    }
}
