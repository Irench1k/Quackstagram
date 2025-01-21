package quackstagram.controllers.prelogin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import quackstagram.utilities.FileHandler;
import quackstagram.models.User;
import quackstagram.views.prelogin.SignInUI;
import quackstagram.views.prelogin.SignInUIDecorator;
import quackstagram.views.prelogin.SignUpUI;

/**
 * The {@code SignUpController} class manages the sign-up process,
 * allowing new users to create an account in the Quackstagram application.
 * It handles user input validation, profile picture upload, and user data persistence.
 */
public class SignUpController {
    public SignUpUI view;

    /**
     * Constructs a {@code SignUpController} with the specified sign-up view.
     *
     * @param view The {@link SignUpUI} that this controller will manage.
     */
    public SignUpController(SignUpUI view) {
        this.view = view;
    }

    /**
     * Attempts to register a new user with the provided username, password, bio, and passcode.
     * If the username already exists, shows an error message. Otherwise, it saves the new user
     * and their profile picture.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param bio The bio of the new user.
     * @param passCode The passcode for two-factor authentication.
     * @param selectedFile The profile picture file for the new user.
     */
    public void signUp(String username, String password, String bio, String passCode, File selectedFile) {
        try {
            FileHandler.getUser(username);
            JOptionPane.showMessageDialog(view,
                    "Username already exists. Please choose a different username.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception e) {
            // User does not exist - expected
        }

        User newUser = new User(username, password, bio, passCode, new ArrayList<String>(), 0, 0);

        // All checks done, save stuff
        // TODO: if one of the saving fails, revert has to be done
        FileHandler.saveUser(newUser);
        saveProfilePicture(selectedFile, username);

        showSignInDecorator();
    }

    /**
     * Overloaded method to register a new user without a passcode for two-factor authentication.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param bio The bio of the new user.
     * @param selectedFile The profile picture file for the new user.
     */
    public void signUp(String username, String password, String bio, File selectedFile) {
        try {
            FileHandler.getUser(username);
            JOptionPane.showMessageDialog(view,
                    "Username already exists. Please choose a different username.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception e) {
            // User does not exist - expected
        }

        User newUser = new User(username, password, bio, new ArrayList<String>(), 0, 0);

        // All checks done, save stuff
        // TODO: if one of the saving fails, revert has to be done
        FileHandler.saveUser(newUser);
        saveProfilePicture(selectedFile, username);

        showSignIn();
    }

    /**
     * Saves the uploaded profile picture to the specified user's profile.
     *
     * @param file The file to be uploaded as the profile picture.
     * @param username The username of the user to whom the profile picture belongs.
     */
    private void saveProfilePicture(File file, String username) {
        try {
            FileHandler.uploadProfilePicture(file, username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the sign-in view.
     */
    public void showSignIn() {
        view.dispose();
        SignInUI signInFrame = new SignInUI();
        signInFrame.setVisible(true);
    }

    /**
     * Shows the sign-in view with the two-factor authentication decorator.
     */
    public void showSignInDecorator() {
        view.dispose();
        SignInUIDecorator signInFrame = new SignInUIDecorator(new SignInUI());
        signInFrame.setVisible(true);
    }

    /**
     * Redisplays the sign-up view.
     * Useful for navigating back to the sign-up view from other views.
     */
    public void showSignUp() {
        view.dispose();
        view.setVisible(true);
    }
}
