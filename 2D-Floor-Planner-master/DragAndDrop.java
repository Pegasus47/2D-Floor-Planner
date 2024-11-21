import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DragAndDrop extends JPanel {
    private final ArrayList<Rooms> rooms; // List of all rooms
    private Rooms selectedRoom = null; // The room being dragged
    private Point originalPosition = null; // Original position of the dragged room
    private static final int GRID_SIZE = 40; // Grid size in pixels
    private Point offset; // Offset between mouse click and rectangle's top-left corner

    public DragAndDrop(ArrayList<Rooms> rooms) {
        this.rooms = rooms;

        // Mouse Adapter for dragging
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Rooms room : rooms) { // Check each room
                    Rectangle rect = room.rectangle;
                    if (rect.contains(e.getPoint())) {
                        selectedRoom = room;
                        originalPosition = new Point(rect.x, rect.y);

                        // Calculate the offset
                        offset = new Point(e.getX() - rect.x, e.getY() - rect.y);
                        return;
                    }
                }
                selectedRoom = null;
            }

            private int snapToGrid(int value) {
                return Math.round(value / (float) GRID_SIZE) * GRID_SIZE;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedRoom != null) {
                    // Snap mouse position to grid
                    int mouseX = snapToGrid(e.getX());
                    int mouseY = snapToGrid(e.getY());

                    // Calculate new position with grid snapping
                    int newX = mouseX - offset.x;
                    int newY = mouseY - offset.y;

                    // Temporarily update the room's position
                    selectedRoom.rectangle.setLocation(newX, newY);

                    // Repaint the panel to show the updated position
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedRoom != null) {
                    // Snap the rectangle to grid position on release
                    int finalX = snapToGrid(selectedRoom.rectangle.x);
                    int finalY = snapToGrid(selectedRoom.rectangle.y);

                    selectedRoom.rectangle.setLocation(finalX, finalY);

                    // Check for overlap after releasing the mouse
                    if (isOverlapping(selectedRoom.rectangle)) {
                        // Revert to original position on overlap
                        selectedRoom.rectangle.setLocation(originalPosition);

                        // Show warning dialog
                        JOptionPane.showMessageDialog(
                                DragAndDrop.this,
                                "Room overlaps with an existing room! Reverting to the original position.",
                                "Overlap Detected",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }

                    // Clear the selection and reset state
                    selectedRoom = null;
                    originalPosition = null;
                }
            }
        };

        // Register mouse listeners
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    // Check if the given room overlaps with any other room
    private boolean isOverlapping(Rectangle movingRect) {
        for (Rectangle existingRect : getRoomRectangles()) {
            if (!movingRect.equals(existingRect) && movingRect.intersects(existingRect)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Rectangle> getRoomRectangles() {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        for (Rooms room : rooms) {
            rectangles.add(room.rectangle);
        }
        return rectangles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid lines
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        // Draw all rooms
        for (Rooms room : rooms) {
            g2d.setColor(room.color);
            g2d.fillRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
        }
    }
}
