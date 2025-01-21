package quackstagram.models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a user on the Quackstagram social media platform. A User object
 * encapsulates information about a user's identity, their social graph, and
 * their activity on the platform, including the number of posts made, followers
 * gained, and the accounts they are following.
 */
public class User extends AbstractModel<User> {
    private String username;
    private String password;
    private String bio;
    private String passCode;
    private ArrayList<String> followingUsers; // other users that this one follows
    private int followersCount;
    private int postsCount;

    /**
     * Constructs a new User with the specified details. Initializes the posts count,
     * followers count, and following list based on the provided parameters.
     *
     * @param username       the unique identifier for the user
     * @param password       the user's password for authentication
     * @param bio            a brief biography or description about the user
     * @param passCode       an optional passcode for additional security, can be null if not used
     * @param followingUsers a list of usernames that this user follows
     * @param followersCount the number of followers this user has
     * @param postsCount     the number of posts this user has made
     */
    public User(String username, String password, String bio,String passCode, ArrayList<String> followingUsers,
                int followersCount, int postsCount) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.passCode = passCode;
        this.followingUsers = followingUsers;
        this.followersCount = followersCount;
        this.postsCount = postsCount;
    }

    /**
     * Polymorphic variation of the User constructor in the event that the user doesn't have/want a passcode
     *
     * @param username
     * @param password
     * @param bio
     * @param followingUsers
     * @param followersCount
     * @param postsCount
     */
    public User(String username, String password, String bio, ArrayList<String> followingUsers,
                int followersCount, int postsCount) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.passCode = "0";
        this.followingUsers = followingUsers;
        this.followersCount = followersCount;
        this.postsCount = postsCount;
    }

    /**
     * Creates an instance of a User from an array of string arguments. This method
     * is typically used to reconstruct a User object from data stored in a persistent
     * storage format, such as a database or a flat file.
     *
     * @param args An array of strings representing the user's attributes. The expected
     *             order is username, password, bio, passCode, space-separated list of
     *             following users, followers count, and posts count. The method expects
     *             exactly 7 elements in the array.
     * @return A newly constructed User object with the provided attributes.
     * @throws RuntimeException If the input array does not contain exactly 7 elements,
     *                          indicating a mismatch between expected and actual data format,
     *                          this exception is thrown to signal the error.
     */
    public static User createInstance(String[] args) throws RuntimeException {
        if (args.length != 7) {
            System.out.println(String.join(", ", args));
            throw new RuntimeException("Couldn't parse users line, expected 7 arguments!");
        }
        String username = args[0];
        String password = args[1];
        String bio = args[2];
        String passCode = args[3];
        ArrayList<String> followingUsers = new ArrayList<>(Arrays.asList(args[4].split(" ")));
        int followersCount = Integer.parseInt(args[5]);
        int postsCount = Integer.parseInt(args[6]);

        return new User(username, password, bio, passCode, followingUsers, followersCount, postsCount);
    }

    @Override
    public String[] serialize() {
        return new String[] {
            username,
            password,
            bio,
            String.valueOf(passCode),
            String.join(" ", followingUsers),
            Integer.toString(followersCount),
            Integer.toString(postsCount)
        };
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    @Override
    public boolean isIdEqualTo(User user) {
        return this.username.equals(user.getUsername());
    }

    // Getter methods for user details

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the bio of the user.
     *
     * @return the bio of the user
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the bio of the user.
     *
     * @param bio the new bio of the user
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Returns the number of posts by the user.
     *
     * @return the post count of the user
     */
    public int getPostsCount() {
        return postsCount;
    }

    /**
     * Returns the number of followers of the user.
     *
     * @return the followers count of the user
     */
    public int getFollowersCount() {
        return followersCount;
    }

    /**
     * Returns the number of users this user is following.
     *
     * @return the following count of the user
     */
    public int getFollowingCount() {
        return this.followingUsers.size();
    }

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param suppliedPassword the password to check
     * @return true if the passwords match, false otherwise
     */
    public boolean isPasswordEqual(String suppliedPassword) {
        return this.password.equals(suppliedPassword);
    }

    /**
     * Checks if the provided passCode matches the user's passCode.
     *
     * @param suppliedPassCode the passCode to check
     * @return true if the passCodes match, false otherwise
     */
    public boolean isPassCodeEqual(String suppliedPassCode) {
        return this.passCode.equals(suppliedPassCode);
    }

    // Setter methods for followers and following counts

    /**
     * Sets the number of followers of the user.
     *
     * @param followersCount the new followers count
     */
    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * Sets the post count for the user.
     *
     * @param postsCount the new post count
     */
    public void setPostCount(int postCount) {
        this.postsCount = postCount;
    }

    public boolean followsUser(User targetUser) {
        for (String followedUser : this.followingUsers) {
            if (followedUser.equals(targetUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a user to this user's list of followed users, if they're not already being followed.
     *
     * @param targetUser the user to follow
     */
    public void addUserToFollow(User targetUser) {
        if (isIdEqualTo(targetUser)) {
            // can't follow ourselves
            return;
        }

        if (followsUser(targetUser)) {
            // Already following
            return;
        }

        followingUsers.add(targetUser.getUsername());
    }

    public String getProfileImagePath() {
        return "img/profile/" + this.username + ".png";
    }

    public ArrayList<String> getFollowingUsers() {
        return new ArrayList<String>(followingUsers);
    }
}