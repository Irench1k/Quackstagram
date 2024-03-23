package quackstagram.views.moderator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

public class ModeratorView extends JFrame {
    private JList<String> userListDisplay;
    private JList<String> userDetailsList;
    private DefaultListModel<String> userDetailsModel;
    private JButton deleteButton;

    private Consumer<String> onUserClick;
    private Consumer<String> onAttributeClick;

    public ModeratorView() {
        setTitle("Moderator Panel");
        setSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        userListDisplay = new JList<>();
        userListDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userListDisplay.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedUser = userListDisplay.getSelectedValue();
                if (selectedUser != null && onUserClick != null) {
                    onUserClick.accept(selectedUser);
                }
            }
        });

        userDetailsModel = new DefaultListModel<>();
        userDetailsList = new JList<>(userDetailsModel);
        userDetailsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userDetailsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDeleteButtonVisibility();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            String selectedAttribute = userDetailsList.getSelectedValue();
            if (selectedAttribute != null && onAttributeClick != null) {
                onAttributeClick.accept(selectedAttribute);
            }
        });
        deleteButton.setVisible(false); // The delete button is initially invisible

        attachPopupMenu(userDetailsList);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(new JScrollPane(userListDisplay), BorderLayout.WEST);
        add(new JScrollPane(userDetailsList), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(deleteButton, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }

    private void attachPopupMenu(JComponent component) {
        // ... Popup menu initialization remains unchanged ...
    }

    public void displayUserList(List<String> users) {
        userListDisplay.setListData(users.toArray(new String[0]));
    }

    public void setUserDetails(List<String> details) {
        userDetailsModel.clear();
        for (String detail : details) {
            userDetailsModel.addElement(detail);
        }
    }

    public void setOnUserClickListener(Consumer<String> onUserClick) {
        this.onUserClick = onUserClick;
    }

    public void setOnAttributeClickListener(Consumer<String> onAttributeClick) {
        this.onAttributeClick = onAttributeClick;
    }

    private void updateDeleteButtonVisibility() {
        boolean selectionExists = !userDetailsList.isSelectionEmpty();
        deleteButton.setVisible(selectionExists); // Show/hide the delete button based on selection
    }

    // Add other methods if necessary ...
}

