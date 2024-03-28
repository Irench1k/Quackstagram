package quackstagram.views.postlogin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import quackstagram.utilities.FileHandler;
import quackstagram.controllers.postlogin.InstagramProfileController;
import quackstagram.models.Picture;
import quackstagram.models.User;
import quackstagram.views.ColorID;
import quackstagram.views.postlogin.components.InstagramUIComponents;

/**
 * The {@code InstagramProfileUI} class extends {@code AbstractPostLogin} to present a profile interface in Quackstagram.
 * It showcases a user's profile header, including user details and a grid of uploaded images. Users can click on an image
 * to view it in full size, with an option to navigate back to the grid view.
 */
public class InstagramProfileUI extends AbstractPostLogin {
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images
    private InstagramUIComponents uiComponents;
    private JPanel contentPanel; // Panel to display the image grid or the clicked image
    private User targetUser;
    private InstagramProfileController controller;

    /**
     * Constructs an InstagramProfileUI for the given user viewing another user's profile.
     *
     * @param currentUser The currently logged-in user.
     * @param targetUser The user whose profile is being viewed.
     */
    public InstagramProfileUI(User currentUser, User targetUser) {
        super("DACS Profile", currentUser);
        this.targetUser = targetUser;
        this.controller = new InstagramProfileController(this, currentUser, targetUser);
        this.uiComponents = new InstagramUIComponents(currentUser, targetUser, controller);
        overwriteMainContentPanel();
    }

    /**
     * Secondary constructor for a user viewing their own profile.
     *
     * @param user The user whose profile is to be displayed.
     */
    public InstagramProfileUI(User user) {
        this(user, user);
    }

    @Override
    protected JComponent createMainContentPanel() {
        this.contentPanel = new JPanel();
        return contentPanel;
    }

    /**
     * Initializes and populates the main content panel with a grid of images uploaded by the user.
     * Adds functionality to view an image in full size upon clicking.
     */
    private void overwriteMainContentPanel() {
        contentPanel.removeAll(); // Clear existing content
        contentPanel.setLayout(new GridLayout(0, 3, 5, 5)); // Grid layout for image grid

        for (Picture picture : FileHandler.getUserPictures(targetUser.getUsername())) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(picture.getPath()).getImage()
                    .getScaledInstance(GRID_IMAGE_SIZE, GRID_IMAGE_SIZE, Image.SCALE_SMOOTH));
            contentPanel.add(createImageLabel(imageIcon));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().removeAll();
        setLayout(new BorderLayout());

        add(uiComponents.createHeaderPanel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        contentPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));
        contentPanel.setBorder(null);

        revalidate();
        repaint();
    }

    /**
     * Creates and returns a JLabel containing an image icon, with added mouse click event to view the image in full size.
     *
     * @param imageIcon The ImageIcon to display.
     * @return A JLabel containing the given ImageIcon.
     */
    private JLabel createImageLabel(ImageIcon imageIcon) {
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayImage(imageIcon); // Call method to display the clicked image
            }
        });
        return imageLabel;
    }

    /**
     * Displays the clicked image in full size, replacing the image grid with a single large image view and a back button.
     *
     * @param imageIcon The ImageIcon of the clicked image to display.
     */
    private void displayImage(ImageIcon imageIcon) {
        contentPanel.removeAll(); // Remove existing content
        contentPanel.setLayout(new BorderLayout()); // Change layout for image display

        JLabel fullSizeImageLabel = new JLabel(imageIcon);
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(fullSizeImageLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            controller.showProfileUI();
        });
        contentPanel.add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /**
     * Provides a list of navigation icons to be disabled for the profile UI, specifically disabling the profile icon.
     *
     * @return A List of icon types to be disabled.
     */
    @Override
    protected List<String> disabledIcons() {
        return List.of("profile");
    }
}