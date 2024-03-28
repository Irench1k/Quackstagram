package quackstagram.views.postlogin;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import quackstagram.controllers.postlogin.ImageUploadController;
import quackstagram.models.User;
import quackstagram.views.ColorID;

/**
 * The {@code ImageUploadUI} class extends {@code AbstractPostLogin} to provide a user interface for uploading images
 * to Quackstagram. Users can select an image from their file system, preview it, enter a caption, and then upload it
 * to their profile.
 */
public class ImageUploadUI extends AbstractPostLogin {
    private JLabel imagePreviewLabel;
    private JTextArea captionArea;
    private JButton uploadButton;
    private ImageUploadController controller;

    /**
     * Constructs an ImageUploadUI with the specified current user.
     *
     * @param currentUser The current user who is uploading an image.
     */
    public ImageUploadUI(User currentUser) {
        super("Upload Image", currentUser);
        controller = new ImageUploadController(this, currentUser);
    }

    /**
     * Initializes the main content panel with components for image upload, including
     * a preview label, caption text area, and an upload button.
     *
     * @return A JComponent representing the main content panel of the image upload UI.
     */
    protected JComponent createMainContentPanel() {
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        createImageIcon(contentPanel);
        createCaptionTextAndPane(contentPanel);
        createUploadButton(contentPanel);
        contentPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));

        return contentPanel;
    }

    /**
     * Creates and configures the upload button.
     *
     * @param contentPanel The panel to which the upload button is added.
     */
    private void createUploadButton(JPanel contentPanel) {
        String upload = "Upload Image";
        uploadButton = new JButton(upload);
        uploadButton.setForeground(getColor(ColorID.TEXT_PRIMARY));
        uploadButton.setBackground(getColor(ColorID.LIKE_BUTTON));
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.addActionListener((e) -> controller.uploadAction(captionArea.getText()));
        contentPanel.add(uploadButton);
    }

    /**
     * Creates and configures the caption text area and its scroll pane.
     *
     * @param contentPanel The panel to which the caption components are added.
     */
    private void createCaptionTextAndPane(JPanel contentPanel) {
        captionArea = new JTextArea("Enter a caption");
        captionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        captionArea.setLineWrap(true);
        captionArea.setWrapStyleWord(true);
        captionArea.setForeground(getColor(ColorID.TEXT_PRIMARY));
        captionArea.setBackground(getColor(ColorID.ENTER_COMPONENT));


        JScrollPane bioScrollPane = new JScrollPane(captionArea);
        bioScrollPane.setBackground(getColor(ColorID.ENTER_COMPONENT));
        bioScrollPane.setBorder(null);
        bioScrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT / 6));
        contentPanel.add(bioScrollPane);
    }


    /**
     * Creates and configures the image preview label.
     *
     * @param contentPanel The panel to which the image preview label is added.
     */
    private void createImageIcon(JPanel contentPanel) {
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePreviewLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));

        // Set an initial empty icon to the imagePreviewLabel
        ImageIcon emptyImageIcon = new ImageIcon();
        imagePreviewLabel.setIcon(emptyImageIcon);

        contentPanel.add(imagePreviewLabel);
    }

    /**
     * Returns a list of icon types that should be disabled in the navigation bar for this UI.
     *
     * @return A List of strings representing the icon types to be disabled.
     */
    @Override
    protected List<String> disabledIcons() {
        return List.of("add");
    }
}
