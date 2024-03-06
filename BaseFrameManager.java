import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public abstract class BaseFrameManager extends JFrame {
    protected static final int WIDTH = 300;
    protected static final int HEIGHT = 500;
    private String title;

    public BaseFrameManager(String title) {
        this.title = title;
        setTitle(getFormattedTitle());
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        initializeUI();
    }

    protected String getRawTitle() {
        return this.title;
    }

    protected String getFormattedTitle() {
        return this.title;
    }

    private void initializeUI() {
        JComponent headerPanel = createHeaderPanel();
        JComponent mainPanel = createMainContentPanel();
        JComponent controlPanel = createControlPanel();

        // Add panels to the frame
        if (getHeaderText() != null) {
            add(headerPanel, BorderLayout.NORTH);
        }
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Defined here, but needs configuration
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(51, 51, 51)); // Set a darker background for the header
        JLabel lblRegister = new JLabel(getHeaderText());
        lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
        lblRegister.setForeground(Color.WHITE); // Set the text color to white
        headerPanel.add(lblRegister);
        headerPanel.setPreferredSize(new Dimension(WIDTH, 40)); // Give the header a fixed height
        return headerPanel;
    }

    protected String getHeaderText() {
        return getRawTitle() + "🐥";
    }

    protected abstract JComponent createMainContentPanel();

    protected abstract JComponent createControlPanel();
}
