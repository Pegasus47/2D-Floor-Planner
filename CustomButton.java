import java.awt.*;
import javax.swing.*;

public class CustomButton extends JButton {

    private final Color backgroundColor = new Color(239, 163, 15); // Button background color
    private final Color borderColor = new Color(239, 163, 15); // Button border color
    private final int arcSize = 20; // Corner radius

    // Constructor for text-only button
    public CustomButton(String text) {
        this(text, null); // Delegate to the constructor with text and image
    }

    // Constructor for text and image button
    public CustomButton(String text, ImageIcon imageIcon) {
        super(text);
        setContentAreaFilled(false); 
        setFocusPainted(false); 
        setBorderPainted(false); 
        setFont(new Font("Arial", Font.PLAIN, 15)); 
        
        if (imageIcon != null) {
            setIcon(resizeIcon(imageIcon, 20, 20)); // Resize icon to desired size
            setHorizontalTextPosition(SwingConstants.RIGHT); // Default: text to the right of icon
            setVerticalTextPosition(SwingConstants.CENTER);  // Default: center alignment
            setIconTextGap(10); // Default gap between icon and text
        }
    }

    // Method to resize the icon
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Setters to customize icon position and gap
    public void setIconPosition(int horizontalPosition, int verticalPosition) {
        setHorizontalTextPosition(horizontalPosition);
        setVerticalTextPosition(verticalPosition);
    }

    public void setIconTextGap(int gap) {
        super.setIconTextGap(gap);
    }

    // Override the paintComponent method to paint the rounded corners
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

        // Draw button background with rounded corners
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);

        // Draw button border
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize);

        super.paintComponent(g);

        g2d.dispose();
    }
}
