package quackstagram.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.file.Files;

public class ModeratorModel {
    private static final Path USERS_FILE = Paths.get("data", "users.txt");
    private static final Path PICTURES_FILE = Paths.get("data", "pictures.txt");
    private List<User> userList;

    public ModeratorModel() {
        userList = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = Files.newBufferedReader(USERS_FILE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("; ");
                User user = User.createInstance(userDetails);
                userList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getUserByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<Picture> getPicturesForUser(String username) {
        List<Picture> pictures = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(PICTURES_FILE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] pictureDetails = line.split("; ");
                if (pictureDetails[1].equals(username)) {
                    pictures.add(Picture.createInstance(pictureDetails));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort pictures by timestamp
        pictures.sort(Comparator.comparing(Picture::getDate).reversed());
        return pictures;
    }
}
