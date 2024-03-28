package quackstagram.views.postlogin;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import quackstagram.models.User;
import quackstagram.views.BaseFrameManager;
import quackstagram.views.ColorID;
import quackstagram.views.IconID;
import quackstagram.views.postlogin.commands.*;

/**
 * An abstract class that serves as a common base for all views accessible after authentication.
 * It extends the BaseFrameManager to provide a unified structure and behavior for post-login views,
 * including navigation and user context handling.
 */
public abstract class AbstractPostLogin extends BaseFrameManager {
    protected static final int NAV_ICON_SIZE = 20; // Standard size for navigation icons
    protected User currentUser; // Current user context

    /**
     * Constructs an AbstractPostLogin view with the specified title and user context.
     *
     * @param title The title of the window.
     * @param currentUser The currently logged-in user.
     */
    public AbstractPostLogin(String title, User currentUser) {
        super(title);
        this.currentUser = currentUser;
        initializeUI();
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The current user.
     */
    protected User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Creates the main content panel of the view.
     * This method is abstract and must be implemented by subclasses to define the specific content of each view.
     *
     * @return A JComponent that represents the main content panel of the view.
     */
    @Override
    protected abstract JComponent createMainContentPanel();

    /**
     * Creates a control panel typically used for navigation.
     * This implementation creates a navigation bar with icons for home, explore, add, notification, and profile.
     *
     * @return A JComponent that represents the navigation panel.
     */
    @Override
    protected JComponent createControlPanel() {
        // Create and return the navigation panel
        // Navigation Bar
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(getColor(ColorID.MINOR_BACKGROUND));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        navigationPanel.add(createIconButton(getIconPath(IconID.HOME), "home"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(getIconPath(IconID.SEARCH), "explore"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(getIconPath(IconID.ADD), "add"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(getIconPath(IconID.HEART), "notification"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(getIconPath(IconID.PROFILE), "profile"));

        return navigationPanel;
    }

    /**
     * Lists icons that should be disabled in the navigation bar.
     * Can be overridden by subclasses to disable specific icons.
     *
     * @return A list of strings representing the types of icons to be disabled.
     */
    protected List<String> disabledIcons() {
        return new ArrayList<String>();
    }

    /**
     * Creates a button with an icon for the navigation bar.
     *
     * @param iconPath The path to the icon image.
     * @param buttonType A string identifying the type of button (e.g., "home", "profile").
     * @return A JButton with the specified icon and action.
     */
    private JButton createIconButton(String iconPath, String buttonType) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        if (!disabledIcons().contains(buttonType)) {
            button.addActionListener(e -> performActionBasedOnButtonType(buttonType));
        }

        return button;
    }

    /**
     * Performs an action based on the type of button clicked in the navigation bar.
     * This method dynamically instantiates and executes the appropriate command for the button action.
     *
     * @param buttonType The type of button that was clicked.
     */
    private void performActionBasedOnButtonType(String buttonType) {
        NavigationCommand command = null;

        switch (buttonType) {
            case "home":
                command = new OpenHomeUICommand(this);
                break;
            case "profile":
                command = new OpenInstagramProfileUICommand(this);
                break;
            case "notification":
                command = new OpenNotificationUICommand(this);
                break;
            case "explore":
                command = new OpenExploreUICommand(this);
                break;
            case "add":
                command = new OpenImageUploadUICommand(this);
                break;
            default:
                break;
        }
        if (command != null) {
            command.execute(currentUser);
            this.dispose();
        }
    }








}
