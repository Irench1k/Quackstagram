package src.main.java.quackstagram;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractUI extends BaseFrameManager {
    protected static final int NAV_ICON_SIZE = 20;

    public AbstractUI(String title) {
        super(title);
    }

    @Override
    protected abstract JComponent createMainContentPanel();

    @Override
    protected JComponent createControlPanel() {
        // Create and return the navigation panel
        // Navigation Bar
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        navigationPanel.add(createIconButton("img/icons/home.png", "home"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/search.png", "explore"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/add.png", "add"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/heart.png", "notification"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/profile.png", "profile"));

        return navigationPanel;
    }

    protected List<String> disabledIcons() {
        return new ArrayList<String>();
    }

    private JButton createIconButton(String iconPath, String buttonType) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        if (disabledIcons().contains(buttonType)) {
            return button;
        }

        // Define actions based on button type
        if ("home".equals(buttonType)) {
            button.addActionListener(e -> openHomeUI());
        } else if ("profile".equals(buttonType)) {
            button.addActionListener(e -> openProfileUI());
        } else if ("notification".equals(buttonType)) {
            button.addActionListener(e -> notificationsUI());
        } else if ("explore".equals(buttonType)) {
            button.addActionListener(e -> exploreUI());
        } else if ("add".equals(buttonType)) {
            button.addActionListener(e -> ImageUploadUI());
        }

        return button;

    }

    protected void ImageUploadUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        ImageUploadUI upload = new ImageUploadUI();
        upload.setVisible(true);
    }

    protected void openProfileUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        InstagramReader reader = new InstagramReader();
        User user = new User(reader.readLoggedInUserName());
        InstagramProfileUI profileUI = new InstagramProfileUI(user);
        profileUI.setVisible(true);
    }

    protected void notificationsUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        NotificationsUI notificationsUI = new NotificationsUI();
        notificationsUI.setVisible(true);
    }

    protected void openHomeUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        QuakstagramHomeUI homeUI = new QuakstagramHomeUI();
        homeUI.setVisible(true);
    }

    protected void exploreUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        ExploreUI explore = new ExploreUI();
        explore.setVisible(true);
    }
}
