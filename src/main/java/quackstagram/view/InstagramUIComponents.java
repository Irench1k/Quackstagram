package quackstagram.view;
import javax.swing.*;
import java.util.List;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.nio.file.*;
import java.util.stream.Stream;

import quackstagram.*;
import quackstagram.images.*;
import quackstagram.user.*;
import quackstagram.view.*;

public class InstagramUIComponents {
    private UserService userService;
    private static int WIDTH;
    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images

    public InstagramUIComponents(int width, UserService userService) {
        this.WIDTH = width;
        this.userService = userService;
    }

    public JPanel createHeaderPanel(User currentUser) {
        InstagramReader reader = new InstagramReader();
        String loggedInUsername = reader.readLoggedInUserName();
        boolean isCurrentUser = loggedInUsername.equals(currentUser.getUsername());

        // Header Panel
        JPanel headerPanel = new JPanel();

        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.GRAY);

        // Top Part of the Header (Profile Image, Stats, Follow Button)
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        // Profile image
        ImageIcon profileIcon = new ImageIcon(new ImageIcon("img/storage/profile/" + currentUser.getUsername() + ".png")
                .getImage().getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH));
        JLabel profileImage = new JLabel(profileIcon);
        profileImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topHeaderPanel.add(profileImage, BorderLayout.WEST);

        // Stats Panel
        JPanel statsPanel = createStatePanel(currentUser);

        // Follow Button
        JButton followButton = createFollowButton(reader, currentUser, loggedInUsername, isCurrentUser);

        // Add Stats and Follow Button to a combined Panel
        JPanel statsFollowPanel = createStatsFollowPanel(currentUser, statsPanel, followButton);
        topHeaderPanel.add(statsFollowPanel, BorderLayout.CENTER);

        headerPanel.add(topHeaderPanel);

        // Profile Name and Bio Panel
        JPanel profileNameAndBioPanel = createProfileNameAndBioPanel();

        // Profile Name Label
        JLabel profileNameLabel = createProfileNameLabel(currentUser);

        // Profile Bio
        JTextArea profileBio = createProfileBio(currentUser);

        profileNameAndBioPanel.add(profileNameLabel, BorderLayout.NORTH);
        profileNameAndBioPanel.add(profileBio, BorderLayout.CENTER);

        headerPanel.add(profileNameAndBioPanel);

        return headerPanel;
    }

    private JLabel createProfileNameLabel(User currentUser) {
        JLabel profileNameLabel = new JLabel(currentUser.getUsername());
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profileNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Padding on the sides
        return profileNameLabel;
    }

    private JPanel createProfileNameAndBioPanel() {
        JPanel profileNameAndBioPanel = new JPanel();
        profileNameAndBioPanel.setLayout(new BorderLayout());
        profileNameAndBioPanel.setBackground(new Color(249, 249, 249));
        return profileNameAndBioPanel;
    }

    private JTextArea createProfileBio(User currentUser) {
        JTextArea profileBio = new JTextArea(currentUser.getBio());
        System.out.println("This is the bio " + currentUser.getUsername());
        profileBio.setEditable(false);
        profileBio.setFont(new Font("Arial", Font.PLAIN, 12));
        profileBio.setBackground(new Color(249, 249, 249));
        profileBio.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding on the sides
        return profileBio;
    }

    private JPanel createStatsFollowPanel(User currentUser, JPanel statsPanel, JButton followButton) {
        JPanel statsFollowPanel = new JPanel();
        statsFollowPanel.setLayout(new BoxLayout(statsFollowPanel, BoxLayout.Y_AXIS));
        statsFollowPanel.add(statsPanel);
        statsFollowPanel.add(followButton);
        return statsFollowPanel;
    }

    private JButton createFollowButton(InstagramReader reader, User currentUser, String loggedInUsername, boolean isCurrentUser) {
        JButton followButton;
        if (isCurrentUser) {
            followButton = new JButton("Edit Profile");
        } else {
            // Check if the current user is already being followed by the logged-in user
            Boolean alreadyFollowing = reader.readAlreadyFollowing(loggedInUsername, currentUser);
            if (alreadyFollowing) {
                followButton = new JButton("Following");
            }
            else {
                followButton = new JButton("Follow");
                followButton.addActionListener(e -> {
                    handleFollowAction(currentUser.getUsername());
                    followButton.setText("Following");
                });
            }
        }
        followButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        followButton.setFont(new Font("Arial", Font.BOLD, 12));
        followButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, followButton.getMinimumSize().height)); // Make the
                                                                                                             // button
                                                                                                             // fill the
                                                                                                             // horizontal
                                                                                                             // space
        followButton.setBackground(new Color(225, 228, 232)); // A soft, appealing color that complements the UI
        followButton.setForeground(Color.BLACK);
        followButton.setOpaque(true);
        followButton.setBorderPainted(false);
        followButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return followButton;
    }

    private JPanel createStatePanel(User currentUser) {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        statsPanel.setBackground(new Color(249, 249, 249));
        System.out.println("Number of posts for this user" + currentUser.getPostsCount());
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getPostsCount()), "Posts"));
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getFollowersCount()), "Followers"));
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getFollowingCount()), "Following"));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Add some vertical padding
        return statsPanel;
    }

    private JLabel createStatLabel(String number, String text) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>",
                SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

    private void handleFollowAction(String usernameToFollow) {
        userService.handleFollowAction(usernameToFollow);
    }
}