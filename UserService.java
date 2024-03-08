import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;
 
public class UserService {
    public User getUserDetails(User currentUser) {
        // Initialize counts
        int imageCount = 0;
        int followersCount = 0;
        int followingCount = 0;

        // Step 1: Read image_details.txt to count the number of images posted by the
        // user
        Path imageDetailsFilePath = Paths.get("img", "image_details.txt");
        try (BufferedReader imageDetailsReader = Files.newBufferedReader(imageDetailsFilePath)) {
            String line;
            while ((line = imageDetailsReader.readLine()) != null) {
                if (line.contains("Username: " + currentUser.getUsername())) {
                    imageCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 2: Read following.txt to calculate followers and following
        Path followingFilePath = Paths.get("data", "following.txt");
        try (BufferedReader followingReader = Files.newBufferedReader(followingFilePath)) {
            String line;
            while ((line = followingReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String[] followingUsers = parts[1].split(";");
                    if (username.equals(currentUser.getUsername())) {
                        followingCount = followingUsers.length;
                    } else {
                        for (String followingUser : followingUsers) {
                            if (followingUser.trim().equals(currentUser.getUsername())) {
                                followersCount++;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String bio = "";

        Path bioDetailsFilePath = Paths.get("data", "credentials.txt");
        try (BufferedReader bioDetailsReader = Files.newBufferedReader(bioDetailsFilePath)) {
            String line;
            while ((line = bioDetailsReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(currentUser.getUsername()) && parts.length >= 3) {
                    bio = parts[2];
                    break; // Exit the loop once the matching bio is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Bio for " + currentUser.getUsername() + ": " + bio);
        currentUser.setBio(bio);

        currentUser.setFollowersCount(followersCount);
        currentUser.setFollowingCount(followingCount);
        currentUser.setPostCount(imageCount);

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
        Path followingFilePath = Paths.get("data", "following.txt");
        Path usersFilePath = Paths.get("data", "users.txt");
        String currentUserUsername = "";

        try {
            // Read the current user's username from users.txt
            try (BufferedReader reader = Files.newBufferedReader(usersFilePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    currentUserUsername = parts[0];
                }
            }

            System.out.println("Real user is " + currentUserUsername);
            // If currentUserUsername is not empty, process following.txt
            if (!currentUserUsername.isEmpty()) {
                boolean found = false;
                StringBuilder newContent = new StringBuilder();

                // Read and process following.txt
                if (Files.exists(followingFilePath)) {
                    try (BufferedReader reader = Files.newBufferedReader(followingFilePath)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(":");
                            if (parts[0].trim().equals(currentUserUsername)) {
                                found = true;
                                if (!line.contains(usernameToFollow)) {
                                    line = line.concat(line.endsWith(":") ? "" : "; ").concat(usernameToFollow);
                                }
                            }
                            newContent.append(line).append("\n");
                        }
                    }
                }

                // If the current user was not found in following.txt, add them
                if (!found) {
                    newContent.append(currentUserUsername).append(": ").append(usernameToFollow).append("\n");
                }

                // Write the updated content back to following.txt
                try (BufferedWriter writer = Files.newBufferedWriter(followingFilePath)) {
                    writer.write(newContent.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
