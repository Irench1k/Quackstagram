package quackstagram.views.postlogin;

import quackstagram.models.User;

/**
 * The {@code NavigationCommand} interface defines a contract for navigation actions within the Quackstagram application.
 * Implementing classes are responsible for executing specific navigation operations, such as switching between different
 * UI views or performing a series of navigational tasks.
 */
public interface NavigationCommand {

    /**
     * Executes the navigation command using the provided user context. This method defines the action to be taken,
     * such as opening a new user interface view or performing a specific operation related to the user's navigation
     * within the application.
     *
     * @param currentUser The user context in which the command is executed. This might be the currently logged-in user
     *                    or a target user profile that the current user wishes to view or interact with.
     */
    void execute(User currentUser);
}
