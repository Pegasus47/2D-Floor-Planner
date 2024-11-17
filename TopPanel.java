import java.awt.*;
import javax.swing.*;

public class TopPanel extends JPanel {
    public TopPanel() {
        setBackground(new Color(255, 193, 7)); // Yellow-orange color
        setPreferredSize(new Dimension(0, 50)); // Fixed height
        setLayout(new BorderLayout());

        // Add components
        JLabel projectLabel = new JLabel("Floor Plan Example 1");
        projectLabel.setFont(new Font("Arial", Font.BOLD, 18));
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton saveButton = new JButton("Save");

        add(projectLabel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.EAST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass method to ensure proper painting
        super.paintComponent(g);

        // Cast to Graphics2D for advanced rendering
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color and draw a rounded rectangle
        g2d.setColor(getBackground());
        int arc = 20; // The arc size for rounded corners
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Dispose of the Graphics2D object to release system resources
        g2d.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false; // Make the panel non-opaque to show rounded corners
    }
}
