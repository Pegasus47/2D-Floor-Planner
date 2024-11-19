import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DragAndDrop extends JPanel {
    private final ArrayList<Rooms> rooms;
    private Rooms selectedRoom; // The room being dragged
    private Point initialClick; // Initial mouse click location
    private Point originalPosition; // Original position of the selected room

    public DragAndDrop(ArrayList<Rooms> rooms) {
        this.rooms = rooms;

        // Add mouse listeners for dragging
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Rooms room : rooms) {
                    if (room.rectangle.contains(e.getPoint())) {
                        selectedRoom = room;
                        originalPosition = room.rectangle.getLocation();
                        initialClick = e.getPoint();
                        break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedRoom != null && initialClick != null) {
                    // Calculate new position
                    int dx = e.getX() - initialClick.x;
                    int dy = e.getY() - initialClick.y;

                    // Move rectangle
                    Rectangle rect = selectedRoom.rectangle;
                    rect.setLocation(rect.x + dx, rect.y + dy);

                    // Update initial click point
                    initialClick = e.getPoint();

                    // Check for overlap
                    if (isOverlapping(selectedRoom)) {
                        // Show dialog box
                        JOptionPane.showMessageDialog(
                                DragAndDrop.this,
                                "Room overlaps with an existing room! Reverting to original position.",
                                "Overlap Detected",
                                JOptionPane.WARNING_MESSAGE
                        );

                        // Revert to original position
                        rect.setLocation(originalPosition);
                        selectedRoom = null; // End dragging
                    }

                    // Repaint the panel
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedRoom = null; // Stop dragging
            }
        };

        // Register mouse listener and motion listener
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private boolean isOverlapping(Rooms movingRoom) {
        Rectangle rect1 = movingRoom.rectangle;
        for (Rooms room : rooms) {
            if (room != movingRoom && rect1.intersects(room.rectangle)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all rooms
        Graphics2D g2d = (Graphics2D) g;
        for (Rooms room : rooms) {
            g2d.setColor(room.color);
            g2d.fillRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
        }
    }
}
