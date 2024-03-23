package quackstagram.views.postlogin;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import quackstagram.FileHandler;
import quackstagram.models.Notification;
import quackstagram.models.User;

public class NotificationsUI extends AbstractPostLogin {
    private JComponent contentPanel;

    public NotificationsUI(User currentUser) {
        super("Notifications", currentUser);
        this.contentPanel = createMainContentPanel();
    }

    @Override
    protected JComponent createMainContentPanel() {
        // Content Panel for notifications
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        updateNotifications();

        return scrollPane;
    }

    /**
     * Updates the notifications panel with the latest notifications for the current
     * user.
     * Removes all existing notifications from the panel and adds new notifications
     * retrieved from the file handler.
     * Each notification is displayed as a label within a panel.
     * The content panel is then revalidated and repainted to reflect the changes.
     * 
     * Gets updated by the Observer pattern when a new notification is added.
     */
    public void updateNotifications() {
        contentPanel.removeAll();

        for (Notification notification : FileHandler.getNotifications(getCurrentUser().getUsername())) {
            // Add the notification to the panel
            JPanel notificationPanel = new JPanel(new BorderLayout());
            notificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel notificationLabel = new JLabel(notification.getMessage());
            notificationPanel.add(notificationLabel, BorderLayout.CENTER);

            contentPanel.add(notificationPanel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
