package quackstagram.user;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import quackstagram.*;
import quackstagram.images.*;
import quackstagram.user.*;
import quackstagram.view.*;


/**
 * Manages user relationships for a social media platform, handling operations such as
 * following a user and retrieving lists of followers and followed users.
 * User relationships are persisted in a file.
 *
 * @author MM
 * @version 1.0, 2024-03-02
 */
public class UserRelationshipManager {

    private final String followersFilePath = "data/followers.txt";

    /**
     * Follows a user by adding the relationship to a persistent file unless
     * the follower is already following the user.
     *
     * @param follower the username of the follower
     * @param followed the username of the user to be followed
     * @throws IOException if an I/O error occurs writing to the file
     */
    public void followUser(String follower, String followed) throws IOException {
        if (!isAlreadyFollowing(follower, followed)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(followersFilePath, true))) {
                writer.write(follower + ":" + followed);
                writer.newLine();
            }
        }
    }

    /**
     * Checks if a user is already following another user by searching
     * the persistent file for the relationship.
     *
     * @param follower the username of the follower
     * @param followed the username of the user to be followed
     * @return true if the follower is already following the user, false otherwise
     * @throws IOException if an I/O error occurs reading from the file
     */
    private boolean isAlreadyFollowing(String follower, String followed) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(followersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(follower + ":" + followed)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the list of followers for a given user by reading the
     * persistent file and collecting all users who follow the specified user.
     *
     * @param username the username of the user whose followers are to be retrieved
     * @return a list of usernames who follow the specified user
     * @throws IOException if an I/O error occurs reading from the file
     */
    public List<String> getFollowers(String username) throws IOException {
        List<String> followers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(followersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[1].equals(username)) {
                    followers.add(parts[0]);
                }
            }
        }
        return followers;
    }

    /**
     * Retrieves the list of users a given user is following by reading
     * the persistent file and collecting all users the specified user follows.
     *
     * @param username the username of the user whose followings are to be retrieved
     * @return a list of usernames the specified user is following
     * @throws IOException if an I/O error occurs reading from the file
     */
    public List<String> getFollowing(String username) throws IOException {
        List<String> following = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(followersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    following.add(parts[1]);
                }
            }
        }
        return following;
    }
}
