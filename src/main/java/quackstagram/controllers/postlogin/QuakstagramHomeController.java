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
        Notification notification = new Notification(picture.getOwner(), currentUser.getUsername(),
                picture.getPictureID());
        notification.setNotificationsUI(notificationsUI); // Set the NotificationsUI instance
        picture.addObserver(notification);
        picture.addLike();
        FileHandler.savePicture(picture);
        FileHandler.saveNotification(notification);
        return picture.getLikesCount();
    }
}
