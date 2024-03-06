import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

}
