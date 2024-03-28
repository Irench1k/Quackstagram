package quackstagram.views.postlogin.commands;

import javax.swing.JFrame;

import quackstagram.models.User;
import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.NavigationCommand;
import quackstagram.views.postlogin.NotificationsUI;
import quackstagram.views.postlogin.QuakstagramHomeUI;

/**
 * A command class that encapsulates the action of opening the QuakstagramHomeUI.
 * Implements the NavigationCommand interface to execute this specific navigation action.
 */
public class OpenHomeUICommand extends JFrame implements NavigationCommand {
    private AbstractPostLogin ui;

    /**
     * Constructs an OpenHomeUICommand with a reference to the current UI.
     *
     * @param ui The current user interface from which we are navigating.
     */
    public OpenHomeUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    /**
     * Executes the command to open the QuakstagramHomeUI, making it visible to the user.
     * This method is part of the NavigationCommand interface implementation.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    @Override
    public void execute(User currentUser) {
        openHomeUI(currentUser);
    }

    /**
     * Opens the QuakstagramHomeUI frame and makes it visible. It disposes of the current UI
     * frame as part of the navigation process. Also initializes and integrates the NotificationsUI
     * with the home UI.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    public void openHomeUI(User currentUser) {
        // Open InstagramProfileUI frame
        this.dispose();
        NotificationsUI notificationsUI = new NotificationsUI(currentUser);
        QuakstagramHomeUI homeUI = new QuakstagramHomeUI(currentUser, notificationsUI);
        homeUI.setVisible(true);
    }
}