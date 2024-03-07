import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represents the home screen of the Quakstagram application,
 * displaying a feed of image posts that users can interact with by liking
 * or viewing in more detail. The UI supports navigation to other parts
 * of the application like search, profile, and notifications.
 *
 * @author MM
 * @version 1.0, 2024-03-02
 */

/** Code Smell: LARGE CLASS
 * @author MM
 *
 *  Code Smell Description: The QuakstagramHomeUI class is responsible for UI initialization, event handling, data
 *  manipulation, and navigation. This violates the Single Responsibility Principle, suggesting the class could be
 *  refactored into smaller, more focused classes.
 *
 *  Applies to: full QuakstagramHomeUI class
 *
 *  Suggested Fix: TODO
 *
 *  Fixed? TODO
 *
 */

/** Code Smell: DUPLICATE CODE
 * @author MM
 *
 *  Code Smell Description: The methods for opening different UIs (`openProfileUI`, `notificationsUI`, `ImageUploadUI`,
 *  `openHomeUI`, `exploreUI`) follow a very similar pattern of disposing the current frame and opening a new one.
 *  This duplication could be reduced by refactoring these operations into a single method with parameters to specify
 *  the UI to open.
 *
 *  Applies to: all methods cited above - these are also labelled as code smells referring back to her under their
 *  method header
 *
 *  Suggested Fix: TODO
 *
 *  Fixed? TODO
 *
 */

/**
 * Code Smell: INAPPROPRIATE INTIMACY
 * 
 * @author MM
 *
 *         Code Smell Description: The class directly manipulates low-level
 *         details of file reading and writing, image
 *         processing, and UI management. This suggests a violation of
 *         encapsulation where the class is overly familiar with
 *         the details of other responsibilities. It would be beneficial to
 *         separate concerns more cleanly, perhaps by using
 *         a data access layer or service layer.
 *
 *         Applies to: full QuakstagramHomeUI class
 *
 *         Suggested Fix: TODO
 *
 *         Fixed? TODO
 *
 */
public class QuakstagramHomeUI extends AbstractUI {

    /**
     * Code Smell: MAGIC NUMBERS
     * 
     * @author MM
     *
     *         Code Smell Description: TThe class contains several "magic numbers,"
     *         such as dimensions and colors, directly in
     *         the code (`WIDTH`, `HEIGHT`, `NAV_ICON_SIZE`, etc.). These should be
     *         declared as named constants or externalized
     *         into configuration files to improve readability and make the code
     *         easier to maintain.
     *
     *         Applies to: QuakstagramHomeUI class constant declaration at the
     *         beginning
     *
     *         Suggested Fix: TODO
     *
     *         Fixed? TODO
     *
     */
    private static final int IMAGE_WIDTH = WIDTH - 100; // Width for the image posts
    private static final int IMAGE_HEIGHT = 150; // Height for the image posts
    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95); // Color for the like button
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel homePanel;
    private JPanel imageViewPanel;
    private ImageLikesManager imageLikes;

    /**
     * Initializes and sets up the Quakstagram home UI including layout, panels,
     * and navigation.
     */
    public QuakstagramHomeUI() {
        super("Quakstagram Home");
    }

    @Override
    protected JComponent createMainContentPanel() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        homePanel = new JPanel(new BorderLayout());
        imageViewPanel = new JPanel(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertical box layout
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Never allow
                                                                                                 // horizontal scrolling
        String[][] sampleData = createSampleData();
        populateContentPanel(contentPanel, sampleData);
        add(scrollPane, BorderLayout.CENTER);

        // Set up the home panel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        homePanel.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(homePanel, "Home");
        cardPanel.add(imageViewPanel, "ImageView");
        cardLayout.show(cardPanel, "Home");

        return cardPanel;
    }

    @Override
    protected String getHeaderText() {
        return "🐥 Quakstagram 🐥";
    }

    /**
     * Populates the content panel with sample post data including images,
     * descriptions,
     * and like buttons.
     *
     * @param panel      The JPanel to populate with post data.
     * @param sampleData An array of sample post data to display.
     */

    /**
     * Code Smell: LONG METHOD
     * 
     * @author MM
     *
     *         Code Smell Description: too long and performs multiple tasks. This
     *         methods could be refactored to improve
     *         readability and maintainability by breaking them down into smaller
     *         methods with single responsibilities.
     *
     *         Applies to: populateContentPanel method
     *
     *         Suggested Fix: TODO
     *
     *         Fixed? TODO
     *
     */

    /**
     * Code Smell: DATA CLUMPS
     * 
     * @author MM
     *
     *         Code Smell Description: The use of arrays (`String[][] sampleData`)
     *         to represent complex data suggests the
     *         potential for introducing a new class to model this data more
     *         appropriately. This would improve type safety and
     *         clarity, making the code easier to understand and work with.
     *
     *         Applies to: String[][] sampleData
     *         note: also present in the initializeUI() method
     *
     *         Suggested Fix: TODO
     *
     *         Fixed? TODO
     *
     */
    private void populateContentPanel(JPanel panel, String[][] sampleData) {

        for (String[] postData : sampleData) {
            JPanel itemPanel = getItemPanel();

            JLabel nameLabel = getNamLabel(postData[0]);
            JLabel imageLabel = getImageLabel(postData[3]);
            JLabel descriptionLabel = getDiscriptionLabel(postData[1]);
            JLabel likesLabel = getLikesLabel(postData[2]);
            JButton likeButton = getLikeButton(postData[3], postData[2]);
            JPanel spacingPanel = getSpacingPanel();

            itemPanel.add(nameLabel);
            itemPanel.add(imageLabel);
            itemPanel.add(descriptionLabel);
            itemPanel.add(likesLabel);
            itemPanel.add(likeButton);

            panel.add(itemPanel);
            panel.add(spacingPanel);

            // Make the image clickable
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    displayImage(postData); // Call a method to switch to the image view
                }
            });
        }
    }

    private JPanel getItemPanel() {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(Color.WHITE); // Set the background color for the item panel
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        itemPanel.setAlignmentX(CENTER_ALIGNMENT);
        return itemPanel;
    }

    private JLabel getNamLabel(String postData) {
        JLabel nameLabel = new JLabel(postData);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return nameLabel;
    }

    private JLabel getDiscriptionLabel(String postData) {
        JLabel descriptionLabel = new JLabel(postData);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return descriptionLabel;
    }

    private JLabel getLikesLabel(String postData) {
        JLabel likesLabel = new JLabel(postData);
        likesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return likesLabel;
    }

    private JPanel getSpacingPanel() {
        // Grey spacing panel
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(WIDTH - 10, 5)); // Set the height for spacing
        spacingPanel.setBackground(new Color(230, 230, 230)); // Grey color for spacing
        return spacingPanel;
    }

    private JLabel getImageLabel(String postData) {
        // Crop the image to the fixed size
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        imageLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border to image label
        try {
            BufferedImage originalImage = ImageIO.read(new File(postData));
            BufferedImage croppedImage = originalImage.getSubimage(0, 0,
                    Math.min(originalImage.getWidth(), IMAGE_WIDTH),
                    Math.min(originalImage.getHeight(), IMAGE_HEIGHT));
            ImageIcon imageIcon = new ImageIcon(croppedImage);
            imageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            // Handle exception: Image file not found or reading error
            imageLabel.setText("Image not found");
        }

        return imageLabel;
    }

    /**
     * Creates sample data for demonstration purposes, simulating a feed of posts.
     *
     * @return A two-dimensional array representing sample post data.
     */
    private String[][] createSampleData() {
        String currentUser = "";
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("data", "users.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                currentUser = line.split(":")[0].trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String followedUsers = "";
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("data", "following.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(currentUser + ":")) {
                    followedUsers = line.split(":")[1].trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Temporary structure to hold the data
        String[][] tempData = new String[100][]; // Assuming a maximum of 100 posts for simplicity
        int count = 0;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("img", "image_details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && count < tempData.length) {
                String[] details = line.split(", ");
                String imagePoster = details[1].split(": ")[1];
                if (followedUsers.contains(imagePoster)) {
                    String imagePath = "img/uploaded/" + details[0].split(": ")[1] + ".png"; // Assuming PNG format
                    String description = details[2].split(": ")[1];
                    String likes = "Likes: " + details[4].split(": ")[1];

                    tempData[count++] = new String[] { imagePoster, description, likes, imagePath };
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Transfer the data to the final array
        String[][] sampleData = new String[count][];
        System.arraycopy(tempData, 0, sampleData, 0, count);

        return sampleData;
    }

    /**
     * Displays a full-size image view for a selected post, including user info,
     * description, and like functionality.
     *
     * @param postData An array containing data for the selected post.
     */
    private void displayImage(String[] postData) {
        imageViewPanel.removeAll(); // Clear previous content

        String imageId = new File(postData[3]).getName().split("\\.")[0];
        JLabel likesLabel = new JLabel(postData[2]); // Update this line

        // Display the image
        JLabel fullSizeImageLabel = new JLabel();
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);

        try {
            BufferedImage originalImage = ImageIO.read(new File(postData[3]));
            BufferedImage croppedImage = originalImage.getSubimage(0, 0, Math.min(originalImage.getWidth(), WIDTH - 20),
                    Math.min(originalImage.getHeight(), HEIGHT - 40));
            ImageIcon imageIcon = new ImageIcon(croppedImage);
            fullSizeImageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            // Handle exception: Image file not found or reading error
            fullSizeImageLabel.setText("Image not found");
        }

        // User Info
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel userName = new JLabel(postData[0]);
        userName.setFont(new Font("Arial", Font.BOLD, 18));
        userPanel.add(userName);// User Name


        JButton likeButton = new JButton();
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setBackground(LIKE_BUTTON_COLOR); // Set the background color for the like button
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false); // Remove border
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //handleLikeAction(imageId, likesLabel);
            ImageLikesManager imageLikes = new ImageLikesManager();
            imageLikes.handleLikeAction(imageId, likesLabel);
            refreshDisplayImage(postData, imageId); // Refresh the view
    }
        });

        // Information panel at the bottom
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel(postData[1])); // Description
        infoPanel.add(new JLabel(postData[2])); // Likes
        infoPanel.add(likeButton);

        imageViewPanel.add(fullSizeImageLabel, BorderLayout.CENTER);
        imageViewPanel.add(infoPanel, BorderLayout.SOUTH);
        imageViewPanel.add(userPanel, BorderLayout.NORTH);

        imageViewPanel.revalidate();
        imageViewPanel.repaint();

        cardLayout.show(cardPanel, "ImageView"); // Switch to the image view
    }

    private JButton getLikeButton(String imageIdData, String likesLabelData) {
        String imageId = new File(imageIdData).getName().split("\\.")[0];
        JLabel likesLabel = getLikesLabel(likesLabelData);
        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setBackground(LIKE_BUTTON_COLOR); // Set the background color for the like button
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false); // Remove border
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //handleLikeAction(imageId, likesLabel);
            ImageLikesManager imageLikes = new ImageLikesManager();
            imageLikes.handleLikeAction(imageId, likesLabel);
        }});

        return likeButton;
    }

    private void refreshDisplayImage(String[] postData, String imageId) {
        // Read updated likes count from image_details.txt
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("img", "image_details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ImageID: " + imageId)) {
                    String likes = line.split(", ")[4].split(": ")[1];
                    postData[2] = "Likes: " + likes;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call displayImage with updated postData
        displayImage(postData);
    }

    
}
