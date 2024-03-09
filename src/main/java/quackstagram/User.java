package src.main.java.quackstagram;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a user on Quackstagram, a social media platform.
 * A user has a username, bio, password, a count of posts, followers, and following,
 * as well as a list of pictures posted by the user.
 *
 * @author MM
 * @version 1.0, 2024-03-02
 */
class User {
    private String username;
    private String bio;
    private String password;
    private int postsCount;
    private int followersCount;
    private int followingCount;
    private List<Picture> pictures;

    /**
     * Constructs a new User with the specified username, bio, and password.
     * Initializes the posts count, followers count, and following count to 0.
     * Initializes an empty list of pictures.
     *
     * @param username the username of the new user
     * @param bio the bio of the new user
     * @param password the password for the new user
     */
    public User(String username, String bio, String password) {
        this.username = username;
        this.bio = bio;
        this.password = password;
        this.pictures = new ArrayList<>();
        // Initialize counts to 0
        this.postsCount = 0;
        this.followersCount = 0;
        this.followingCount = 0;
    }

    /**
     * Constructs a new User with the specified username.
     * This constructor is primarily used when only a username is known.
     *
     * @param username the username of the new user
     */
    public User(String username){
        this.username = username;
    }

    /**
     * Adds a picture to the user's profile. Increments the user's post count.
     *
     * @param picture the Picture to be added to the user's profile
     */
    public void addPicture(Picture picture) {
        pictures.add(picture);
        postsCount++;
    }

    // Getter methods for user details

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() { return username; }

    /**
     * Returns the bio of the user.
     *
     * @return the bio of the user
     */
    public String getBio() { return bio; }

    /**
     * Sets the bio of the user.
     *
     * @param bio the new bio of the user
     */
    public void setBio(String bio) {this.bio = bio; }

    /**
     * Returns the number of posts by the user.
     *
     * @return the post count of the user
     */
    public int getPostsCount() { return postsCount; }

    /**
     * Returns the number of followers of the user.
     *
     * @return the followers count of the user
     */
    public int getFollowersCount() { return followersCount; }

    /**
     * Returns the number of users this user is following.
     *
     * @return the following count of the user
     */
    public int getFollowingCount() { return followingCount; }

    /**
     * Returns the list of pictures posted by the user.
     *
     * @return the list of pictures
     */
    public List<Picture> getPictures() { return pictures; }

    /**
     * Returns password
     * 
     * @return password
     */
    public String getPassword() { return password; }

    // Setter methods for followers and following counts

    /**
     * Sets the number of followers of the user.
     *
     * @param followersCount the new followers count
     */
   public void setFollowersCount(int followersCount) { this.followersCount = followersCount; }

    /**
     * Sets the number of users this user is following.
     *
     * @param followingCount the new following count
     */
   public void setFollowingCount(int followingCount) { this.followingCount = followingCount; }

    /**
     * Sets the post count for the user.
     *
     * @param postsCount the new post count
     */
   public void setPostCount(int postCount) { this.postsCount = postCount;}

    /**
     * Returns a string representation of the user,
     * including the username, bio, and password.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return username + ":" + bio + ":" + password; // Format as needed
    }

}