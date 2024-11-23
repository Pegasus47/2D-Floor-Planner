import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainControlPanel extends JPanel {
    ImageIcon rightArrow;
    ImageIcon downArrow;
    ImageIcon bedroomColor;
    ImageIcon bathroomColor;
    ImageIcon livingColor;
    ImageIcon kitchenColor;
    ImageIcon balconyColor;
    ImageIcon wallIcon, windowIcon, doorIcon;
    CustomButton addRoom;
    JPanel buttonPanel;
    boolean isDropdownOpen = false;
    CustomButton addWall;
    CustomButton placeDoor;
    CustomButton placeWindow;
    CustomButton formatting;
    CanvasPanel canvasPanel;
    ImageIcon bedIcon, bookshelfIcon, closetIcon, diningTableIcon, fridgeIcon, kitchenTableIcon, deskIcon, sofaIcon, tableIcon, chairIcon, sinkIcon, toiletIcon;
    CustomButton2 bed, bookshelf, closet, diningTable, fridge, kitchenTable, desk, sofa, table, chair, sink, toilet;


    public MainControlPanel(CanvasPanel canvas) {
        this.canvasPanel = canvas;
        setBackground(new Color(255, 194, 74));
        setPreferredSize(new Dimension(210, 700));
        setOpaque(false);
        setLayout(null);

        // //save all the images to be used
        // rightArrow = new ImageIcon("right-arrow.png");
        // downArrow = new ImageIcon("down-arrow.png");
        // bedroomColor = new ImageIcon("green.png");
        // bathroomColor = new ImageIcon("blue.png");
        // livingColor = new ImageIcon("yellow.png");
        // kitchenColor = new ImageIcon("red.png");
        // balconyColor = new ImageIcon("pretty.png");
        // wallIcon = new ImageIcon("wall2.png");
        // doorIcon = new ImageIcon("door.png");
        // windowIcon = new ImageIcon("windows.png");
        // bedIcon = new ImageIcon("bed.png");
        // bookshelfIcon = new ImageIcon("bookshelf.png");
        // closetIcon = new ImageIcon("closet.png");
        // diningTableIcon = new ImageIcon("dining_table.png");
        // fridgeIcon = new ImageIcon("fridge.png");
        // kitchenTableIcon = new ImageIcon("kitchen.png");
        // deskIcon = new ImageIcon("office-desk.png");
        // sofaIcon = new ImageIcon("sofa.png");
        // tableIcon = new ImageIcon("table.png");
        // chairIcon = new ImageIcon("chair.png");

            // Scale all icons once during initialization
            rightArrow = scaleIcon(new ImageIcon("right-arrow.png"), 16, 16);
            downArrow = scaleIcon(new ImageIcon("down-arrow.png"), 16, 16);
            bedroomColor = scaleIcon(new ImageIcon("green.png"), 16, 16);
            bathroomColor = scaleIcon(new ImageIcon("blue.png"), 16, 16);
            livingColor = scaleIcon(new ImageIcon("yellow.png"), 16, 16);
            kitchenColor = scaleIcon(new ImageIcon("red.png"), 16, 16);
            balconyColor = scaleIcon(new ImageIcon("pretty.png"), 16, 16);
            wallIcon = scaleIcon(new ImageIcon("wall2.png"), 16, 16);
            doorIcon = scaleIcon(new ImageIcon("door.png"), 16, 16);
            windowIcon = scaleIcon(new ImageIcon("windows.png"), 16, 16);
            sinkIcon = scaleIcon(new ImageIcon("sink.png"), 16, 16);
            toiletIcon = scaleIcon(new ImageIcon("toilet.png"), 16, 16);

            // Pre-scale object icons
            bedIcon = scaleIcon(new ImageIcon("bed.png"), 30, 30);
            bookshelfIcon = scaleIcon(new ImageIcon("bookshelf.png"), 30, 30);
            closetIcon = scaleIcon(new ImageIcon("closet.png"), 30, 30);
            diningTableIcon = scaleIcon(new ImageIcon("dining_table.png"), 30, 30);
            fridgeIcon = scaleIcon(new ImageIcon("fridge.png"), 30, 30);
            kitchenTableIcon = scaleIcon(new ImageIcon("kitchen.png"), 30, 30);
            deskIcon = scaleIcon(new ImageIcon("office-desk.png"), 30, 30);
            sofaIcon = scaleIcon(new ImageIcon("sofa.png"), 30, 30);
            tableIcon = scaleIcon(new ImageIcon("table.png"), 30, 30);
            chairIcon = scaleIcon(new ImageIcon("chair.png"), 30, 30);
            sinkIcon = scaleIcon(new ImageIcon("sink.png"), 30, 30);
            toiletIcon = scaleIcon(new ImageIcon("toilet.png"), 30, 30);
        
        addWall = new CustomButton("Add Wall", scaleIcon(wallIcon, 16, 16));
        addWall.setFont(new Font("Arial", Font.PLAIN, 16));
        addWall.setBounds(10, 30, 170, 40);
        addWall.setHorizontalTextPosition(SwingConstants.LEFT);
        addWall.setIconTextGap(10);
        addWall.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //write code for this
            }
        }));

        placeDoor = new CustomButton("Place Door", scaleIcon(doorIcon, 16, 16));
        placeDoor.setFont(new Font("Arial", Font.PLAIN, 16));
        placeDoor.setBounds(10, 85, 170, 40);
        placeDoor.setHorizontalTextPosition(SwingConstants.LEFT);
        placeDoor.setIconTextGap(10);
        placeDoor.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //write code for this
            }
        }));

        placeWindow = new CustomButton("Place Window", scaleIcon(windowIcon, 16, 16));
        placeWindow.setFont(new Font("Arial", Font.PLAIN, 16));
        placeWindow.setBounds(10, 140, 170, 40);
        placeWindow.setHorizontalTextPosition(SwingConstants.LEFT);
        placeWindow.setIconTextGap(10);
        placeWindow.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //write code for this
            }
        }));

        addRoom = new CustomButton("Add Room", rightArrow);
        addRoom.setFont(new Font("Arial", Font.PLAIN, 16));
        addRoom.setBounds(10, 195, 170, 40);
        addRoom.setHorizontalTextPosition(SwingConstants.LEFT);
        addRoom.setIconTextGap(10);
        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleRoomOptions();
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(10, 250, 200, 0);
        buttonPanel.setBackground(new Color(255, 194, 74));

        add(addWall);
        add(placeDoor);
        add(placeWindow);
        add(addRoom);
        add(buttonPanel);
    }

    private void toggleRoomOptions() {
        isDropdownOpen = !isDropdownOpen;

        buttonPanel.removeAll();
        if (isDropdownOpen) {
            addRoom.setText("Add Room");
            ImageIcon scaledIcon = scaleIcon(downArrow, 16, 16);
            addRoom.setIcon(scaledIcon);

            CustomButton bedroom = new CustomButton("Bedroom", scaleIcon(bedroomColor, 16, 16)); // Rescale icon
            bedroom.setBounds(10, 0, 150, 30);
            bedroom.setHorizontalTextPosition(SwingConstants.LEFT);
            bedroom.setIconTextGap(10);
            buttonPanel.add(bedroom);
            bedroom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvasPanel.setButtonName("Bedroom");
                    canvasPanel.setDrawingEnabled(true);
                }
            });

            CustomButton bathroom = new CustomButton("Bathroom", scaleIcon(bathroomColor, 16, 16));
            bathroom.setBounds(10, 40, 150, 30);
            bathroom.setHorizontalTextPosition(SwingConstants.LEFT);
            bathroom.setIconTextGap(10);
            buttonPanel.add(bathroom);
            bathroom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvasPanel.setButtonName("Bathroom");
                    canvasPanel.setDrawingEnabled(true);
                }
            });

            CustomButton livingRoom = new CustomButton("Living Room", scaleIcon(livingColor, 16, 16));
            livingRoom.setBounds(10, 80, 150, 30);
            livingRoom.setHorizontalTextPosition(SwingConstants.LEFT);
            livingRoom.setIconTextGap(10);
            buttonPanel.add(livingRoom);
            livingRoom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvasPanel.setButtonName("Living Room");
                    canvasPanel.setDrawingEnabled(true);
                }
            });

            CustomButton kitchen = new CustomButton("Kitchen", scaleIcon(kitchenColor, 16, 16));
            kitchen.setBounds(10, 120, 150, 30);
            kitchen.setHorizontalTextPosition(SwingConstants.LEFT);
            kitchen.setIconTextGap(10);
            buttonPanel.add(kitchen);
            kitchen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvasPanel.setDrawingEnabled(true);
                    canvasPanel.setButtonName("Kitchen");
                }
            });

            CustomButton balcony = new CustomButton("Balcony", scaleIcon(balconyColor, 16, 16));
            balcony.setBounds(10, 160, 150, 30);
            balcony.setHorizontalTextPosition(SwingConstants.LEFT);
            balcony.setIconTextGap(10);
            buttonPanel.add(balcony);
            balcony.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvasPanel.setButtonName("Balcony");
                    canvasPanel.setDrawingEnabled(true);
                }
            });

            // Adjust dropdown panel size
            buttonPanel.setBounds(10, 250, 200, 200); // Dynamically adjust height
        } else {
            // Update "Add Room" button to show dropdown is closed
            addRoom.setText("Add Room");
            ImageIcon scaledIcon = scaleIcon(rightArrow, 16, 16); // Use scaled right arrow icon
            addRoom.setIcon(scaledIcon);


            // Collapse dropdown
            buttonPanel.setBounds(10, 250, 200, 0);
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    // Utility method to scale icons
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // public void showObjectsGrid() {
    //     removeAll(); // Clear the current panel content
    
    //     // Create a panel for the objects grid
    //     JPanel objectsPanel = new JPanel(new CardLayout()); // 3 rows, 2 columns
    //     objectsPanel.setBounds(10, 30, 190, 250); // Adjust size and position
    //     objectsPanel.setBackground(new Color(255, 194, 74));
    //     objectsPanel.setOpaque(true);

        
    //     bed = new CustomButton2("Bed", scaleIcon(bedIcon, 15, 15)); // Rescale icon
    //     bookshelf = new CustomButton2("Bookshelf", scaleIcon(bookshelfIcon, 30, 30));
    //     closet = new CustomButton2("Closet", scaleIcon(closetIcon, 30, 30));
    //     diningTable = new CustomButton2("Dining Table", scaleIcon(diningTableIcon, 30, 30));
    //     fridge = new CustomButton2("Fridge", scaleIcon(fridgeIcon, 30, 30));
    //     kitchenTable = new CustomButton2("Kitchen Table", scaleIcon(kitchenTableIcon, 30, 30));
    //     desk = new CustomButton2("Desk", scaleIcon(deskIcon, 30, 30));
    //     sofa = new CustomButton2("Sofa", scaleIcon(sofaIcon, 30, 30));
    //     table = new CustomButton2("Table", scaleIcon(tableIcon, 30, 30));
    //     chair = new CustomButton2("Chair", scaleIcon(chairIcon, 30, 30));
        
    //     /* 
    //     bed = new CustomButton("Bed"); // Rescale icon
    //     bookshelf = new CustomButton("Bookshelf");
    //     closet = new CustomButton("Closet");
    //     diningTable = new CustomButton("Dining Table");
    //     fridge = new CustomButton("Fridge");
    //     kitchenTable = new CustomButton("Kitchen Table");
    //     desk = new CustomButton("Desk");
    //     sofa = new CustomButton("Sofa");
    //     table = new CustomButton("Table");
    //     chair = new CustomButton("Chair"); */
    //     objectsPanel.add(bed);
    //     objectsPanel.add(bookshelf);
    //     objectsPanel.add(closet);
    //     objectsPanel.add(diningTable);
    //     objectsPanel.add(fridge);
    //     objectsPanel.add(kitchenTable);
    //     objectsPanel.add(desk);
    //     objectsPanel.add(sofa);
    //     objectsPanel.add(table);
    //     objectsPanel.add(chair);
    //     add(objectsPanel);
    // }

    public void resetToMainView() {
        removeAll(); // Clear the panel
    
        // Re-add the main buttons
        add(addWall);
        add(placeDoor);
        add(placeWindow);
        add(addRoom);
        add(buttonPanel);
    
        revalidate(); // Refresh the panel
        repaint();
    }

    public void showObjectsGrid() {
        removeAll(); // Clear the current panel content
    
        // Create a panel for the objects grid
        JPanel objectsPanel = new JPanel(new GridLayout(6, 2, 5, 5)); // 5 rows, 2 columns
        objectsPanel.setBounds(10, 30, 190, 500); // Adjust size and position
        objectsPanel.setBackground(new Color(255, 194, 74));
    
        // Add buttons with pre-scaled icons
        bed = new CustomButton2("Bed", bedIcon);
        bookshelf = new CustomButton2("Bookshelf", bookshelfIcon);
        closet = new CustomButton2("Closet", closetIcon);
        diningTable = new CustomButton2("Dining Table", diningTableIcon);
        fridge = new CustomButton2("Fridge", fridgeIcon);
        kitchenTable = new CustomButton2("Kitchen Table", kitchenTableIcon);
        desk = new CustomButton2("Desk", deskIcon);
        sofa = new CustomButton2("Sofa", sofaIcon);
        table = new CustomButton2("Table", tableIcon);
        chair = new CustomButton2("Chair", chairIcon);
        toilet = new CustomButton2("Toilet", toiletIcon);
        sink = new CustomButton2("Sink", sinkIcon);
    
        // Add components to the grid
        objectsPanel.add(bed);
        objectsPanel.add(bookshelf);
        objectsPanel.add(closet);
        objectsPanel.add(diningTable);
        objectsPanel.add(fridge);
        objectsPanel.add(kitchenTable);
        objectsPanel.add(desk);
        objectsPanel.add(sofa);
        objectsPanel.add(table);
        objectsPanel.add(chair);
        objectsPanel.add(toilet);
        objectsPanel.add(sink);
    
        add(objectsPanel);
        revalidate(); // Refresh the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass method
        super.paintComponent(g);

        // Cast to Graphics2D for more control
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smooth rounded edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color and draw a rounded rectangle
        g2d.setColor(new Color(255, 194, 74));
        int arc = 30; // The arc size for rounded corners
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Dispose of the Graphics object to free resources
        g2d.dispose();
    }
}

