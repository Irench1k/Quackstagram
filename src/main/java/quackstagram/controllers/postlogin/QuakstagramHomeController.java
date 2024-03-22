package quackstagram.controllers.postlogin;

import quackstagram.FileHandler;
import quackstagram.models.Notification;
import quackstagram.models.Picture;
import quackstagram.models.User;
import quackstagram.views.postlogin.NotificationsUI;
import quackstagram.views.postlogin.QuakstagramHomeUI;

public class QuakstagramHomeController {
    private QuakstagramHomeUI view;
    private User currentUser;

    public QuakstagramHomeController(QuakstagramHomeUI view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
    }

    public int addLike(Picture picture, NotificationsUI notificationsUI) {
        // Create a notification for the owner of the picture
        Notification ownerNotification = new Notification(picture.getOwner(), currentUser.getUsername(),
                picture.getPictureID());
        ownerNotification.setNotificationsUI(notificationsUI);
        picture.addObserver(ownerNotification);
        FileHandler.saveNotification(ownerNotification);

        // Create a notification for the user who liked the picture
        Notification userNotification = new Notification(currentUser.getUsername(), currentUser.getUsername(),
                picture.getPictureID());
        userNotification.setNotificationsUI(notificationsUI);
        picture.addObserver(userNotification);
        FileHandler.saveNotification(userNotification);

        // Add the like to the picture
        picture.addLike();
        FileHandler.savePicture(picture);

        return picture.getLikesCount();
    }
}
