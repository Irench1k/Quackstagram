import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public interface ImageDisplay {
    static final int IMAGE_SIZE = AbstractUI.WIDTH / 3; // Adjust size as needed

    public default void loadImages(JPanel imageGridPanel) {
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

    public abstract void displayImage(String imagePath);

}
