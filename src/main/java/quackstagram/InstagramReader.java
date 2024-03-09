package src.main.java.quackstagram;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import src.main.java.quackstagram.*;
import src.main.java.quackstagram.images.*;
import src.main.java.quackstagram.ui.*;
import src.main.java.quackstagram.user.*;

public class InstagramReader {
    public int readImageCount(User user) {
        int imageCount = 0;
        Path imageDetailsFilePath = Paths.get("img", "image_details.txt");
        try (BufferedReader imageDetailsReader = Files.newBufferedReader(imageDetailsFilePath)) {
            String line;
            while ((line = imageDetailsReader.readLine()) != null) {
                if (line.contains("Username: " + user.getUsername())) {
                    imageCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageCount;
    }

    public int readFollowingCount(User user) {
        Path followingFilePath = Paths.get("data", "following.txt");
        int followingCount = 0;
        try (BufferedReader followingReader = Files.newBufferedReader(followingFilePath)) {
            String line;
            while ((line = followingReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String[] followingUsers = parts[1].split(";");
                    if (username.equals(user.getUsername())) {
                        followingCount = followingUsers.length;
                    } 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return followingCount;
    }
    
    public int readFollowersCount(User user) {
        Path followingFilePath = Paths.get("data", "following.txt");
        int followersCount = 0;
        try (BufferedReader followingReader = Files.newBufferedReader(followingFilePath)) {
            String line;
            while ((line = followingReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String[] followingUsers = parts[1].split(";");
                    for (String followingUser : followingUsers) {
                        if (followingUser.trim().equals(user.getUsername())) {
                            followersCount++;
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return followersCount;
    }

    public String readBio(User user) {
        String bio = "";
        Path bioDetailsFilePath = Paths.get("data", "credentials.txt");
        try (BufferedReader bioDetailsReader = Files.newBufferedReader(bioDetailsFilePath)) {
            String line;
            while ((line = bioDetailsReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(user.getUsername()) && parts.length >= 3) {
                    bio = parts[2];
                    break; // Exit the loop once the matching bio is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bio;
    }

    public String readLoggedInUserName() {
        String loggedInUsername = "";

        // Read the logged-in user's username from users.txt
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("data", "users.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                loggedInUsername = line.split(":")[0].trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loggedInUsername;
    }

    public Boolean readAlreadyFollowing(String loggedInUsername, User currentUser) {
        Path followingFilePath = Paths.get("data", "following.txt");
            try (BufferedReader ireader = Files.newBufferedReader(followingFilePath)) {
                String line;
                while ((line = ireader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts[0].trim().equals(loggedInUsername)) {
                        String[] followedUsers = parts[1].split(";");
                        for (String followedUser : followedUsers) {
                            if (followedUser.trim().equals(currentUser.getUsername())) {
                                return true;
                            }
                        }
                    }
                    
                }
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    }

    public String readCurrentUser() {
        Path usersFilePath = Paths.get("data", "users.txt");
        String currentUserUsername = "";
        try (BufferedReader reader = Files.newBufferedReader(usersFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                currentUserUsername = parts[0];
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return currentUserUsername;
    }

    public void updateFollowing(String follower, String usernameToFollow) {
        boolean found = false;
        Path followingFilePath = Paths.get("data", "following.txt");
        StringBuilder newContent = new StringBuilder();

        // Read and process following.txt
        if (Files.exists(followingFilePath)) {
            try (BufferedReader reader = Files.newBufferedReader(followingFilePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts[0].trim().equals(follower)) {
                        found = true;
                        if (!line.contains(usernameToFollow)) {
                            line = line.concat(line.endsWith(":") ? "" : "; ").concat(usernameToFollow);
                        }
                    }
                    newContent.append(line).append("\n");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // If the current user was not found in following.txt, add them
        if (!found) {
            newContent.append(follower).append(": ").append(usernameToFollow).append("\n");
        }

        // Write the updated content back to following.txt
        try (BufferedWriter writer = Files.newBufferedWriter(followingFilePath)) {
            writer.write(newContent.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }   

    public void saveUserInformation(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/users.txt", false))) {
            writer.write(user.toString()); // Implement a suitable toString method in User class
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Here again, improper error handling.
        }
    }

    public boolean verifyCredentials(User user) {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(user.getUsername()) && credentials[1].equals(user.getPassword())) {
                    String bio = credentials[2];
                    // Create User object and save information
                    user.setBio(bio);; // Assuming User constructor takes these parameters
                    saveUserInformation(user);

                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Error Handling: Directly printing stack trace.
        }
        return false;
    }

    public boolean doesUsernameExist(String username) {
        String credentialsFilePath = "data/credentials.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(credentialsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ":")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Error Handling: Directly printing stack trace.
        }
        return false;
    }
}



