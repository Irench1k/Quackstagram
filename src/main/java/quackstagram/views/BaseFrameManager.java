package quackstagram.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The {@code BaseFrameManager} class is an abstract class that provides a template for creating UI frames with a consistent structure and style across the application.
 * It defines a basic frame structure including a header, main content area, and control panel, which can be customized by extending classes.
 */
public abstract class BaseFrameManager extends JFrame {
    protected static final int WIDTH = 300;
    protected static final int HEIGHT = 500;
    private String title;
    private Theme theme = Theme.getInstance();

    /**
     * Constructs a {@code BaseFrameManager} with the specified title.
     *
     * @param title The title of the frame.
     */
    public BaseFrameManager(String title) {
        System.out.println("Instantiating BaseFrmameManager and theme is : " + this.theme);
        this.title = title;
        setTitle(getFormattedTitle());
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Gets the raw title of the frame.
     *
     * @return The raw title string.
     */
    protected String getRawTitle() {
        return this.title;
    }

    /**
     * Gets the formatted title of the frame. Can be overridden to change the format.
     *
     * @return The formatted title string.
     */
    protected String getFormattedTitle() {
        return this.title;
    }

    /**
     * Retrieves a color specified by the {@code ColorID} from the theme.
     *
     * @param id The {@code ColorID} of the desired color.
     * @return The {@code Color} object corresponding to the specified ID.
     */
    protected Color getColor(ColorID id) {
        return this.theme.getColor(id);
    }

    /**
     * Retrieves the path to an icon specified by the {@code IconID} from the theme.
     *
     * @param id The {@code IconID} of the desired icon.
     * @return The path to the icon as a string.
     */
    protected String getIconPath(IconID id) {
        return this.theme.getIconPath(id);
    }

    /**
     * Initializes the user interface by creating and arranging the header, main content, and control panels.
     */
    protected void initializeUI() {
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

    /**
     * Creates the header panel with the header text.
     *
     * @return A {@code JPanel} that serves as the header of the frame.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(getColor(ColorID.BACKGROUND_HEADER)); // Set a darker background for the header
        JLabel lblRegister = new JLabel(getHeaderText());
        lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
        lblRegister.setForeground(getColor(ColorID.OPPOSITE_TEXT)); // Set the text color to white
        headerPanel.add(lblRegister);
        headerPanel.setPreferredSize(new Dimension(WIDTH, 40)); // Give the header a fixed height
        return headerPanel;
    }

    /**
     * Gets the header text for the frame. Can be overridden to provide specific header text.
     *
     * @return A string representing the header text.
     */
    protected String getHeaderText() {
        return getRawTitle() + "🐥";
    }

    /**
     * Abstract method to create the main content panel. Must be implemented by extending classes.
     *
     * @return A {@code JComponent} that serves as the main content area of the frame.
     */
    protected abstract JComponent createMainContentPanel();

    /**
     * Abstract method to create the control panel. Must be implemented by extending classes.
     *
     * @return A {@code JComponent} that serves as the control panel of the frame.
     */
    protected abstract JComponent createControlPanel();
}
