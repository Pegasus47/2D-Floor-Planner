import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CanvasPanel extends JPanel {
    private Point startPoint;
    private Point endPoint;
    private boolean drawingEnabled = false; // Flag to enable drawing mode
    private String buttonName = ""; // Stores the room type
    private ArrayList<Rooms> rooms = new ArrayList<>();
    private Rooms selectedRoom = null; // The room being dragged
    private Point initialClick = null;
    private Point originalPosition = null; // Original position of the dragged room
    private Point offset; // Offset for snapping
    protected static final int GRID_SIZE = 20; // Grid size for snapping
    private final JPopupMenu popupMenu;
    private FurnitureFixture itemToAdd;  // The item to be dragged
    private Point currentDragPoint;      // Track the drag point
    // Furniture-related variables
    //private ArrayList<Furniture> furnitureList = new ArrayList<>();
    private FurnitureFixture selectedFurniture = null;
private boolean furniturePlacementEnabled = false; // Enable/disable placement mode
private List<FurnitureFixture> furnitureList = new ArrayList<>(); // List of placed furniture

    public CanvasPanel() {
        this.setBackground(new Color(40, 40, 40));
        this.setLayout(null);

        popupMenu = new JPopupMenu();
        JMenuItem rotateMenuItem = new JMenuItem("Rotate");
        JMenuItem deleteMenuItem = new JMenuItem("Delete");

        // Add action listeners for menu items
        rotateMenuItem.addActionListener(e -> rotate());
        deleteMenuItem.addActionListener(e -> deleteRoom());

        // Add items to the popup menu
        popupMenu.add(rotateMenuItem);
        popupMenu.add(deleteMenuItem);

        // MouseAdapter for handling user interactions
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
public void mousePressed(MouseEvent e) {
    if (drawingEnabled) {
        // Snap the start point to the grid
        startPoint = snapToGrid(e.getPoint());
    } else if (SwingUtilities.isRightMouseButton(e)) {
        // Right-click for furniture actions
        for (FurnitureFixture furniture : furnitureList) {
            if (furniture.contains(e.getPoint())) {
                selectedFurniture = furniture;

                // Show popup menu for actions
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem rotateItem = new JMenuItem("Rotate");
                JMenuItem deleteItem = new JMenuItem("Delete");

                rotateItem.addActionListener(event -> {
                    selectedFurniture.rotateFurniture();
                    repaint();
                });

                deleteItem.addActionListener(event -> {
                    furnitureList.remove(selectedFurniture);
                    selectedFurniture = null;
                    repaint();
                });

                popupMenu.add(rotateItem);
                popupMenu.add(deleteItem);
                popupMenu.show(CanvasPanel.this, e.getX(), e.getY());
                return;
            }
        }
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

        // Check if clicking on furniture for dragging
        for (FurnitureFixture furniture : furnitureList) {
            if (furniture.contains(e.getPoint())) {
                selectedFurniture = furniture;
                return;
            }
        }

        selectedRoom = null;
        selectedFurniture = null;
        originalPosition = null;
    }
    if (e.isPopupTrigger()) {
        // For right-click popup
        showPopup(e);
    } else {
        selectRoom(e.getPoint());
    }
    if (itemToAdd != null) {
        currentDragPoint = e.getPoint();
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
        
                    // Check for overlap between rooms during dragging
                    if (isOverlappingWithOtherRooms(selectedRoom.rectangle)) {
                        // Optionally show some indication of overlap, if needed
                    }
        
                    repaint();
                } else if (selectedFurniture != null) {
                    // Update furniture position while dragging (no room overlap check)
                    int newX = e.getX() - selectedFurniture.getWidth() / 2;
                    int newY = e.getY() - selectedFurniture.getHeight() / 2;
                    selectedFurniture.setPosition(newX, newY);
        
                    // Check for overlap between furniture during dragging
                    if (isOverlappingWithOtherFurniture(selectedFurniture)) {
                        // Optionally show some indication of overlap, if needed
                    }
        
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
            
                    // Check for overlap with other rooms
                    if (isOverlappingWithOtherRooms(selectedRoom.rectangle)) {
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
            
                // New code block for furniture placement logic
                if (e.isPopupTrigger()) {
                    // Handle right-click popup
                    showPopup(e);
                }
            
                repaint();
            
                if (itemToAdd != null && currentDragPoint != null) {
                    int x = e.getX() - itemToAdd.getWidth() / 2;
                    int y = e.getY() - itemToAdd.getHeight() / 2;
            
                    // Add the item to the canvas (furniture only)
                    addItemToCanvas(x, y);
            
                    // After placing the furniture, reset variables
                    itemToAdd = null; 
                    currentDragPoint = null; 
                    repaint();
                }
            }
            


// Helper method to check overlap between rooms only
private boolean isOverlappingWithOtherRooms(Rectangle rect) {
    for (Rooms room : rooms) {
        if (!room.equals(selectedRoom) && room.rectangle.intersects(rect)) {
            return true; // Room overlap detected
        }
    }
    return false; // No room overlap detected
}

// Helper method to check overlap between furniture only
private boolean isOverlappingWithOtherFurniture(FurnitureFixture furniture) {
    Rectangle furnitureBounds = new Rectangle(furniture.getPosition().x, furniture.getPosition().y, furniture.getWidth(), furniture.getHeight());

    for (FurnitureFixture otherFurniture : furnitureList) {
        if (!otherFurniture.equals(furniture)) {
            // Check if the two furniture items overlap
            Rectangle otherFurnitureBounds = new Rectangle(otherFurniture.getPosition().x, otherFurniture.getPosition().y, otherFurniture.getWidth(), otherFurniture.getHeight());
            if (furnitureBounds.intersects(otherFurnitureBounds)) {
                return true; // Furniture overlap detected
            }
        }
    }
    return false; // No furniture overlap detected
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
    //ro be commented

    //public void setItemToAdd(FurnitureFixture item) {
    //    this.itemToAdd = item;
    //}
    private void addItemToCanvas(int x, int y) {
        FurnitureFixture item = itemToAdd;
        itemToAdd = null; // Reset the current item to be added
        Graphics g = getGraphics();
        g.setColor(item.getColor());
        g.fillRect(x, y, item.getWidth(), item.getHeight());
    }

    private void selectRoom(Point point) 
    {
        selectedRoom = null; // Clear selection
        for (Rooms room : rooms) 
        {
            if (room.rectangle.contains(point)) 
            {
                selectedRoom = room;
                break;
            }
        }
    }

    private void showPopup(MouseEvent e) 
    {
        selectRoom(e.getPoint());
        if (selectedRoom != null) 
        {
            popupMenu.show(this, e.getX(), e.getY());
        }
    }

    private void rotate() {
        if (selectedRoom != null) {
            // Save the current dimensions before rotation
            int originalWidth = selectedRoom.rectangle.width;
            int originalHeight = selectedRoom.rectangle.height;
    
            // Perform the rotation
            selectedRoom.rotate();
    
            // Check for overlap after rotation
            if (isOverlapping(selectedRoom.rectangle)) {
                // Revert to original dimensions on overlap
                selectedRoom.rectangle.setSize(originalWidth, originalHeight);
    
                // Show warning dialog
                JOptionPane.showMessageDialog(
                    this,
                    "Room overlaps with an existing room after rotation! Reverting to the original orientation.",
                    "Overlap Detected",
                    JOptionPane.WARNING_MESSAGE
                );
            }
            repaint();
        }
    }
    private void deleteRoom() 
    {
        if (selectedRoom != null) 
        {
            rooms.remove(selectedRoom);
            selectedRoom = null;
            repaint();
        }
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

        // Draw all furniture
        for (FurnitureFixture furniture : furnitureList) {
            furniture.draw(g2d);
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
