import java.awt.*;
import java.awt.image.BufferedImage;

public class FurnitureFixture {
    private final String name;
    private final Dimension size;
    private final Color color;
    private BufferedImage image; 
    private double rotation = 0; 

    public FurnitureFixture(String name, Dimension size, Color color, BufferedImage image) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.image = image;
    }

    // Getter 
    public String getName() {
        return name;
    }

    public Dimension getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public BufferedImage getImage() {
        return image;
    }

    public double getRotation() {
        return rotation;
    }

    public void rotateFurniture() {
        rotation += Math.PI / 2; 
        rotation %= 2 * Math.PI; 
    }

    // Render the rotated image
    public void draw(Graphics2D g2d, int x, int y) {
        if (image != null) {
    
            Graphics2D g = (Graphics2D) g2d.create();


            g.rotate(rotation, x + size.width / 2.0, y + size.height / 2.0);
            g.drawImage(image, x, y, size.width, size.height, null);


            g.dispose();
        } else {
            g2d.setColor(color);
            g2d.fillRect(x, y, size.width, size.height);
        }
    }
}
