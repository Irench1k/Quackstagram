import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The ImageLikesManager class is responsible for managing likes on images.
 * It provides methods to like an image, read likes from a file, and save likes
 * to a file.
 */
public class ImageLikesManager {

    private final String likesFilePath = "data/likes.txt";

    /**
     * Method to like an image
     * 
     * Adds a like to the specified image by the given username.
     * If the image has not been liked by any user before, a new entry is created in
     * the likes map.
     * The likes map is then updated and saved to persist the changes.
     *
     * @param username the username of the user liking the image
     * @param imageID  the ID of the image being liked
     * @throws IOException if an error occurs while reading or saving the likes map
     */
    public void likeImage(String username, String imageID) throws IOException {
        Map<String, Set<String>> likesMap = readLikes();
        if (!likesMap.containsKey(imageID)) {
            likesMap.put(imageID, new HashSet<>());
        }
        Set<String> users = likesMap.get(imageID);
        if (users.add(username)) { // Only add and save if the user hasn't already liked the image
            saveLikes(likesMap);
        }
    }

    /**
     * Method to read likes from file
     * 
     * Reads the likes data from a file and returns a map of image IDs to the set of
     * users who liked each image.
     *
     * @return a map containing image IDs as keys and sets of users as values
     * @throws IOException if an I/O error occurs while reading the file
     */
    private Map<String, Set<String>> readLikes() throws IOException {
        Map<String, Set<String>> likesMap = new HashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(likesFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String imageID = parts[0];
                Set<String> users = Arrays.stream(parts[1].split(",")).collect(Collectors.toSet());
                likesMap.put(imageID, users);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        reader.close();
        return likesMap;
    }

    /**
     * Method to save likes to file
     * 
     * Saves the likes map to a file.
     *
     * @param likesMap the map containing image IDs as keys and sets of user IDs as
     *                 values
     * @throws IOException if an I/O error occurs while writing to the file
     */
    private void saveLikes(Map<String, Set<String>> likesMap) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(likesFilePath, false));
            for (Map.Entry<String, Set<String>> entry : likesMap.entrySet()) {
                String line = entry.getKey() + ":" + String.join(",", entry.getValue());
                writer.write(line);
                writer.newLine();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
