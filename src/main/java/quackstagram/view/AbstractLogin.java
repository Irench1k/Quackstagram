package src.main.java.quackstagram.view;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import src.main.java.quackstagram.*;
import src.main.java.quackstagram.images.*;
import src.main.java.quackstagram.user.*;
import src.main.java.quackstagram.view.*;

public abstract class AbstractLogin extends BaseFrameManager {
    private JButton primaryButton;
    private JButton secondaryButton;
    private JLabel lblPhoto;

    public AbstractLogin(String title) {
        super(title);
    }

    @Override
    protected String getFormattedTitle() {
        return "Quackstagram - " + this.getRawTitle();
    }

    @Override
    protected String getHeaderText() {
        return "Quackstagram 🐥";
    }

    @Override
    protected JComponent createControlPanel() {
        JComponent primaryButton = createPrimaryButton();
        JComponent secondaryButton = createSecondaryButton();

        // Panel to hold both buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Grid layout with 1 row, 2 columns
        buttonPanel.setBackground(Color.white);
        buttonPanel.add(primaryButton);
        buttonPanel.add(secondaryButton);

        return buttonPanel;
    }

    private JComponent createPrimaryButton() {
        primaryButton = new JButton(getRawTitle());
        primaryButton.addActionListener(this::onPrimaryButtonClick);
        primaryButton.setBackground(new Color(255, 90, 95)); // Use a red color that matches the mockup
        primaryButton.setForeground(Color.BLACK); // Set the text color to black
        primaryButton.setFocusPainted(false);
        primaryButton.setBorderPainted(false);
        primaryButton.setFont(new Font("Arial", Font.BOLD, 14));
        return primaryButton;
    }

    private JComponent createSecondaryButton() {
        // New button for navigating to SignUpUI
        secondaryButton = new JButton(getSecondButtonText());
        secondaryButton.addActionListener(this::onSecondaryButtonCLick); // TODO: Low Cohesion: This method triggers navigation, affecting UI flow and state management.
        secondaryButton.setBackground(Color.WHITE); // Set a different color for distinction
        secondaryButton.setForeground(Color.BLACK);
        secondaryButton.setFocusPainted(false);
        secondaryButton.setBorderPainted(false);
        return secondaryButton;
    }

    protected JPanel getDuckIcon() {
        // Profile picture placeholder without border
        lblPhoto = new JLabel();
        lblPhoto.setPreferredSize(new Dimension(80, 80));
        lblPhoto.setHorizontalAlignment(JLabel.CENTER);
        lblPhoto.setVerticalAlignment(JLabel.CENTER);
        lblPhoto.setIcon(new ImageIcon(
                new ImageIcon("img/logos/DACS.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        JPanel photoPanel = new JPanel(); // Use a panel to center the photo label
        photoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        photoPanel.add(lblPhoto);

        return photoPanel;
    }

    protected abstract String getSecondButtonText();

    protected abstract JComponent createMainContentPanel();

    protected abstract void onPrimaryButtonClick(ActionEvent event);

    protected abstract void onSecondaryButtonCLick(ActionEvent event);
}
