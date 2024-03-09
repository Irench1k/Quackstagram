package src.main.java.quackstagram.images;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JLabel;

import src.main.java.quackstagram.*;
import src.main.java.quackstagram.images.*;
import src.main.java.quackstagram.ui.*;
import src.main.java.quackstagram.user.*;

public class ImageLikesManager {

    private final String likesFilePath = "data/likes.txt";

    /**
     * Likes an image by a user.
     * 
     * @param user  the user who likes the image
     * @param image the image to be liked
     * @throws IOException if there is an error reading or saving the likes data
     */
    public void likeImage(User user, Picture image) throws IOException {
        Map<String, Set<String>> likesMap = readLikes();
        String imageID = image.getImageID();

        if (!likesMap.containsKey(imageID)) {
            likesMap.put(imageID, new HashSet<>());
        }
        Set<String> users = likesMap.get(imageID);
        if (users.add(user.getUsername())) { // Only add and save if the user hasn't already liked the image
            saveLikes(likesMap);
        }
    }

    /**
     * Reads the likes from a file and returns a map containing the likes data.
     *
     * @return a map containing the likes data, where the keys are image IDs and the
     *         values are sets of user IDs
     * @throws IOException if an I/O error occurs while reading the file
     */
    private Map<String, Set<String>> readLikes() throws IOException {
        Map<String, Set<String>> likesMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(likesFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(likesMap, line);
            }
        }
        return likesMap;
    }

    /**
     * Processes a line of input and updates the likesMap accordingly.
     *
     * @param likesMap the map containing image IDs as keys and sets of users as
     *                 values
     * @param line     the line of input to be processed
     */
    private void processLine(Map<String, Set<String>> likesMap, String line) {
        String[] parts = line.split(":");
        String imageID = parts[0];
        Set<String> users = Arrays.stream(parts[1].split(",")).collect(Collectors.toSet());
        likesMap.put(imageID, users);
    }

    /**
     * Saves the likes map to a file.
     * 
     * @param likesMap the map containing the likes data, where the key is the image
     *                 ID and the value is a set of user IDs who liked the image
     * @throws IOException if an I/O error occurs while writing to the file
     */
    private void saveLikes(Map<String, Set<String>> likesMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(likesFilePath, false))) {
            for (Map.Entry<String, Set<String>> entry : likesMap.entrySet()) {
                writeLine(writer, entry);
            }
        }
    }

    /**
     * Writes a line to the BufferedWriter with the given entry.
     *
     * @param writer The BufferedWriter to write the line to.
     * @param entry  The Map.Entry containing the key and value to be written.
     * @throws IOException If an I/O error occurs while writing the line.
     */
    private void writeLine(BufferedWriter writer, Map.Entry<String, Set<String>> entry) throws IOException {
        String line = entry.getKey() + ":" + String.join(",", entry.getValue());
        writer.write(line);
        writer.newLine();
    }

    /**
     * Handles the action of liking a post, updating the like count in the UI and
     * persisting the change to a file.
     *
     * @param imageId    The ID of the image being liked.
     * @param likesLabel The JLabel displaying the current like count for the post.
     */
    public int handleLikeAction(String imageId, JLabel likesLabel) {
        Path detailsPath = Paths.get("img", "image_details.txt");
        StringBuilder newContent = new StringBuilder();
        boolean updated = false;
        String currentUser = "";
        String imageOwner = "";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int updatedLikesCount = -1;

        // Retrieve the current user from users.txt
        try (BufferedReader userReader = Files.newBufferedReader(Paths.get("data", "users.txt"))) {
            String line = userReader.readLine();
            if (line != null) {
                currentUser = line.split(":")[0].trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read and update image_details.txt
        try (BufferedReader reader = Files.newBufferedReader(detailsPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ImageID: " + imageId)) {
                    String[] parts = line.split(", ");
                    imageOwner = parts[1].split(": ")[1];
                    int likes = Integer.parseInt(parts[4].split(": ")[1]);
                    updatedLikesCount = ++likes; // Increment the likes count
                    parts[4] = "Likes: " + likes;
                    line = String.join(", ", parts);

                    // Update the UI
                    likesLabel.setText("Likes: " + likes);
                    updated = true;
                }
                newContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated likes back to image_details.txt
        if (updated) {
            try (BufferedWriter writer = Files.newBufferedWriter(detailsPath)) {
                writer.write(newContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Record the like in notifications.txt
            String notification = String.format("%s; %s; %s; %s\n", imageOwner, currentUser, imageId, timestamp);
            try (BufferedWriter notificationWriter = Files.newBufferedWriter(Paths.get("data", "notifications.txt"),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                notificationWriter.write(notification);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updatedLikesCount;
    }

}
