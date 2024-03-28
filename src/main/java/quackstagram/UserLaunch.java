package quackstagram;

import javax.swing.SwingUtilities;
import quackstagram.views.prelogin.SignInUI;

/**
 * The {@code UserLaunch} class serves as the entry point for the Quackstagram application,
 * specifically initiating the user interface for sign-in.
 */
public class UserLaunch {
    public static void main(String[] args) {
        /**
         * Initializes the user interface on the Event Dispatch Thread (EDT),
         * ensuring thread safety for swing components.
         */
        SwingUtilities.invokeLater(() -> {
            SignInUI frame = new SignInUI();
            frame.setVisible(true);
        });
    }
}
