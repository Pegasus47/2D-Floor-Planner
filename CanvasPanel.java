import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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
    private String currentObjectName = "";
    private Point objectPosition = new Point(0, 0);
    public String selectedItem = "";
    private boolean itemPlacement = false;
    private JLabel draggedObject = null; // The currently dragged object
    private Point dragOffset = null;     // Offset of the mouse position relative to the object



    // Furniture-related variables
    private ArrayList<Furniture> furnitureList = new ArrayList<>();
    private Furniture selectedFurniture = null;

    public void setSelectedItem(String itemName) {
        selectedItem = itemName;
    }
    
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
    // If drawing is enabled, snap start point to grid
    if (drawingEnabled) {
        startPoint = snapToGrid(e.getPoint());
        return; // Drawing mode takes precedence
    }
    
    // Check if right mouse button is clicked for popup menu
    if (SwingUtilities.isRightMouseButton(e)) {
        for (Furniture furniture : furnitureList) {
            if (furniture.contains(e.getPoint())) {
                selectedFurniture = furniture;

                // Show popup menu for furniture actions
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
        // Handle dragging logic for rooms or furniture
        Point snappedPoint = snapToGrid(e.getPoint());
        for (Rooms room : rooms) {
            if (room.rectangle.contains(snappedPoint)) {
                selectedRoom = room;
                originalPosition = new Point(room.rectangle.x, room.rectangle.y);
                offset = new Point(snappedPoint.x - room.rectangle.x, snappedPoint.y - room.rectangle.y);
                return;
            }
        }
        for (Furniture furniture : furnitureList) {
            if (furniture.contains(e.getPoint())) {
                selectedFurniture = furniture;
                return;
            }
        }
    }

    // Check if clicking on a JLabel for dragging
    Component component = getComponentAt(e.getPoint());
    if (component instanceof JLabel) {
        draggedObject = (JLabel) component; // Set the object being dragged
        dragOffset = new Point(e.getX() - draggedObject.getX(), e.getY() - draggedObject.getY());
        System.out.println("Dragging JLabel: " + draggedObject.getText());
    } else {
        System.out.println("Not a JLabel, component type: " + component.getClass().getName());
    }

    // Clear selections if no valid target
    selectedRoom = null;
    selectedFurniture = null;
    originalPosition = null;

    // Handle popup trigger if right-click
    if (e.isPopupTrigger()) {
        showPopup(e);
    } else {
        selectRoom(e.getPoint());
    }

    // Handle adding items if applicable
    if (itemToAdd != null) {
        currentDragPoint = e.getPoint();
    }
}


            @Override
            public void mouseClicked(MouseEvent e) {
                if (itemPlacement) {
                    System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
                    // Load the image based on selected item
                    String imagePath = "";
                    switch (selectedItem) {
                        case "Bed":
                            imagePath = "bed.png"; // Replace with actual image path
                            break;
                        case "Bookshelf":
                            imagePath = "bookshelf.png"; 
                            break;
                        case "Closet":
                            imagePath = "closet.png"; // Replace with actual image path
                            break;
                        case "Dining Table":
                            imagePath = "dining_table.png"; // Replace with actual image path
                            break;
                        case "Fridge":
                            imagePath = "fridge.png"; // Replace with actual image path
                            break;
                        case "Kitchen Table":
                            imagePath = "kitchen.png"; // Replace with actual image path
                            break;
                        case "Desk":
                            imagePath = "desk.png"; // Replace with actual image path
                            break;
                        case "Sofa":
                            imagePath = "sofa.png"; // Replace with actual image path
                            break;
                        case "Table":
                            imagePath = "table.png"; // Replace with actual image path
                            break;
                        case "Chair":
                            imagePath = "chair.png"; // Replace with actual image path
                            break;
                        case "Toilet":
                            imagePath = "toilet.png"; // Replace with actual image path
                            break;
                        case "Sink":
                            imagePath = "sink.png"; // Replace with actual image path
                            break;
                        default:
                            imagePath = null; // Default image
                            break;
                    }
        
                    // Create an ImageIcon from the selected image path
                    ImageIcon itemIcon = new ImageIcon(imagePath);
                    Image image = itemIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(image);
        
                    // Insert image as JLabel at the mouse position
                    JLabel imageLabel = new JLabel(resizedIcon);
                    imageLabel.setBounds(e.getX(), e.getY(), 50,50);  // Adjust size as needed
                    imageLabel.setLocation(e.getX(), e.getY());
                    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    CanvasPanel.this.add(imageLabel);
                    CanvasPanel.this.revalidate();
                    CanvasPanel.this.repaint();
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
                // } else if (selectedFurniture != null) {
                //     // Update furniture position while dragging
                //     int newX = e.getX() - selectedFurniture.getWidth() / 2;
                //     int newY = e.getY() - selectedFurniture.getHeight() / 2;
                //     selectedFurniture.setPosition(newX, newY);
                //     repaint();
                }
                else if (selectedFurniture != null) {
                    // Update furniture position while dragging
                    int newX = e.getX() - selectedFurniture.getWidth() / 2;
                    int newY = e.getY() - selectedFurniture.getHeight() / 2;
                    selectedFurniture.setPosition(newX, newY);
        
                    //snap
                    Point snappedPoint = snapToGrid(new Point(newX, newY));
                    selectedFurniture.setPosition(snappedPoint.x , snappedPoint.y);
        
                    repaint();
                }
                else if (itemPlacement && draggedObject != null) {
                    System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
                    moveObject(e.getPoint());
                }
            }

            private void moveObject(Point mousePoint) {
                if (draggedObject != null && dragOffset != null) {
                    // Calculate the new position by applying the offset
                    int newX = mousePoint.x - dragOffset.x;
                    int newY = mousePoint.y - dragOffset.y;
            
                    // Update the position of the dragged JLabel
                    draggedObject.setLocation(newX, newY);
            
                    // Repaint to reflect the changes
                    draggedObject.repaint();
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
                    draggedObject = null;
                    dragOffset = null;
                }
                
                //new code block
                if (e.isPopupTrigger()) 
                { 
                    // For some systems, popup is triggered on mouse release
                    showPopup(e);
                }
                repaint();
                if (itemToAdd != null && currentDragPoint != null) {
                    int x = e.getX() - itemToAdd.getSize().width / 2;
                    int y = e.getY() - itemToAdd.getSize().height / 2;

                    // Draw the furniture/fixture at the new position
                    addItemToCanvas(x, y);
                    itemToAdd = null; // Reset the item
                    currentDragPoint = null; // Reset drag point
                    repaint();
                }
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

    private void placeObjectOnCanvas(Point point) {
        // Create a JLabel with the image icon of the current selected object
        ImageIcon icon = getObjectIcon(currentObjectName);
        if (icon != null) {
            JLabel objectLabel = new JLabel(icon);
            objectLabel.setBounds(point.x, point.y, icon.getIconWidth(), icon.getIconHeight());
            objectLabel.setLocation(point);

            // Make the label draggable
            objectLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Store the initial click position
                    objectPosition.setLocation(e.getPoint());
                }
            });

            objectLabel.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    // Move the label while dragging
                    int dx = e.getX() - objectPosition.x;
                    int dy = e.getY() - objectPosition.y;
                    objectLabel.setLocation(objectLabel.getX() + dx, objectLabel.getY() + dy);
                    objectPosition.setLocation(e.getPoint());
                }
            });

            add(objectLabel);
            revalidate();
            repaint();
        }
    }

    // Get the corresponding icon for the object name (like "Bed")
    private ImageIcon getObjectIcon(String objectName) {
        switch (objectName) {
            case "Bed":
                return new ImageIcon("bed.png");
            case "Book Shelf":
                return new ImageIcon("bookshelf.png");
            // Add cases for other objects like "Bookshelf", "Sofa", etc.
            case "Closet":
                return new ImageIcon("closet.png"); 
            case "Dining Table":
                return new ImageIcon("dining_table.png"); 
            case "Fridge":
                return new ImageIcon("fridge.png"); 
            case "Kitchen Table":
                return new ImageIcon("kitchen.png"); 
            case "Desk":
                return new ImageIcon("desk.png"); 
            case "Sofa":
                return new ImageIcon("sofa.png"); 
            case "Table":
                return new ImageIcon("table.png"); 
            case "Chair":
                return new ImageIcon("chair.png"); 
            case "Toilet":
                return new ImageIcon("toilet.png"); 
            case "Sink":
                return new ImageIcon("sink.png"); 
            default:
                return null;
        }
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
        g.fillRect(x, y, item.getSize().width, item.getSize().height);
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

    public void setItemPlacement(boolean enabled){
        this.itemPlacement = enabled;
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
        for (Furniture furniture : furnitureList) {
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
