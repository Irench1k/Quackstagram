package quackstagram.views.postlogin.commands;

import javax.swing.JFrame;

import quackstagram.models.User;
import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.NavigationCommand;
import quackstagram.views.postlogin.NotificationsUI;

/**
 * A command class that encapsulates the action of opening the NotificationsUI.
 * Implements the NavigationCommand interface to execute this specific navigation action.
 */
public class OpenNotificationUICommand extends JFrame implements NavigationCommand {
    private AbstractPostLogin ui;

    /**
     * Constructs an OpenNotificationUICommand with a reference to the current UI.
     *
     * @param ui The current user interface from which we are navigating.
     */
    public OpenNotificationUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    /**
     * Executes the command to open the NotificationsUI, making it visible to the user.
     * This method is part of the NavigationCommand interface implementation.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    @Override
    public void execute(User currentUser) {
        notificationsUI(currentUser);
    }

    /**
     * Opens the NotificationsUI frame and makes it visible. It disposes of the current UI
     * frame as part of the navigation process. This allows users to view and interact with their notifications.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    public void notificationsUI(User currentUser) {
        this.dispose();
        NotificationsUI notificationsUI = new NotificationsUI(currentUser);
        notificationsUI.setVisible(true);
    }
}
