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


    public MainControlPanel() {
        setBackground(new Color(255, 194, 74)); 
        setPreferredSize(new Dimension(210, 700));
        setOpaque(false); 
        setLayout(null);

        //save all the images to be used
        rightArrow = new ImageIcon("right-arrow.png");
        downArrow = new ImageIcon("down-arrow.png");
        bedroomColor = new ImageIcon("green.png");
        bathroomColor = new ImageIcon("blue.png");
        livingColor = new ImageIcon("yellow.png");
        kitchenColor = new ImageIcon("red.png");
        balconyColor = new ImageIcon("pretty.png");
        wallIcon = new ImageIcon("wall2.png");
        doorIcon = new ImageIcon("door.png");
        windowIcon = new ImageIcon("windows.png");

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

            CustomButton bathroom = new CustomButton("Bathroom", scaleIcon(bathroomColor, 16, 16));
            bathroom.setBounds(10, 40, 150, 30);
            bathroom.setHorizontalTextPosition(SwingConstants.LEFT);
            bathroom.setIconTextGap(10);
            buttonPanel.add(bathroom);

            CustomButton livingRoom = new CustomButton("Living Room", scaleIcon(livingColor, 16, 16));
            livingRoom.setBounds(10, 80, 150, 30); 
            livingRoom.setHorizontalTextPosition(SwingConstants.LEFT);
            livingRoom.setIconTextGap(10);
            buttonPanel.add(livingRoom);

            CustomButton kitchen = new CustomButton("Kitchen", scaleIcon(kitchenColor, 16, 16));
            kitchen.setBounds(10, 120, 150, 30);
            kitchen.setHorizontalTextPosition(SwingConstants.LEFT);
            kitchen.setIconTextGap(10);
            buttonPanel.add(kitchen);

            CustomButton balcony = new CustomButton("Balcony", scaleIcon(balconyColor, 16, 16));
            balcony.setBounds(10, 160, 150, 30);
            balcony.setHorizontalTextPosition(SwingConstants.LEFT);
            balcony.setIconTextGap(10);
            buttonPanel.add(balcony);

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
