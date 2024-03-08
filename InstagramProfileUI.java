import javax.swing.*;
import java.util.List;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.nio.file.*;
import java.util.stream.Stream;

/*
 * The InstagramProfileUI class is handling UI craetion, file reading, and business logic.
 */

/**
 * Represents the user interface for the Instagram profile.
 */
public class InstagramProfileUI extends AbstractUI {
    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images
    private UserService userService = new UserService();
    private InstagramUIComponents uiComponents = new InstagramUIComponents(WIDTH, userService);
    private JPanel contentPanel; // Panel to display the image grid or the clicked image
    private User currentUser; // User object to store the current user's information

    public InstagramProfileUI(User user) {
        this();
        userHandling(user);
        overwriteMainContentPanel();
    }

    public InstagramProfileUI() {
        super("DACS Profile");
    }

    private void userHandling(User user) {
        this.currentUser = userService.getUserDetails(user);
    }

    @Override
    protected JComponent createMainContentPanel() {
        this.contentPanel = new JPanel();
        return contentPanel;
    }

    private void overwriteMainContentPanel() {
        contentPanel.removeAll(); // Clear existing content
        contentPanel.setLayout(new GridLayout(0, 3, 5, 5)); // Grid layout for image grid

        Path imageDir = Paths.get("img", "uploaded");
        try (Stream<Path> paths = Files.list(imageDir)) {
            paths.filter(path -> path.getFileName().toString().startsWith(currentUser.getUsername() + "_"))
                    .forEach(path -> {
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(path.toString()).getImage()
                                .getScaledInstance(GRID_IMAGE_SIZE, GRID_IMAGE_SIZE, Image.SCALE_SMOOTH));
                        contentPanel.add(createImageLabel(imageIcon));
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle exception (e.g., show a message or log)
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().removeAll();
        setLayout(new BorderLayout());

        add(uiComponents.createHeaderPanel(currentUser), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

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

    private void displayImage(ImageIcon imageIcon) {
        contentPanel.removeAll(); // Remove existing content
        contentPanel.setLayout(new BorderLayout()); // Change layout for image display

        JLabel fullSizeImageLabel = new JLabel(imageIcon);
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(fullSizeImageLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            openProfileUI();
        });
        contentPanel.add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    @Override
    protected List<String> disabledIcons() {
        return List.of("profile");
    }
}