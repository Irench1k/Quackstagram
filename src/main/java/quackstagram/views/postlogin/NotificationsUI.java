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
import quackstagram.views.ColorID;

public class NotificationsUI extends AbstractPostLogin {

    public NotificationsUI(User currentUser) {
        super("Notifications", currentUser);
    }

    @Override
    protected JComponent createMainContentPanel() {
        // Content Panel for notifications
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setOpaque(false); // Make the viewport non-opaque
        scrollPane.getViewport().setBackground(getColor(ColorID.MAIN_BACKGROUND));

        for (Notification notification : FileHandler.getNotifications(getCurrentUser().getUsername())) {
            // Add the notification to the panel
            JPanel notificationPanel = new JPanel(new BorderLayout());
            notificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            notificationPanel.setOpaque(false);  // Make the notification panels non-opaque
            notificationPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));

            JLabel notificationLabel = new JLabel(notification.getMessage());
            notificationLabel.setForeground(getColor(ColorID.TEXT_PRIMARY));
            notificationPanel.add(notificationLabel, BorderLayout.CENTER);

            contentPanel.add(notificationPanel);
        }

        contentPanel.setBackground(getColor(ColorID.MAIN_BACKGROUND));
        contentPanel.setBorder(null);

        return scrollPane;
    }
}
