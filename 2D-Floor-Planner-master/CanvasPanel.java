import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class CanvasPanel extends JPanel {
    private Point startPoint;
    private Point endPoint;
    private boolean drawingEnabled = false;
    private String buttonName = ""; // Store the selected button name
    private final ArrayList<Rooms> rooms = new ArrayList<>();
    private Rooms selectedRoom = null; // Room being dragged
    private Point initialClick = null; // Initial point of dragging
//new things called originalPostiion and grid size added
    private Point originalPosition = null; // Original position of the dragged room
    protected static final int GRID_SIZE = 20; 
    private Point offset; 

    public CanvasPanel() {
        this.setBackground(new Color(40, 40, 40));
        this.setLayout(null);

        // Mouse Adapter for room drawing and dragging
        MouseAdapter mouseAdapter = new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (drawingEnabled) {
                    //startPoint = e.getPoint();

                    //newCodeBlockAdded
                    startPoint = snapToGrid(e.getPoint());
                } else {
                    // Check if clicking inside a room for dragging
                    for (Rooms room : rooms) {
                        if (room.rectangle.contains(e.getPoint())) {
                            selectedRoom = room;
                            initialClick = e.getPoint();
                            break;
                        }
                    }


//new code block added
                Point snappedPoint = snapToGrid(e.getPoint());
                for (Rooms room : rooms) { // Check each room
                    Rectangle rect = room.rectangle;
                    if (rect.contains(snappedPoint)) {
                        selectedRoom = room;
                        originalPosition = new Point(rect.x, rect.y);

                        // Calculate the offset
                        offset = new Point(snappedPoint.x - rect.x, snappedPoint.y - rect.y);
                        return;
                    }
                }
                selectedRoom = null;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) 
            {
                if (drawingEnabled) {
                    endPoint = e.getPoint();
                    repaint();
                } else if (selectedRoom != null && initialClick != null) {
                    // Dragging logic
                    int dx = e.getX() - initialClick.x;
                    int dy = e.getY() - initialClick.y;
                    selectedRoom.rectangle.setLocation(
                            selectedRoom.rectangle.x + dx,
                            selectedRoom.rectangle.y + dy
                    );
                    initialClick = e.getPoint();
                    repaint();
                }

//new code block added
                if (selectedRoom != null) {
                    Point snappedPoint = snapToGrid(e.getPoint());

                   // Calculate new position with grid snapping
                    int newX = snappedPoint.x - offset.x;
                    int newY = snappedPoint.y - offset.y;

                    // Temporarily update the room's position
                    selectedRoom.rectangle.setLocation(newX, newY);

                    // Repaint the panel to show the updated position
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
                if (drawingEnabled) {
                    endPoint = snapToGrid(e.getPoint());
                    if (addRoom()) {
                        System.out.println("Room added successfully!");
                    } else {
                        System.out.println("Room overlaps with an existing room!");
                    }
                    startPoint = null;
                    endPoint = null;
                    drawingEnabled = false; // Disable drawing until re-enabled
                    repaint();
                } else {
                    // Reset dragging variables
                    selectedRoom = null;
                    initialClick = null;
                }

//new added code
                if (selectedRoom != null) {
                    // Snap the rectangle to grid position on release
                    Point snappedPoint = snapToGrid(new Point(
                            selectedRoom.rectangle.x,
                            selectedRoom.rectangle.y
                    ));
                    selectedRoom.rectangle.setLocation(snappedPoint);

                    // Check for overlap after releasing the mouse
                    if (isOverlapping2(selectedRoom.rectangle)) {
                        // Revert to original position on overlap
                        selectedRoom.rectangle.setLocation(originalPosition);

                        // Show warning dialog
                        JOptionPane.showMessageDialog(
                                CanvasPanel.this,
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

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }


    private Point snapToGrid(Point p) {
        int x = (p.x / GRID_SIZE) * GRID_SIZE;
        int y = (p.y / GRID_SIZE) * GRID_SIZE;
        return new Point(x, y);
    }

    public void setDrawingEnabled(boolean enabled) {
        this.drawingEnabled = enabled;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public ArrayList<Rooms> getRooms() {
        return rooms;
    }

    private boolean addRoom() {
        if (startPoint != null && endPoint != null) {
            int x = Math.min(startPoint.x, endPoint.x);
            int y = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(startPoint.x - endPoint.x);
            int height = Math.abs(startPoint.y - endPoint.y);
            Rectangle newRectangle = new Rectangle(x, y, width, height);

            if (isOverlapping(newRectangle)) {
                JOptionPane.showMessageDialog(this,
                        "The new room overlaps with an existing room!",
                        "Overlap Detected",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                Color color = getColorByButtonName(buttonName);
                rooms.add(new Rooms(newRectangle, color));
                return true;
            }
        }
        return false;
    }


    public boolean isOverlapping(Rectangle newRectangle) {
        for (Rooms room : rooms) {
            Rectangle existingRectangle = room.rectangle;
            // Check for any intersection or containment
            if (existingRectangle.intersects(newRectangle) ||
                    existingRectangle.contains(newRectangle) ||
                    newRectangle.contains(existingRectangle)) {
                return true;
            }
        }
        return false;
    }

//new code added
    private boolean isOverlapping2(Rectangle movingRect) {
        for (Rectangle existingRect : getRoomRectangles()) {
            if (!movingRect.equals(existingRect) && movingRect.intersects(existingRect)) {
                return true;
            }
        }
        return false;
    }

//newcodeblock 
    private ArrayList<Rectangle> getRoomRectangles() {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        for (Rooms room : rooms) {
            rectangles.add(room.rectangle);
        }
        return rectangles;
    }

    Color getColorByButtonName(String buttonName) {
        if (buttonName.equalsIgnoreCase("Bedroom")) {
            return Color.GREEN;
        } else if (buttonName.equalsIgnoreCase("Bathroom")) {
            return Color.BLUE;
        } else if (buttonName.equalsIgnoreCase("Living Room")) {
            return Color.YELLOW;
        } else if (buttonName.equalsIgnoreCase("Kitchen")) {
            return Color.RED;
        } else if (buttonName.equalsIgnoreCase("Balcony")) {
            return new Color(120, 200, 120); // Purple for Balcony
        } else {
            return Color.GRAY; // Default color
        }
    }
    public void setRooms(ArrayList<Rooms> newRooms) {
        this.rooms.clear(); // Clear the existing rooms
        this.rooms.addAll(newRooms); // Add all the loaded rooms
        repaint(); // Refresh the canvas to show updated rooms
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grid
        g.setColor(Color.GRAY);
        int gridSize = 20;
        for (int x = 0; x < this.getWidth(); x += gridSize) {
            for (int y = 0; y < this.getHeight(); y += gridSize) {
                g.drawRect(x, y, gridSize, gridSize);
            }
        }

        // Draw all stored rooms
        Graphics2D g2d = (Graphics2D) g;
        for (Rooms room : rooms) {
            g2d.setColor(room.color);
            g2d.fillRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
        }

        // Draw the current rectangle being dragged
        if (startPoint != null && endPoint != null) {
            int x = Math.min(startPoint.x, endPoint.x);
            int y = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(startPoint.x - endPoint.x);
            int height = Math.abs(startPoint.y - endPoint.y);
            g2d.setColor(Color.BLUE);
            g2d.drawRect(x, y, width, height);
        }
    }
}
