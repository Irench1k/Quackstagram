package quackstagram.controllers.postlogin;

import quackstagram.utilities.FileHandler;
import quackstagram.models.User;
import quackstagram.views.postlogin.InstagramProfileUI;


/**
 * The {@code InstagramProfileController} class is responsible for managing the interaction between the profile view and the model.
 * It facilitates operations such as viewing a user's profile and following/unfollowing other users within the Quackstagram application.
 */
public class InstagramProfileController {
    private InstagramProfileUI view;
    private User currentUser;
    private User targetUser;

    /**
     * Constructs an {@code InstagramProfileController} with the specified profile view, the current user, and the target user whose profile is to be displayed.
     *
     * @param view The {@link InstagramProfileUI} that this controller will manage.
     * @param currentUser The {@link User} who is currently logged in and interacting with the application.
     * @param targetUser The {@link User} whose profile is being viewed or interacted with.
     */
    public InstagramProfileController(InstagramProfileUI view, User currentUser, User targetUser) {
        this.view = view;
        this.currentUser = currentUser;
        this.targetUser = targetUser;
    }

    /**
     * Displays the profile UI for the target user.
     * Disposes of the current profile view if it exists and creates a new instance of {@link InstagramProfileUI}
     * to display the profile of the target user.
     */
    public void showProfileUI() {
        view.dispose();
        InstagramProfileUI profileUI = new InstagramProfileUI(currentUser, targetUser);
        profileUI.setVisible(true);
    }

    /**
     * Handles the action of the current user deciding to follow the target user.
     * This method updates the current user's list of followed users to include the target user
     * and saves the updated user information to persistent storage.
     */
    public void handleFollowAction() {
        this.currentUser.addUserToFollow(targetUser);
        FileHandler.saveUser(this.currentUser);
    }
}
