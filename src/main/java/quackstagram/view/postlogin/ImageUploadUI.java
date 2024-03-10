package quackstagram.view.postlogin;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import quackstagram.FileHandler;
import quackstagram.models.Picture;
import quackstagram.models.User;

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
public class ImageUploadUI extends AbstractPostLogin {
    private JLabel imagePreviewLabel;
    private JTextArea bioTextArea;
    private JButton uploadButton;

    /**
     * Constructs a new ImageUploadUI object.
     * Sets the title, size, minimum size, default close operation, and layout
     * of the window. Also initializes the UI components.
     */
    public ImageUploadUI(User currentUser) {
        super("Upload Image", currentUser);
    }

    private void uploadAction(ActionEvent event) {
        File selectedFile = selectFile();
        if (selectedFile != null) {
            try {
                Picture picture = Picture.createNewForUser(getCurrentUser().getUsername(), bioTextArea.getText());
                FileHandler.uploadImage(selectedFile, picture);
                FileHandler.savePicture(picture);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // I have refractored the uploadAction method into smaller methods and/or
    // classes

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
        createImageIcon(contentPanel);
        createCaptionTextAndPane(contentPanel);
        createUploadButton(contentPanel);

        return contentPanel;
    }

    private void createUploadButton(JPanel contentPanel) {
        uploadButton = new JButton("Upload Image");
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.addActionListener(this::uploadAction);
        contentPanel.add(uploadButton);
    }

    private void createCaptionTextAndPane(JPanel contentPanel) {
        bioTextArea = new JTextArea("Enter a caption");
        bioTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        JScrollPane bioScrollPane = new JScrollPane(bioTextArea);
        bioScrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT / 6));
        contentPanel.add(bioScrollPane);
    }

    private void createImageIcon(JPanel contentPanel) {
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePreviewLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));

        // Set an initial empty icon to the imagePreviewLabel
        ImageIcon emptyImageIcon = new ImageIcon();
        imagePreviewLabel.setIcon(emptyImageIcon);

        contentPanel.add(imagePreviewLabel);
    }

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

    @Override
    protected List<String> disabledIcons() {
        return List.of("add");
    }
}
