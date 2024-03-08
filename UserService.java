import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

public class UserService {

    // I just merged Selems code with the original code
    public User getUserDetails(User currentUser) {
        InstagramReader reader = new InstagramReader();
        currentUser.setPostCount(reader.readImageCount(currentUser));
        currentUser.setFollowersCount(reader.readFollowersCount(currentUser));
        currentUser.setFollowingCount(reader.readFollowingCount(currentUser));
        currentUser.setBio(reader.readBio(currentUser));

        System.out.println("Bio for " + currentUser.getUsername() + ": " + currentUser.getBio());
        System.out.println(currentUser.getPostsCount());

        return currentUser;
    }

    // !!!!!!!!!!!!!!!!
    public Stream<Path> getUserImages(User user) {
        // For example, let's say we're getting the user's images from a directory
        try {
            return Files.list(Paths.get("img", "uploaded"))
                    .filter(path -> path.getFileName().toString().startsWith(user.getUsername() + "_"));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    public void handleFollowAction(String usernameToFollow) {
        InstagramReader reader = new InstagramReader();
        String currentUserUsername = reader.readCurrentUser();
        System.out.println("Real user is " + currentUserUsername);
        // If currentUserUsername is not empty, process following.txt
        if (!currentUserUsername.isEmpty()) {
            reader.updateFollowing(currentUserUsername, usernameToFollow);
        }

    }
}
