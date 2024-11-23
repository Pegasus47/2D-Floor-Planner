import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DragAndDropHandler {
    private final JComponent component;
    private Point initialClick;
    private boolean dragging;

    public DragAndDropHandler(JComponent component) {
        this.component = component;

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                dragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        });

        component.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    // Get the current location of the component
                    Point currentLocation = component.getLocation();

                    // Calculate the new location based on mouse movement
                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;

                    int newX = currentLocation.x + xMoved;
                    int newY = currentLocation.y + yMoved;

                    // Update the component's location
                    component.setLocation(newX, newY);
                }
            }
        });
    }
}
