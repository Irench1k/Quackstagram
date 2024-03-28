package quackstagram.views.postlogin.commands;

import javax.swing.JFrame;

import quackstagram.models.User;
import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.ImageUploadUI;
import quackstagram.views.postlogin.NavigationCommand;
import quackstagram.views.postlogin.QuakstagramHomeUI;

/**
 * A command class that encapsulates the action of opening the ImageUploadUI.
 * Implements the NavigationCommand interface to execute this specific navigation action.
 */
public class OpenImageUploadUICommand extends JFrame implements NavigationCommand {
    private AbstractPostLogin ui;

    /**
     * Constructs an OpenImageUploadUICommand with a reference to the current UI.
     *
     * @param ui The current user interface from which we are navigating.
     */
    public OpenImageUploadUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    /**
     * Executes the command to open the ImageUploadUI, making it visible to the user.
     * This method is part of the NavigationCommand interface implementation.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    @Override
    public void execute(User currentUser) {
        imageUploadUI(currentUser);
    }

    /**
     * Opens the ImageUploadUI frame and makes it visible. It disposes of the current UI
     * frame as part of the navigation process. This allows users to upload new images to their profile.
     *
     * @param currentUser The User object representing the currently logged-in user.
     */
    public void imageUploadUI(User currentUser) {
        // Open InstagramProfileUI frame
        this.dispose();
        ImageUploadUI upload = new ImageUploadUI(currentUser);
        upload.setVisible(true);
    }

}