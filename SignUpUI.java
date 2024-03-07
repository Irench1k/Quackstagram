import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SignUpUI extends AbstractLogin {
    private JTextField txtUsername; // TODO: Primitive Obsession: using simple text fields for sensitive information.
    private JTextField txtPassword;
    private JTextField txtBio; // TODO: Data Clump: Grouping of username, password, and bio suggests they should be a single object.
    private JButton btnUploadPhoto;
    private final String profilePhotoStoragePath = "img/storage/profile/";

    public SignUpUI() {
        super("Sign Up");
    }

    @Override
    protected String getSecondButtonText() {
        return "Already have an account? Sign In";
    }

    @Override
    protected JPanel createMainContentPanel() {
        JPanel fieldsPanel = new JPanel();
        JPanel photoPanel = getDuckIcon();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        txtUsername = new JTextField("Username");
        txtPassword = new JTextField("Password");
        txtBio = new JTextField("Bio");
        txtBio.setForeground(Color.GRAY);
        txtUsername.setForeground(Color.GRAY);
        txtPassword.setForeground(Color.GRAY);

        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(txtBio);
        btnUploadPhoto = new JButton("Upload Photo");

        btnUploadPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleProfilePictureUpload();
            }
        });
        JPanel photoUploadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoUploadPanel.add(btnUploadPhoto);
        fieldsPanel.add(photoUploadPanel);

        return fieldsPanel;
    }

    @Override
    protected void onPrimaryButtonClick(ActionEvent event) {
        // TODO: Shotgun Surgery: Similar logic appears in the SignInUI class. Changes
        // here may require changes there.
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String bio = txtBio.getText();
        InstagramReader reader = new InstagramReader();

        if (reader.doesUsernameExist(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            User newUser = new User(username, bio, password);
            reader.saveUserInformation(newUser);;
            handleProfilePictureUpload();
            dispose();

            // Open the SignInUI frame
            SwingUtilities.invokeLater(() -> {
                SignInUI signInFrame = new SignInUI();
                signInFrame.setVisible(true);
            });
        }
    }

    @Override
    protected void onSecondaryButtonCLick(ActionEvent event) {
        // TODO: Duplicated Code: Similar logic appears in SignInUI class
        // (onRegisterNowClicked)
        // Close the SignUpUI frame
        dispose();

        // Open the SignInUI frame
        SwingUtilities.invokeLater(() -> {
            SignInUI signInFrame = new SignInUI();
            signInFrame.setVisible(true);
        });
    }

    // Method to handle profile picture upload
    private void handleProfilePictureUpload() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveProfilePicture(selectedFile, txtUsername.getText());
        }
    }

    private void saveProfilePicture(File file, String username) {
        try {
            BufferedImage image = ImageIO.read(file);
            File outputFile = new File(profilePhotoStoragePath + username + ".png");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Error Handling: Directly printing stack trace.
        }
    }
}
