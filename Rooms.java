import java.awt.*;

public class Rooms {
    Rectangle rectangle;
    Color color;
    Point originalPosition; //maybe change this to currentPosition later

    public Rooms(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.originalPosition = new Point(rectangle.x, rectangle.y);
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
    //for rooms class
    public void rotate() 
    {
        // Swap width and height
        //rectangle.x = rectangle.x;
        //rectangle.y = rectangle.y;
        int temp = rectangle.width;
        rectangle.width = rectangle.height;
        rectangle.height = temp;
    }
}