import java.awt.*;
import javax.swing.*;

public class MainControlPanel extends JPanel {

    public MainControlPanel() {
        // Set the preferred size and background properties
        setBackground(new Color(255, 194, 74)); // Background color
        setPreferredSize(new Dimension(250, 700)); // Desired fixed size
        setOpaque(false); // Ensures transparency to show the rounded corners

        // Use absolute layout for precise positioning
        setLayout(null);

        // Example of adding a child component
        AddRoomButton addRoomButton = new AddRoomButton();
        addRoomButton.setBounds(20, 20, 250, 300); // Adjust bounds to fit inside the panel
        add(addRoomButton);

        /*AddWallButton addWallButton = new AddWallButton();
        addWallButton.setBounds(20, 60, 120, 200);
        add(addWallButton);*/
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass method
        super.paintComponent(g);

        // Cast to Graphics2D for more control
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smooth rounded edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color and draw a rounded rectangle
        g2d.setColor(new Color(255, 194, 74));
        int arc = 30; // The arc size for rounded corners
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Dispose of the Graphics object to free resources
        g2d.dispose();
    }
}
