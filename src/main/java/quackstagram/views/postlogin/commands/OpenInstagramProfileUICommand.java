package quackstagram.views.postlogin.commands;

import javax.swing.JFrame;

import quackstagram.models.User;
import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.InstagramProfileUI;
import quackstagram.views.postlogin.NavigationCommand;

/**
 * A command class that encapsulates the action of opening the InstagramProfileUI.
 * Implements the NavigationCommand interface to execute this specific navigation action.
 */
public class OpenInstagramProfileUICommand extends JFrame implements NavigationCommand {
    private AbstractPostLogin ui;

    /**
     * Constructs an OpenInstagramProfileUICommand with a reference to the current UI.
     *
     * @param ui The current user interface from which we are navigating.
     */
    public OpenInstagramProfileUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    /**
     * Executes the command to open the InstagramProfileUI, making it visible to the user.
     * This method is part of the NavigationCommand interface implementation.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    @Override
    public void execute(User currentUser) {
        openProfileUI(currentUser);
    }

    /**
     * Opens the InstagramProfileUI frame and makes it visible. It disposes of the current UI
     * frame as part of the navigation process. This allows users to view and interact with their own or another user's profile.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    public void openProfileUI(User currentUser) {
        // Open InstagramProfileUI frame
        this.dispose();
        InstagramProfileUI profileUI = new InstagramProfileUI(currentUser, currentUser);
        profileUI.setVisible(true);
    }
}
