import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractLogin extends JFrame{
    protected static final int WIDTH = 300;
    protected static final int HEIGHT = 500;
    private JButton primaryButton;
    private JButton secondaryButton;
    private JLabel lblPhoto;
    private String title;


    public AbstractLogin(String title) {
        this.title = title;
        setTitle("Quackstagram - " + title);
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        initializeUI();
    }

    private void initializeUI() {
        JPanel headerPanel = createHeaderPanel();
        JPanel fieldsPanel = createFieldPanel();
        JPanel buttonPanel = createButtoPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        // Header with the Register label
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(51, 51, 51)); // Set a darker background for the header
        JLabel lblRegister = new JLabel("Quackstagram 🐥");
        lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
        lblRegister.setForeground(Color.WHITE); // Set the text color to white
        headerPanel.add(lblRegister);
        headerPanel.setPreferredSize(new Dimension(WIDTH, 40)); // Give the header a fixed height

        return headerPanel;
    }

    private JPanel createButtoPanel() {
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
        primaryButton = new JButton(title);
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

    protected abstract JPanel createFieldPanel();

    protected abstract void onPrimaryButtonClick(ActionEvent event);

    protected abstract void onSecondaryButtonCLick(ActionEvent event);
}
