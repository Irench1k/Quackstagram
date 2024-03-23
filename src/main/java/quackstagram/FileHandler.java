package quackstagram;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.imageio.ImageIO;

import quackstagram.models.AbstractModel;
import quackstagram.models.Notification;
import quackstagram.models.Picture;
import quackstagram.models.User;

public class FileHandler {
    private static final Path NOTIFICATIONS_FILE = Paths.get("data", "notifications.txt");
    private static final Path PICTURES_FILE = Paths.get("data", "pictures.txt");
    private static final Path USERS_FILE = Paths.get("data", "users.txt");
    private static final Path PROFILE_PICTURE_DIR = Paths.get("img", "profile");
    private static final Path UPLOADS_PICTURE_DIR = Paths.get("img", "uploaded");

    public static User getUser(String username) throws Exception {
        ArrayList<User> users = readFile(USERS_FILE, User::createInstance);

        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }

        throw new Exception("No such user " + username + " exist");
    }

    public static void saveUser(User user) {
        saveToFile(USERS_FILE, user, User::createInstance);
    }

    public static Picture getPictureById(String pictureId) throws Exception {
        ArrayList<Picture> pictures = readFile(PICTURES_FILE, Picture::createInstance);

        for (Picture picture : pictures) {
            if (pictureId.equals(picture.getPictureID())) {
                return picture;
            }
        }

        throw new Exception("No such picture " + pictureId + " exist");
    }

    // username == null -> return all pictures
    public static ArrayList<Picture> getUserPictures(String username) {
        ArrayList<Picture> allPictures = readFile(PICTURES_FILE, Picture::createInstance);
        ArrayList<Picture> userPictures = new ArrayList<Picture>();

        for (Picture picture : allPictures) {
            if (username == null || username.equals(picture.getOwner())) {
                userPictures.add(picture);
            }
        }

        return userPictures;
    }

    public static void savePicture(Picture picture) {
        saveToFile(PICTURES_FILE, picture, Picture::createInstance);
    }

    public static void uploadImage(File file, Picture picture) throws IOException {
        BufferedImage image = ImageIO.read(file);
        File outputFile = UPLOADS_PICTURE_DIR.resolve(picture.getPictureID() + ".png").toFile();
        ImageIO.write(image, "png", outputFile);
    }

    public static void uploadProfilePicture(File file, String username) throws IOException {
        BufferedImage image = ImageIO.read(file);
        File outputFile = PROFILE_PICTURE_DIR.resolve(username + ".png").toFile();
        ImageIO.write(image, "png", outputFile);
    }

    // All notifications *FOR SELECTED USER* (i.e. all notifications they should
    // see)
    public static ArrayList<Notification> getNotifications(String username) {
        ArrayList<Notification> allNotifications = readFile(NOTIFICATIONS_FILE, Notification::createInstance);
        ArrayList<Notification> userNotifications = new ArrayList<Notification>();

        for (Notification notification : allNotifications) {
            // if username == zero element in the notifications.txt, this user received like
            if (username.equals(notification.getUsername())) {
                userNotifications.add(notification);
            }
        }

        return userNotifications;
    }

    public static void saveNotification(Notification notification) {
        saveToFile(NOTIFICATIONS_FILE, notification, Notification::createInstance);
    }

    private static <T> ArrayList<T> readFile(Path filePath, Function<String[], T> instanceCreator) {
        ArrayList<T> result = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] arguments = line.split("; ");
                // Note that we have to use `.apply()` to call the function here, because it was
                // passed as Function object to readFile
                result.add(instanceCreator.apply(arguments));
            }
        } catch (IOException e) {
            // The file is corrupt?
            System.out.println("File path: (" + filePath + ") could not be read");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // If update == true and object already exists in a file, update its line
    // If update == false, ALWAYS create a new line
    // Existing object is detected by comparing the zero element in a file
    private static <T extends AbstractModel<T>> void saveToFile(Path filePath, T object,
            Function<String[], T> instanceCreator) {
        ArrayList<T> existingData = readFile(filePath, instanceCreator);

        if (object.isUpdatable()) {
            // For example, if "mike" exists in the file, rewrite "mike's" line to update
            // its data (e.g. followers, likes, etc)
            boolean foundInFile = false;
            for (int i = 0; i < existingData.size(); i++) {
                T line = existingData.get(i);
                if (object.isIdEqualTo(line)) {
                    foundInFile = true;
                    existingData.set(i, object);
                    continue;
                }
            }

            if (!foundInFile) {
                existingData.add(0, object);
            }
        } else {
            existingData.add(0, object);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (T line : existingData) {
                String lineAsString = String.join("; ", line.serialize());
                writer.write(lineAsString);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserBio(String username) {
        // Read all users
        ArrayList<User> users = readFile(USERS_FILE, User::createInstance);

        // Find the user and update their bio
        users.forEach(user -> {
            if (user.getUsername().equals(username)) {
                user.setBio(""); // Update the bio to an empty string
            }
        });

        // Save the updated users back to the file
        saveAllUsers(users);
    }

    private static void saveAllUsers(ArrayList<User> users) {
        try (BufferedWriter writer = Files.newBufferedWriter(USERS_FILE)) {
            for (User user : users) {
                String line = String.join("; ", user.serialize());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}