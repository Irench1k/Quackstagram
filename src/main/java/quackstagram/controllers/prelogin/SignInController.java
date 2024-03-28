package quackstagram.controllers.prelogin;

import quackstagram.utilities.FileHandler;
import quackstagram.models.User;
import quackstagram.views.postlogin.InstagramProfileUI;
import quackstagram.views.prelogin.SignInUI;
import quackstagram.views.prelogin.SignUpUI;

/**
 * The {@code SignInController} class manages the sign-in process,
 * allowing users to log in to the Quackstagram application.
 * It validates user credentials and navigates to the profile UI upon successful login.
 */
public class SignInController {
    public SignInUI view;

    /**
     * Constructs a {@code SignInController} with the specified sign-in view.
     *
     * @param view The {@link SignInUI} that this controller will manage.
     */
    public SignInController(SignInUI view) {
        this.view = view;
    }

    /**
     * Attempts to log in a user with the provided username and password.
     * On successful authentication, navigates to the user's profile UI.
     * Displays an error and remains on the sign-in view upon failure.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user.
     */
    public void logIn(String username, String password) {
        User user;
        try {
            user = FileHandler.getUser(username);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (user.isPasswordEqual(password)) {
            showProfileUI(user);
        } else {
            System.out.println("Login Failed");
        }
    }

    /**
     * Attempts to log in a user with the provided username, password, and passCode (for two-factor authentication).
     * On successful authentication, navigates to the user's profile UI.
     * Displays an error and remains on the sign-in view upon failure.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user.
     * @param passCode The two-factor authentication code of the user.
     */
    public void logIn(String username, String password, String passCode) {
        User user;
        try {
            user = FileHandler.getUser(username);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (user.isPasswordEqual(password) && user.isPassCodeEqual(passCode)) {
            showProfileUI(user);
        } else {
            System.out.println("Login Failed");
        }
    }

    /**
     * Navigates to the profile UI of the specified user.
     * Disposes of the current sign-in view and displays the profile view.
     *
     * @param user The {@link User} whose profile UI should be displayed.
     */
    protected void showProfileUI(User user) {
        view.dispose();
        InstagramProfileUI profileUI = new InstagramProfileUI(user);
        profileUI.setVisible(true);
    }

    /**
     * Navigates to the sign-up view.
     * Disposes of the current sign-in view and displays the sign-up view.
     */
    public void showSignUp() {
        view.dispose();
        SignUpUI signUpFrame = new SignUpUI();
        signUpFrame.setVisible(true);
    }

    /**
     * Redisplays the sign-in view.
     * Useful for navigating back to the sign-in view from other views.
     */
    public void showSignIn() {
        view.dispose();
        view.setVisible(true);
    }
}
