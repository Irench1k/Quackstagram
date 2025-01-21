package quackstagram.views.prelogin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import quackstagram.views.ColorID;

/**
 * The {@code SignUpUIDecorator} class extends {@code SignUpUI} to add two-factor authentication (2FA) functionality to the sign-up process.
 * It provides an additional text field for users to enter their passcode, supporting enhanced security during account creation.
 */
public class SignUpUIDecorator extends SignUpUI {
    SignUpUI signUpUI;
    JTextField twoFAField;

    /**
     * Constructs a {@code SignUpUIDecorator} with a reference to the original {@code SignUpUI}.
     *
     * @param signUpUI the original {@code SignUpUI} to be decorated with 2FA functionality
     */
    public SignUpUIDecorator(SignUpUI signUpUI) {
        super();
        this.signUpUI = signUpUI;
    }

    /**
     * Creates and returns the main content panel, modified to include a passcode field for two-factor authentication.
     *
     * @return A {@code JPanel} containing the components for the decorated sign-up form.
     */
    @Override
    protected JPanel createMainContentPanel() {
        JPanel fieldsPanel = new JPanel();
        JPanel photoPanel = getDuckIcon();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        txtUsername = new JTextField("Username");
        txtPassword = new JTextField("Password");
        txtBio = new JTextField("Bio");
        txtBio.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtBio.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtUsername.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtUsername.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtPassword.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtPassword.setBackground(getColor(ColorID.ENTER_COMPONENT));

        txtBio.setBorder(BorderFactory.createEmptyBorder());
        txtPassword.setBorder(BorderFactory.createEmptyBorder());
        txtUsername.setBorder(BorderFactory.createEmptyBorder());
        
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtBio);
        fieldsPanel.add(Box.createVerticalStrut(10));
        btnUploadPhoto = new JButton("Upload Photo");
        btnUploadPhoto.setForeground(getColor(ColorID.TEXT_PRIMARY));
        btnUploadPhoto.setBackground(getColor(ColorID.ENTER_COMPONENT));
        
        btnUploadPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleProfilePictureUpload();
            }
        });
        
        twoFAField = new JTextField("Passcode");
        twoFAField.setForeground(getColor(ColorID.TEXT_SECONDARY));
        twoFAField.setBackground(getColor(ColorID.ENTER_COMPONENT));
        twoFAField.setBorder(BorderFactory.createEmptyBorder());
        fieldsPanel.add(twoFAField);

        JPanel photoUploadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoUploadPanel.setForeground(getColor(ColorID.TEXT_SECONDARY));
        photoUploadPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));
        photoUploadPanel.add(btnUploadPhoto);
        
        fieldsPanel.add(photoUploadPanel);
        fieldsPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));


        return fieldsPanel;
    }

    /**
     * Overrides the onPrimaryButtonClick method to include the 2FA passcode in the sign-up process.
     *
     * @param event The action event triggered by clicking the primary button.
     */
    @Override
    protected void onPrimaryButtonClick(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String bio = txtBio.getText();
        String passCode = twoFAField.getText();

        if (this.selectedFile == null) {
            handleProfilePictureUpload();
        }

        if (this.selectedFile == null) {
            // User still hasnt selected the file, abort
            JOptionPane.showMessageDialog(this, "No file was selected. Please choose a file.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.signUp(username, password, bio, passCode, selectedFile);
    }
}
