package quackstagram.views.moderator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * The {@code ModeratorView} class provides a graphical user interface for the moderator's panel.
 * It includes functionality to display a list of users, show details for a selected user, and
 * perform delete operations on selected user attributes like bio and pictures.
 */
public class ModeratorView extends JFrame {
    private JList<String> userListDisplay;
    private JList<String> userDetailsList;
    private DefaultListModel<String> userDetailsModel;
    private JButton deleteButton;

    private Consumer<String> onUserClick;
    private Consumer<String> onAttributeClick;

    /**
     * Constructs a new {@code ModeratorView} setting up the main frame,
     * initializing components, and laying out the components in the panel.
     */
    public ModeratorView() {
        setTitle("Moderator Panel");
        setSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        layoutComponents();
    }

    /**
     * Initializes the components in the moderator panel including the user list,
     * user details list, and the delete button.
     */
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

    }

    /**
     * Lays out the components in the panel using a {@code BorderLayout}.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(new JScrollPane(userListDisplay), BorderLayout.WEST);
        add(new JScrollPane(userDetailsList), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(deleteButton, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Displays the list of users in the moderator panel.
     *
     * @param users the list of user names to display
     */
    public void displayUserList(List<String> users) {
        userListDisplay.setListData(users.toArray(new String[0]));
    }


    /**
     * Sets the user details in the user details display component.
     * Also triggers a refresh of the component to ensure the latest data is shown.
     *
     * @param details the list of details to display for the selected user
     */
    public void setUserDetails(List<String> details) {
        userDetailsModel.clear();
        for (String detail : details) {
            userDetailsModel.addElement(detail);
        }
        userDetailsList.revalidate();
        userDetailsList.repaint();
    }

    /**
     * Sets the callback for when a user is clicked in the user list.
     *
     * @param onUserClick the consumer action to perform on user click
     */
    public void setOnUserClickListener(Consumer<String> onUserClick) {
        this.onUserClick = onUserClick;
    }

    /**
     * Sets the callback for when an attribute is clicked in the user details list.
     *
     * @param onAttributeClick the consumer action to perform on attribute click
     */
    public void setOnAttributeClickListener(Consumer<String> onAttributeClick) {
        this.onAttributeClick = onAttributeClick;
    }

    /**
     * Updates the visibility of the delete button based on the selection in the user details list.
     */
    private void updateDeleteButtonVisibility() {
        boolean selectionExists = !userDetailsList.isSelectionEmpty();
        deleteButton.setVisible(selectionExists); // Show/hide the delete button based on selection
    }

    /**
     * Gets the username of the currently selected user in the user list.
     *
     * @return the username of the selected user, or null if no user is selected
     */
    public String getSelectedUsername() {
        return userListDisplay.getSelectedValue();
    }

}

