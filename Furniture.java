import java.awt.*;
import java.awt.image.BufferedImage;

public class Furniture {
    private BufferedImage image;
    private int x, y;
    private double rotation = 0; // Rotation angle in radians

    public Furniture(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void rotateFurniture() {
        rotation += Math.PI / 2; // Rotate by 90 degrees
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public boolean contains(Point p) {
        Rectangle bounds = new Rectangle(x, y, getWidth(), getHeight());
        return bounds.contains(p);
    }

    public void draw(Graphics2D g2d) {
        g2d.translate(x + getWidth() / 2, y + getHeight() / 2); // Center rotation
        g2d.rotate(rotation);
        g2d.drawImage(image, -getWidth() / 2, -getHeight() / 2, null);
        g2d.rotate(-rotation); // Reset rotation
        g2d.translate(-x - getWidth() / 2, -y - getHeight() / 2);
    }
}