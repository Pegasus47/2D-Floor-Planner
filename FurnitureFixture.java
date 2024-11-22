import java.awt.*;

public class FurnitureFixture {
    private final String name;
    private final Color color;
    private double rotation = 0;
    private int x, y;
    private int width;
    private int height;
    private Rectangle rectangle;
    public Point originalPosition;

    // Constructor to initialize furniture or fixture
    public FurnitureFixture(Rectangle rectangle, String name, Color color) {
        this.name = name;
        this.rectangle = rectangle;
        this.color = color;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void rotateFurniture() {
        rotation += Math.PI / 2; // Rotate by 90 degrees
    }

    public void setPosition(Point newPosition) {
        this.rectangle.setLocation(newPosition);
    }

    public void resetPosition() {
        this.rectangle.setLocation(originalPosition);
    }

    public Point getPosition(){
        return this.rectangle.getLocation();
    }

    public boolean contains(Point p) {
        Rectangle bounds = new Rectangle(x, y, getWidth(), getHeight());
        return bounds.contains(p);
    }

    public void draw(Graphics2D g2d) {
        g2d.translate(x + getWidth() / 2, y + getHeight() / 2); // Center rotation
        g2d.rotate(rotation);
        //g2d.drawImage(image, -getWidth() / 2, -getHeight() / 2, null);
        //g2d.rotate(-rotation); // Reset rotation
        g2d.translate(-x - getWidth() / 2, -y - getHeight() / 2);
    }
}