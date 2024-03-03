import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * The ExploreUI class represents the user interface for the Explore page in the
 * Quackstagram application.
 * It extends the JFrame class and provides methods to initialize and create the
 * main content panels.
 * The ExploreUI class displays a header panel, navigation panel, and a main
 * content panel with a search bar and image grid.
 * It also provides functionality to display image details when an image is
 * clicked.
 */
public class ExploreUI extends AbstractUI {
    private static final int IMAGE_SIZE = WIDTH / 3; // Size for each image in the grid

    /**
     * Represents the Explore user interface.
     */
    public ExploreUI() {
        super("Explore");
    }
    /*
     * For initializeUI method:
     * I would reduce the code by just doing for example:
     * add(createHeaderPanel(), BorderLayout.NORTH);
     * add(createMainContentPanel(), BorderLayout.CENTER);
     * add(createNavigationPanel(), BorderLayout.SOUTH);
     * 
     */

    /**
     * Represents a container that is used to organize and display components in a
     * graphical user interface (GUI).
     * It is a lightweight container that can be used to group other components
     * together.
     * 
     * @return the JPanel for the main content section
     * 
     */

    @Override
    protected JComponent createMainContentPanel() {
        // Create the main content panel with search and image grid
        // Search bar at the top
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField(" Search Users");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchField.getPreferredSize().height)); // Limit
                                                                                                             // the
                                                                                                             // height

        // Image Grid
        JPanel imageGridPanel = new JPanel(new GridLayout(0, 3, 2, 2)); // 3 columns, auto rows

        loadImages(imageGridPanel);

        JScrollPane scrollPane = new JScrollPane(imageGridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Main content panel that holds both the search bar and the image grid
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.add(searchPanel);
        mainContentPanel.add(scrollPane); // This will stretch to take up remaining space
        return mainContentPanel;
    }

    private void loadImages(JPanel imageGridPanel) {
        // Load images from the uploaded folder
        File imageDir = new File("img/uploaded");
        File[] imageFiles = imageDir.listFiles((dir, name) -> name.matches(".*\\.(png|jpg|jpeg)"));
        for (File imageFile : imageFiles) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageFile.getPath()).getImage()
                    .getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    displayImage(imageFile.getPath()); // Call method to display the clicked image
                }
            });
            imageGridPanel.add(imageLabel);
        }
    }
    /*
     * For loadImages method:
     * This method is doing too many things. It should be refactored.
     * Create a void loadImages(Jpanel imageGridPanel) method that takes a JPanel as
     * a parameter.
     * Then call this method inside the createMainContentPanel method.
     */


    /**
     * Displays an image with its details on the ExploreUI frame.
     * 
     * @param imagePath the path of the image file to be displayed
     */
    private void displayImage(String imagePath) {
        // Extract image ID from the imagePath
        String imageId = new File(imagePath).getName().split("\\.")[0];

        // Read image details
        String username = "";
        String bio = "";
        String timestampString = "";
        int likes = 0;
        Path detailsPath = Paths.get("img", "image_details.txt");
        try (Stream<String> lines = Files.lines(detailsPath)) {
            String details = lines.filter(line -> line.contains("ImageID: " + imageId)).findFirst().orElse("");
            if (!details.isEmpty()) {
                String[] parts = details.split(", ");
                username = parts[1].split(": ")[1];
                bio = parts[2].split(": ")[1];
                System.out.println(bio + "this is where you get an error " + parts[3]);
                timestampString = parts[3].split(": ")[1];
                likes = Integer.parseInt(parts[4].split(": ")[1]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle exception
        }

        // Calculate time since posting
        String timeSincePosting = "Unknown";
        if (!timestampString.isEmpty()) {
            LocalDateTime timestamp = LocalDateTime.parse(timestampString,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime now = LocalDateTime.now();
            long days = ChronoUnit.DAYS.between(timestamp, now);
            timeSincePosting = days + " day" + (days != 1 ? "s" : "") + " ago";
        }

        // Top panel for username and time since posting
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton usernameLabel = new JButton(username);
        JLabel timeLabel = new JLabel(timeSincePosting);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        topPanel.add(usernameLabel, BorderLayout.WEST);
        topPanel.add(timeLabel, BorderLayout.EAST);

        // Prepare the image for display
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            ImageIcon imageIcon = new ImageIcon(originalImage);
            imageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            imageLabel.setText("Image not found");
        }

        // Bottom panel for bio and likes
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextArea bioTextArea = new JTextArea(bio);
        bioTextArea.setEditable(false);
        JLabel likesLabel = new JLabel("Likes: " + likes);
        bottomPanel.add(bioTextArea, BorderLayout.CENTER);
        bottomPanel.add(likesLabel, BorderLayout.SOUTH);

        // Panel for the back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");

        // Make the button take up the full width
        backButton.setPreferredSize(new Dimension(WIDTH - 20, backButton.getPreferredSize().height));

        backButtonPanel.add(backButton);

        backButton.addActionListener(e -> {
            exploreUI();
        });
        final String finalUsername = username;

        usernameLabel.addActionListener(e -> {
            User user = new User(finalUsername); // Assuming User class has a constructor that takes a username
            InstagramProfileUI profileUI = new InstagramProfileUI(user);
            profileUI.setVisible(true);
            dispose(); // Close the current frame
        });

        // Container panel for image and details
        JPanel containerPanel = new JPanel(new BorderLayout());

        containerPanel.add(topPanel, BorderLayout.NORTH);
        containerPanel.add(imageLabel, BorderLayout.CENTER);
        containerPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Add the container panel and back button panel to the frame
        add(backButtonPanel, BorderLayout.NORTH);
        add(containerPanel, BorderLayout.CENTER);
        add(createNavigationPanel(), BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
    /*
     * For displayImage method:
     * This method is too long and does too many things. It should be refactored.
     * We should break it down into smaller, more focused methods to improve
     * readibility and maintainability.
     * 
     * Seperate the file I/O operations from UI class by creating a separate class
     * or method.
     */
}

/*
 * Methods to consider for refactoring because they are similar to the ones in
 * the InstagramProfileUI class:
 * These methods can be moved to a separate class and then called from the
 * ExploreUI class.
 * This includes the createHeaderPanel, createNavigationPanel, createIconButton.
 */

/*
 * Magic numbers:
 * WIDTH - 20 should be set to a constant variable
 * makes the code more readable and maintainable
 */