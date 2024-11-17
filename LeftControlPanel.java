import java.awt.*;
import javax.swing.*;

public class LeftControlPanel extends JPanel {
    public LeftControlPanel() {
        setBackground(new Color(255, 194, 74)); // Set background color
        setPreferredSize(new Dimension(96, 600)); // Fixed width
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add buttons
        CustomButton export = new CustomButton("Export");
        CustomButton build = new CustomButton("Build");
        CustomButton objects = new CustomButton("Objects");
        CustomButton share = new CustomButton("Share");
        CustomButton help = new CustomButton("Help");

        export.setAlignmentX(CENTER_ALIGNMENT);
        build.setAlignmentX(CENTER_ALIGNMENT);
        objects.setAlignmentX(CENTER_ALIGNMENT);
        share.setAlignmentX(CENTER_ALIGNMENT);
        help.setAlignmentX(CENTER_ALIGNMENT);

        add(export);
        add(Box.createVerticalStrut(10)); // Spacer
        add(build);
        add(Box.createVerticalStrut(10)); // Spacer
        add(objects);
        add(Box.createVerticalStrut(10)); // Spacer
        add(share);
        add(Box.createVerticalStrut(10)); // Spacer
        add(help);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass method to ensure proper painting
        super.paintComponent(g);

        // Cast Graphics to Graphics2D for advanced rendering options
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color and draw a rounded rectangle
        g2d.setColor(getBackground());
        int arcWidth = 30;  // The horizontal arc for rounded corners
        int arcHeight = 30; // The vertical arc for rounded corners
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        // Dispose of the Graphics2D object to release system resources
        g2d.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false; // Make the panel non-opaque to allow rounded corners
    }
}
