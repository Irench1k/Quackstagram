package quackstagram.views.prelogin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import quackstagram.controllers.prelogin.SignUpController;
import quackstagram.views.ColorID;

/**
 * The {@code SignUpUI} class represents the user interface for the sign-up process in the Quackstagram application.
 * It allows new users to create an account by entering their username, password, and bio, and by uploading a profile picture.
 */
public class SignUpUI extends AbstractPreLogin {
    protected JTextField txtUsername;
    protected JTextField txtPassword;
    protected JTextField txtBio;
    protected JButton btnUploadPhoto;
    protected File selectedFile;
    protected SignUpController controller;

    /**
     * Constructs a new {@code SignUpUI} and initializes its controller.
     */
    public SignUpUI() {
        super("Sign Up");
        this.controller = new SignUpController(this);
    }

    /**
     * Returns the text for the secondary button displayed on the sign-up interface.
     *
     * @return A string representing the text for the secondary button.
     */
    @Override
    protected String getSecondButtonText() {
        return "Already have an account? Sign In";
    }

    /**
     * Creates and returns the main content panel for the sign-up interface.
     * This panel includes text fields for entering user information and buttons for uploading a profile picture and enabling two-factor authentication.
     *
     * @return A {@code JPanel} containing the components for the sign-up form.
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
        txtUsername.setForeground(getColor(ColorID.TEXT_SECONDARY));
        txtPassword.setForeground(getColor(ColorID.TEXT_SECONDARY));

        txtBio.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtUsername.setBackground(getColor(ColorID.ENTER_COMPONENT));
        txtPassword.setBackground(getColor(ColorID.ENTER_COMPONENT));

        // Remove the borders of the text fields
        txtBio.setBorder(null);
        txtUsername.setBorder(null);
        txtPassword.setBorder(null);

        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtBio);
        btnUploadPhoto = new JButton("Upload Photo");
        btnUploadPhoto.setForeground(getColor(ColorID.TEXT_PRIMARY));
        btnUploadPhoto.setBackground(getColor(ColorID.ENTER_COMPONENT));

        btnUploadPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleProfilePictureUpload();
            }
        });

        JButton twoFAButton = new JButton("Add 2FA");
        twoFAButton.setForeground(getColor(ColorID.TEXT_PRIMARY));
        twoFAButton.setBackground(getColor(ColorID.ENTER_COMPONENT));
        twoFAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.view.dispose();
                controller.view = new SignUpUIDecorator(controller.view);
                controller.showSignUp();
            }
        });

        JPanel photoUploadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoUploadPanel.add(btnUploadPhoto);
        photoUploadPanel.add(twoFAButton);
        photoUploadPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));

        fieldsPanel.add(photoUploadPanel);
        fieldsPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));

        return fieldsPanel;
    }

    /**
     * Handles the primary button click by collecting user input from text fields and calling the sign-up method on the controller.
     *
     * @param event The action event triggered by clicking the primary button.
     */
    @Override
    protected void onPrimaryButtonClick(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String bio = txtBio.getText();

        if (this.selectedFile == null) {
            handleProfilePictureUpload();
        }

        if (this.selectedFile == null) {
            // User still hasnt selected the file, abort
            JOptionPane.showMessageDialog(this, "No file was selected. Please choose a file.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.signUp(username, password, bio, selectedFile);
    }

    /**
     * Handles the secondary button click, navigating the user to the sign-in interface.
     *
     * @param event The action event triggered by clicking the secondary button.
     */
    @Override
    protected void onSecondaryButtonCLick(ActionEvent event) {
        controller.showSignIn();
    }

    /**
     * Prompts the user to select a profile picture from their file system.
     * Sets the selected file as the profile picture for the new account.
     */
    public void handleProfilePictureUpload() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
    }
}
