package quackstagram.controllers.postlogin;

import quackstagram.utilities.FileHandler;
import quackstagram.models.Notification;
import quackstagram.models.Picture;
import quackstagram.models.User;
import quackstagram.views.postlogin.NotificationsUI;
import quackstagram.views.postlogin.QuakstagramHomeUI;

/**
 * The {@code QuakstagramHomeController} class manages the home UI of the Quakstagram application,
 * facilitating interactions such as liking pictures and receiving notifications.
 */
public class QuakstagramHomeController {
    private QuakstagramHomeUI view;
    private User currentUser;

    /**
     * Constructs a {@code QuakstagramHomeController} with the specified home view and current user.
     *
     * @param view The {@link QuakstagramHomeUI} that this controller manages.
     * @param currentUser The {@link User} currently logged in and interacting with the home view.
     */
    public QuakstagramHomeController(QuakstagramHomeUI view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
    }

    /**
     * Adds a like to a given picture and updates the picture's like count.
     * If the current user is not the owner of the picture, a notification is created
     * and saved for the owner of the picture to inform them of the like.
     *
     * @param picture The {@link Picture} object being liked.
     * @param notificationsUI The {@link NotificationsUI} object used to update notifications view when a new like is added.
     * @return The updated like count of the picture after adding the like.
     */
    public int addLike(Picture picture, NotificationsUI notificationsUI) {
        // Add the like to the picture
        picture.addLike();
        FileHandler.savePicture(picture);
    
        // Check if the current user is the owner of the picture
        if (!currentUser.getUsername().equals(picture.getOwner())) {
            // Create a notification for the owner of the picture
            Notification notification = new Notification(picture.getOwner(), currentUser.getUsername(),
                    picture.getPictureID());
            notification.setNotificationsUI(notificationsUI);
            picture.addObserver(notification);
            FileHandler.saveNotification(notification);
        }
    
        return picture.getLikesCount();
    }
       
}
