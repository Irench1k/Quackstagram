package quackstagram.controllers.postlogin;

import quackstagram.utilities.FileHandler;
import quackstagram.models.User;
import quackstagram.views.postlogin.ExploreUI;
import quackstagram.views.postlogin.InstagramProfileUI;

/**
 * The {@code ExploreController} class is responsible for managing the interaction between the explore view and the model.
 * It handles navigation from the explore view to a user's profile view when a picture is clicked.
 */
public class ExploreController {
    private ExploreUI view;
    private User currentUser;

    /**
     * Constructs an {@code ExploreController} with the specified explore view and the current user.
     *
     * @param view The {@link ExploreUI} that this controller will manage.
     * @param currentUser The {@link User} who is currently logged in and interacting with the explore view.
     */
    public ExploreController(ExploreUI view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
    }

    /**
     * Navigates to the profile UI of the user who owns the clicked picture.
     * This method retrieves the user associated with the given username and disposes of the current explore view
     * before displaying the target user's profile UI.
     *
     * @param username The username of the user whose profile should be displayed. This user owns the clicked picture.
     */
    public void goToUserProfile(String username) {
        User pictureOwner;
        try {
            pictureOwner = FileHandler.getUser(username);
        } catch (Exception error) {
            error.printStackTrace();
            return;
        }
        view.dispose();

        InstagramProfileUI profileUI = new InstagramProfileUI(currentUser, pictureOwner);
        profileUI.setVisible(true);
    }
}
