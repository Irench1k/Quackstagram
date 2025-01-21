package quackstagram.views.postlogin.commands;

import javax.swing.JFrame;

import quackstagram.models.User;
import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.ExploreUI;
import quackstagram.views.postlogin.NavigationCommand;
import quackstagram.views.postlogin.QuakstagramHomeUI;

/**
 * A command class that encapsulates the action of opening the ExploreUI.
 * Implements the NavigationCommand interface to execute this specific navigation action.
 */
public class OpenExploreUICommand extends JFrame implements NavigationCommand {
    private AbstractPostLogin ui;

    /**
     * Constructs an OpenExploreUICommand with a reference to the current UI.
     *
     * @param ui The current user interface from which we are navigating.
     */
    public OpenExploreUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    /**
     * Executes the command to open the ExploreUI, making it visible to the user.
     * This method is part of the NavigationCommand interface implementation.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    @Override
    public void execute(User currentUser) {
        exploreUI(currentUser);
    }

    /**
     * Opens the ExploreUI frame and makes it visible. It disposes of the current UI
     * frame as part of the navigation process.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    public void exploreUI(User currentUser) {
        // Open InstagramProfileUI frame
        this.dispose();
        ExploreUI explore = new ExploreUI(currentUser);
        explore.setVisible(true);
    }
}