package quackstagram.views.moderator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

public class ModeratorView extends JFrame {
    private JList<String> userListDisplay;
    private JTextArea userDetailsArea;
    private Consumer<String> onUserClick;
    private Consumer<String> onBioDelete;
    private JTextArea bioTextArea;

    public ModeratorView() {
        setTitle("Moderator Panel");
        setSize(new Dimension(400, 600));
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

        userDetailsArea = new JTextArea(10, 25);
        userDetailsArea.setEditable(false);
        attachPopupMenu(userDetailsArea);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(new JScrollPane(userListDisplay), BorderLayout.WEST);
        add(new JScrollPane(userDetailsArea), BorderLayout.CENTER);
    }

    private void attachPopupMenu(JComponent component) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteBioItem = new JMenuItem("Delete Bio");
        deleteBioItem.addActionListener(e -> {
            String selectedUser = userListDisplay.getSelectedValue();
            if (selectedUser != null && onBioDelete != null) {
                onBioDelete.accept(selectedUser);
            }
        });
        popupMenu.add(deleteBioItem);

        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void displayUserList(List<String> users) {
        userListDisplay.setListData(users.toArray(new String[0]));
    }

    public void setUserDetails(String details) {
        userDetailsArea.setText(details);
    }

    public void setOnUserClickListener(Consumer<String> onUserClick) {
        this.onUserClick = onUserClick;
    }

    public void setOnBioDeleteListener(Consumer<String> onBioDelete) {
        this.onBioDelete = onBioDelete;
    }
}
