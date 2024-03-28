package quackstagram.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import quackstagram.views.postlogin.NotificationsUI;

/**
 * Represents a notification generated in response to an event in the Quackstagram application,
 * such as a user liking a picture. Implements the Observer interface as part of the Observer Design Pattern,
 * allowing notifications to be updated in response to observed events.
 */
public class Notification extends AbstractModel<Notification> implements Observer {
    private NotificationsUI notificationsUI; // Reference to the Notifications UI to update UI on notification
    private String username; // Username of the user whose image was liked
    private String likedBy; // Username of the user who liked the image
    private String pictureId; // ID of the picture that was liked
    private String date; // Date and time when the like occurred

    /**
     * Constructs a Notification with all necessary details.
     *
     * @param username The username of the user whose picture was liked.
     * @param likedBy The username of the user who liked the picture.
     * @param pictureId The ID of the picture that was liked.
     * @param date The date and time when the picture was liked.
     */
    public Notification(String username, String likedBy, String pictureId, String date) {
        this.username = username;
        this.likedBy = likedBy;
        this.pictureId = pictureId;
        this.date = date;
    }

    /**
     * Constructs a Notification with the current date and time automatically set.
     *
     * @param username The username of the user whose picture was liked.
     * @param likedBy The username of the user who liked the picture.
     * @param pictureId The ID of the picture that was liked.
     */
    public Notification(String username, String likedBy, String pictureId) {
        this.username = username;
        this.likedBy = likedBy;
        this.pictureId = pictureId;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = ZonedDateTime.now(TimeZone.getTimeZone("UTC").toZoneId()).format(formatter);
    }

    
    /**
     * Updates the notification by printing a message indicating that a user has liked a picture.
     * It also updates the notifications UI.
     * 
     * Part of the Observer Design Pattern
     */
    @Override
    public void update() {
        String message = String.format("User %s liked picture %s on %s", likedBy, pictureId, date);
        System.out.println(message);
        notificationsUI.updateNotifications();
    }

    /**
     * Sets the NotificationsUI instance for this notification to interact with.
     *
     * @param notificationsUI The NotificationsUI instance.
     */
    public void setNotificationsUI(NotificationsUI notificationsUI) {
        this.notificationsUI = notificationsUI;
    }

    /**
     * Factory method to create a Notification instance from an array of string arguments.
     *
     * @param args An array of strings containing notification details.
     * @return A new Notification instance.
     * @throws RuntimeException If the arguments do not match the expected format.
     */
    public static Notification createInstance(String[] args) throws RuntimeException {
        if (args.length != 4) {
            System.out.println(String.join(", ", args));
            throw new RuntimeException("Couldn't parse notifications line, expected 4 arguments!");
        }
        return new Notification(args[0], args[1], args[2], args[3]);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String[] serialize() {
        return new String[] { username, likedBy, pictureId, date };
    }

    @Override
    public boolean isUpdatable() {
        return false;
    }

    @Override
    public boolean isIdEqualTo(Notification notification) {
        return this.username.equals(notification.getUsername());
    }

    public String getMessage() {
        return likedBy + " liked your picture - " + getElapsedTime(date) + " ago";
    }

    private String getElapsedTime(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timeOfNotification = LocalDateTime.parse(timestamp, formatter);
        LocalDateTime currentTime = LocalDateTime.now();

        long daysBetween = ChronoUnit.DAYS.between(timeOfNotification, currentTime);
        long minutesBetween = ChronoUnit.MINUTES.between(timeOfNotification, currentTime) % 60;

        StringBuilder timeElapsed = new StringBuilder();
        if (daysBetween > 0) {
            timeElapsed.append(daysBetween).append(" day").append(daysBetween > 1 ? "s" : "");
        }
        if (minutesBetween > 0) {
            if (daysBetween > 0) {
                timeElapsed.append(" and ");
            }
            timeElapsed.append(minutesBetween).append(" minute").append(minutesBetween > 1 ? "s" : "");
        }
        return timeElapsed.toString();
    }
}
