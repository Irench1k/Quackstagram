package quackstagram.controllers.postlogin;

import quackstagram.FileHandler;
import quackstagram.models.Notification;
import quackstagram.models.Picture;
import quackstagram.models.User;
import quackstagram.views.postlogin.QuakstagramHomeUI;

public class QuakstagramHomeController {
    private QuakstagramHomeUI view;
    private User currentUser;

    public QuakstagramHomeController(QuakstagramHomeUI view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
    }

    public int addLike(Picture picture) {
        picture.addLike();
        FileHandler.savePicture(picture);
        Notification notification = new Notification(picture.getOwner(), currentUser.getUsername(),
                picture.getPictureID());
        FileHandler.saveNotification(notification);
        return picture.getLikesCount();
    }
}
