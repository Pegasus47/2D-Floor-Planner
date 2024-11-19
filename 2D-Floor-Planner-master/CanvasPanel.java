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

    public CanvasPanel() {
        this.setBackground(new Color(40, 40, 40));
        this.setLayout(null);

        // Mouse Adapter for room drawing and dragging
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (drawingEnabled) {
                    startPoint = e.getPoint();
                } else {
                    // Check if clicking inside a room for dragging
                    for (Rooms room : rooms) {
                        if (room.rectangle.contains(e.getPoint())) {
                            selectedRoom = room;
                            initialClick = e.getPoint();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawingEnabled) {
                    endPoint = e.getPoint();
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
            }

            @Override
            public void mouseDragged(MouseEvent e) {
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
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
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


    private boolean isOverlapping(Rectangle newRectangle) {
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
