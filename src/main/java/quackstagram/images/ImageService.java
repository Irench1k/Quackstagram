package src.main.java.quackstagram.images;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import src.main.java.quackstagram.*;
import src.main.java.quackstagram.images.*;
import src.main.java.quackstagram.ui.*;
import src.main.java.quackstagram.user.*;

/*
 * The ImageUploadUI class is currently responsible for both the user interface and the business logic 
 * (like reading the username, getting the next image ID, saving image info, etc.).
 * This is a violation of the Single Responsibility Principle, which states that a class should have only one 
 * reason to change.
 * 
 * So i decided to refactor the ImageUploadUI class by creating a new class called ImageService to handle
 * the business logic for uploading images and saving image information.
 */

/**
 * The ImageService class provides methods for reading, saving, and processing
 * images.
 * It handles operations such as reading the username from a file, generating
 * the next image ID, saving image information,
 * retrieving the file extension, and processing the image file.
 */
public class ImageService {
    private static final String USERS_FILE_PATH = "data/users.txt"; // not sure if i can implement this
    private static final String IMAGE_DIR = "img/uploaded";
    private static final String IMAGE_DETAILS_FILE_PATH = "img/image_details.txt"; // not sure if i can implement this

    /**
     * Reads the username from the users.txt file.
     *
     * @return The username extracted from the first line of the file, or null if no
     *         username is found.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String readUsername() throws IOException {
        Path usersFilePath = Paths.get("data", "users.txt");
        try (BufferedReader reader = Files.newBufferedReader(usersFilePath)) {
            String line = reader.readLine();
            if (line != null) {
                return line.split(":")[0]; // Extract the username from the first line
            }
        }
        return null; // Return null if no username is found
    }

    /**
     * Retrieves the next available image ID for a given username.
     * The image ID is determined by searching for existing image files in the
     * storage directory
     * and finding the highest numeric ID associated with the username.
     *
     * @param username the username for which to retrieve the next image ID
     * @return the next available image ID
     * @throws IOException if an I/O error occurs while accessing the storage
     *                     directory
     */
    public int getNextImageId(String username) throws IOException {
        Path storageDir = Paths.get("img", "uploaded"); // Ensure this is the directory where images are saved
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }

        int maxId = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(storageDir, username + "_*")) {
            for (Path path : stream) {
                String fileName = path.getFileName().toString();
                int idEndIndex = fileName.lastIndexOf('.');
                if (idEndIndex != -1) {
                    String idStr = fileName.substring(username.length() + 1, idEndIndex);
                    try {
                        int id = Integer.parseInt(idStr);
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException ex) {
                        // Ignore filenames that do not have a valid numeric ID
                    }
                }
            }
        }
        return maxId + 1; // Return the next available ID
    }

    /**
     * Saves the image information to a file.
     *
     * @param imageId  the ID of the image
     * @param username the username associated with the image
     * @param bio      the bio associated with the image
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void saveImageInfo(String imageId, String username, String bio) throws IOException {
        Path infoFilePath = Paths.get("img", "image_details.txt");
        if (!Files.exists(infoFilePath)) {
            Files.createFile(infoFilePath);
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (BufferedWriter writer = Files.newBufferedWriter(infoFilePath, StandardOpenOption.APPEND)) {
            writer.write(String.format("ImageID: %s, Username: %s, Bio: %s, Timestamp: %s, Likes: 0", imageId, username,
                    bio, timestamp));
            writer.newLine();
        }
    }

    /**
     * Returns the file extension of the given file.
     *
     * @param file the file for which to determine the extension
     * @return the file extension, or an empty string if the file has no extension
     */
    public String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }

    /**
     * Processes the selected image file by copying it to the user's image
     * directory,
     * saving the image information to a text file, and loading the image for
     * preview.
     *
     * @param selectedFile      the selected image file to be processed
     * @param username          the username of the user
     * @param imagePreviewLabel the label to display the preview of the processed
     *                          image
     * @param bioTextArea       the text area containing the user's bio
     * @throws IOException if an I/O error occurs during the file operations
     */
    public void processImage(File selectedFile, String username, JLabel imagePreviewLabel, JTextArea bioTextArea)
            throws IOException {
        int imageId = getNextImageId(username);
        String fileExtension = getFileExtension(selectedFile);
        String newFileName = username + "_" + imageId + "." + fileExtension;

        Path destPath = Paths.get(IMAGE_DIR, newFileName);
        Files.copy(selectedFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);

        // Save the bio and image ID to a text file
        saveImageInfo(username + "_" + imageId, username, bioTextArea.getText());

        // Load the image from the saved path
        ImageIcon imageIcon = new ImageIcon(destPath.toString());

        // Scale and set the image if imagePreviewLabel has a valid size
        if (imagePreviewLabel.getWidth() > 0 && imagePreviewLabel.getHeight() > 0) {
            scaleAndSetImage(imageIcon, imagePreviewLabel);
        }
    }

    /**
     * A helper method for processImage that scales and sets the image for the
     * imagePreviewLabel.
     *
     * @param imageIcon         the image icon to be scaled and set
     * @param imagePreviewLabel the label where the scaled image will be displayed
     */
    private void scaleAndSetImage(ImageIcon imageIcon, JLabel imagePreviewLabel) {
        Image image = imageIcon.getImage();

        // Calculate the dimensions for the image preview
        int previewWidth = imagePreviewLabel.getWidth();
        int previewHeight = imagePreviewLabel.getHeight();
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double widthRatio = (double) previewWidth / imageWidth;
        double heightRatio = (double) previewHeight / imageHeight;
        double scale = Math.min(widthRatio, heightRatio);
        int scaledWidth = (int) (scale * imageWidth);
        int scaledHeight = (int) (scale * imageHeight);

        // Set the image icon with the scaled image
        imageIcon.setImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        imagePreviewLabel.setIcon(imageIcon);
    }

}