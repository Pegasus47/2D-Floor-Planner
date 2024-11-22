import java.awt.*;

public class FurnitureFixture {
    private final String name;
    private final Dimension size;
    private final Color color;

    // Constructor to initialize furniture or fixture
    public FurnitureFixture(String name, Dimension size, Color color) {
        this.name = name;
        this.size = size;
        this.color = color;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public Dimension getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }
}