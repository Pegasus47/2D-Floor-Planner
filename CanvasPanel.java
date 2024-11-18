import java.awt.*;
import javax.swing.*;

public class CanvasPanel extends JPanel {
    public CanvasPanel() {
        setBackground(new Color(40, 40, 40)); // Dark gray background
        setLayout(null); // Absolute layout for drag-and-drop
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        
        // Draw grid
        int gridSize = 20;
        for (int x = 0; x < getWidth(); x += gridSize) {
            for (int y = 0; y < getHeight(); y += gridSize) {
                g.drawRect(x, y, gridSize, gridSize);
            }
        }
    }
}
