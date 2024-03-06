import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The ImageUploadUI class represents a graphical user interface for uploading
 * images and entering captions.
 * It extends the JFrame class and provides methods for initializing the UI
 * components and handling user actions.
 * The UI consists of an image preview, a text area for entering captions, and
 * buttons for uploading images and saving captions.
 * The class also provides methods for reading the username, generating unique
 * image IDs, saving image information, and getting file extensions.
 */
public class ImageUploadUI extends AbstractUI {
    private JLabel imagePreviewLabel;
    private JTextArea bioTextArea;
    private JButton uploadButton;
    private JButton saveButton;
    private boolean imageUploaded = false;

    /**
     * Constructs a new ImageUploadUI object.
     * Sets the title, size, minimum size, default close operation, and layout
     * of the window. Also initializes the UI components.
     */
    public ImageUploadUI() {
        super("Upload Image");
    }

    /**
     * Initializes the user interface for the image upload functionality.
     * This method creates and configures the necessary UI components, such as
     * panels, labels, text areas, and buttons.
     * It sets up the layout and adds the components to the frame.
     */
    protected JComponent createMainContentPanel() {
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Image preview
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePreviewLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));

        // Set an initial empty icon to the imagePreviewLabel
        ImageIcon emptyImageIcon = new ImageIcon();
        imagePreviewLabel.setIcon(emptyImageIcon);

        contentPanel.add(imagePreviewLabel);

        // Bio text area
        bioTextArea = new JTextArea("Enter a caption");
        bioTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        JScrollPane bioScrollPane = new JScrollPane(bioTextArea);
        bioScrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT / 6));
        contentPanel.add(bioScrollPane);

        // Upload button
        uploadButton = new JButton("Upload Image");
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.addActionListener(this::uploadAction);
        contentPanel.add(uploadButton);

        // Save button (for bio)
        saveButton = new JButton("Save Caption");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(this::saveBioAction);
        
        return contentPanel;
    }

    /**
     * Handles the upload action when the user selects an image file.
     * Displays a file chooser dialog for the user to select an image file.
     * If a file is selected, it saves the image to a specified destination,
     * updates the image preview, and updates the UI accordingly.
     *
     * @param event the action event triggered by the upload button
     */
    private void uploadAction(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String username = readUsername(); // Read username from users.txt
                int imageId = getNextImageId(username);
                String fileExtension = getFileExtension(selectedFile);
                String newFileName = username + "_" + imageId + "." + fileExtension;

                Path destPath = Paths.get("img", "uploaded", newFileName);
                Files.copy(selectedFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);

                // Save the bio and image ID to a text file
                saveImageInfo(username + "_" + imageId, username, bioTextArea.getText());

                // Load the image from the saved path
                ImageIcon imageIcon = new ImageIcon(destPath.toString());

                // Check if imagePreviewLabel has a valid size
                if (imagePreviewLabel.getWidth() > 0 && imagePreviewLabel.getHeight() > 0) {
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
                }

                imagePreviewLabel.setIcon(imageIcon);

                // Update the flag to indicate that an image has been uploaded
                imageUploaded = true;

                // Change the text of the upload button
                uploadButton.setText("Upload Another Image");

                JOptionPane.showMessageDialog(this, "Image uploaded and preview updated!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /*
     * For uploadAction method:
     * Method is too long and does too many stuff.
     * We can break it down into smaller methods.
     *  - slectFile method for selecting a file.
     *  - processImage method for processing the image.
     *  - updateUI method for updating the UI.
     * 
     */

    /**
     * Retrieves the next available image ID for a given username.
     * The image ID is determined by searching for existing image files in the
     * storage directory
     * and finding the highest numeric ID associated with the given username.
     * 
     * @param username the username for which to retrieve the next image ID
     * @return the next available image ID
     * @throws IOException if an I/O error occurs while accessing the storage
     *                     directory
     */
    private int getNextImageId(String username) throws IOException {
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
     * @throws IOException if an I/O error occurs while creating or writing to the
     *                     file
     */
    private void saveImageInfo(String imageId, String username, String bio) throws IOException {
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
     * @param file the file for which to retrieve the extension
     * @return the file extension, or an empty string if the file has no extension
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }

    /**
     * Saves the bio text entered by the user.
     * 
     * @param event the action event that triggered the save action
     */
    private void saveBioAction(ActionEvent event) {
        // Here you would handle saving the bio text
        String bioText = bioTextArea.getText();
        // For example, save the bio text to a file or database
        JOptionPane.showMessageDialog(this, "Caption saved: " + bioText);
    }

    /**
     * Reads the username from the users.txt file.
     *
     * @return The username extracted from the first line of the file, or null if no
     *         username is found.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private String readUsername() throws IOException {
        Path usersFilePath = Paths.get("data", "users.txt");
        try (BufferedReader reader = Files.newBufferedReader(usersFilePath)) {
            String line = reader.readLine();
            if (line != null) {
                return line.split(":")[0]; // Extract the username from the first line
            }
        }
        return null; // Return null if no username is found
    }

    @Override
    protected List<String> disabledIcons() {
        return List.of("add");
    }
}

/*
 * The ImageUploadUI class is doing too much. Its handling:
 * - UI creation
 * - File handling
 * - Image processing
 * 
 * We should break it down into smaller classes each with a single
 * responsibility.
 * - ImageProcessor class for handling image-related operations.
 * - FileHandler class for handling file-related operations (I/O operations).
 * - ImageUploadUI for UI-related tasks.
 *
 */

/*
 * Duplicate code in the createHeaderPanel and createNavigationPanel and
 * createIconButton methods
 * (with the ExpoloreUI class).
 * We can create a separate class for creating the header and navigation panels.
 */

 /*
  * Duplicate code: readUsername and openProfileUI methods.
  * Same code to read username from users.txt file.
  * Refractoring into a method and call that method wherever needed.
  */