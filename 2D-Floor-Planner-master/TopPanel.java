import java.awt.*;
import javax.swing.*;

public class TopPanel extends JPanel {
    ImageIcon saveIcon;

    public TopPanel() {
        setBackground(new Color(254, 169, 3)); // Yellow-orange color
        setPreferredSize(new Dimension(0, 70)); // Fixed height
        setLayout(new BorderLayout());

        // Add components
        JLabel proJLabel = new JLabel("Project");
        proJLabel.setFont(new Font("Arial", Font.BOLD, 10));
        proJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel projectLabel = new JLabel("Floor Plan Example 1");
        projectLabel.setFont(new Font("Arial", Font.BOLD, 22));
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);

        saveIcon = new ImageIcon("save.png");
        CustomButton2 saveButton = new CustomButton2("Save", saveIcon);
        saveButton.setMargin(new Insets(0, 0, 0, 60));

        //add(proJLabel, BorderLayout.NORTH);
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