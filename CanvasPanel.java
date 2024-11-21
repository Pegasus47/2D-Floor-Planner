import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class CanvasPanel extends JPanel {
    private Point startPoint;
    private Point endPoint;
    private boolean drawingEnabled = false; // Flag to enable drawing mode
    private String buttonName = ""; // Stores the room type
    private ArrayList<Rooms> rooms = new ArrayList<>();
    private Rooms selectedRoom = null; // The room being dragged
    private Point initialClick = null; // Point where dragging starts
    private Point originalPosition = null; // Original position of the dragged room
    private Point offset; // Offset for snapping
    protected static final int GRID_SIZE = 20; // Grid size for snapping

    public CanvasPanel() {
        this.setBackground(new Color(40, 40, 40));
        this.setLayout(null);

        // MouseAdapter for handling user interactions
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (drawingEnabled) {
                    // Snap the start point to the grid
                    startPoint = snapToGrid(e.getPoint());
                } else {
                    // Check if clicking inside an existing room for dragging
                    Point snappedPoint = snapToGrid(e.getPoint());
                    for (Rooms room : rooms) {
                        if (room.rectangle.contains(snappedPoint)) {
                            selectedRoom = room;
                            originalPosition = new Point(room.rectangle.x, room.rectangle.y);
                            offset = new Point(snappedPoint.x - room.rectangle.x, snappedPoint.y - room.rectangle.y);
                            return;
                        }
                    }
                    selectedRoom = null;
                    originalPosition = null;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawingEnabled) {
                    // Update endpoint as user drags for drawing a new room
                    endPoint = snapToGrid(e.getPoint());
                    repaint();
                } else if (selectedRoom != null) {
                    // Dragging logic with snapping to grid
                    Point snappedPoint = snapToGrid(e.getPoint());
                    int newX = snappedPoint.x - offset.x;
                    int newY = snappedPoint.y - offset.y;
                    selectedRoom.rectangle.setLocation(newX, newY);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawingEnabled) {
                    // Complete the room drawing
                    endPoint = snapToGrid(e.getPoint());
                    if (addRoom()) {
                        System.out.println("Room added successfully!");
                    } else {
                        System.out.println("Room overlaps with an existing room!");
                    }
                    startPoint = null;
                    endPoint = null;
                    drawingEnabled = false; // Disable drawing mode
                } else if (selectedRoom != null) {
                    // Snap the rectangle to grid position on release
                    Point snappedPoint = snapToGrid(new Point(
                            selectedRoom.rectangle.x,
                            selectedRoom.rectangle.y
                    ));
                    selectedRoom.rectangle.setLocation(snappedPoint);

                    // Check for overlap after releasing the mouse
                    if (isOverlapping(selectedRoom.rectangle)) {
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

                    selectedRoom = null;
                    originalPosition = null;
                }
                repaint();
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    private Point snapToGrid(Point p) {
        int x = Math.round((float) p.x / GRID_SIZE) * GRID_SIZE;
        int y = Math.round((float) p.y / GRID_SIZE) * GRID_SIZE;
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

    public void setRooms(ArrayList<Rooms> rooms) {
        this.rooms = rooms;
        repaint();
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
            if (room.rectangle != newRectangle && room.rectangle.intersects(newRectangle)) {
                Rectangle intersection = room.rectangle.intersection(newRectangle);
                if (intersection.width > 0 && intersection.height > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private Color getColorByButtonName(String buttonName) {
        return switch (buttonName.toLowerCase()) {
            case "bedroom" -> Color.GREEN;
            case "bathroom" -> Color.BLUE;
            case "living room" -> Color.YELLOW;
            case "kitchen" -> Color.RED;
            case "balcony" -> new Color(120, 200, 120);
            default -> Color.GRAY; // Default color
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        g.setColor(Color.GRAY);
        for (int x = 0; x < this.getWidth(); x += GRID_SIZE) {
            for (int y = 0; y < this.getHeight(); y += GRID_SIZE) {
                g.drawRect(x, y, GRID_SIZE, GRID_SIZE);
            }
        }

        // Draw all rooms
        Graphics2D g2d = (Graphics2D) g;
        for (Rooms room : rooms) {
            g2d.setColor(room.color);
            g2d.fillRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(room.rectangle.x, room.rectangle.y, room.rectangle.width, room.rectangle.height);
        }

        // Draw the room being drawn
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
