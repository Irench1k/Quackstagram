package quackstagram.controllers.postlogin;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import quackstagram.utilities.FileHandler;
import quackstagram.models.Picture;
import quackstagram.models.User;
import quackstagram.views.postlogin.ImageUploadUI;

/**
 * The {@code ImageUploadController} class manages the process of uploading images by users.
 * It facilitates selecting an image file, attaching a caption, and saving the image
 * to the user's profile within the Quackstagram application.
 */
public class ImageUploadController {
    private ImageUploadUI view;
    private User currentUser;


    /**
     * Constructs an {@code ImageUploadController} with the specified view and the current user.
     *
     * @param view The {@link ImageUploadUI} view that this controller manages.
     * @param currentUser The {@link User} currently logged in and performing the upload action.
     */
    public ImageUploadController(ImageUploadUI view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
    }

    /**
     * Handles the action of uploading an image by opening a file chooser dialog,
     * reading the selected image file, and saving the image with a caption.
     * If the file selection is successful, the image is uploaded and saved;
     * otherwise, the process is aborted.
     *
     * @param caption The caption provided by the user for the image to be uploaded.
     */
    public void uploadAction(String caption) {
        File selectedFile = selectFile();
        if (selectedFile == null) {
            return;
        }

        try {
            Picture picture = Picture.createNewForUser(currentUser.getUsername(), caption);
            FileHandler.uploadImage(selectedFile, picture);
            FileHandler.savePicture(picture);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Error saving image: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens a file chooser dialog allowing the user to select an image file.
     * Filters files to allow only images in png, jpg, or jpeg formats.
     *
     * @return The selected {@link File} object if an image is selected, or {@code null} if the operation is cancelled.
     */
    private File selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
