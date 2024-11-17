import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddRoomButton extends JPanel {
    ImageIcon rightArrow;
    ImageIcon downArrow;
    CustomButton addRoom;
    JPanel buttonPanel;
    boolean isDropdownOpen = false;

    public AddRoomButton() {
        setBackground(new Color(255, 194, 74)); // Panel background color
        setLayout(null); // Use null for precise control over positioning

        // Load icons
        rightArrow = new ImageIcon("right-arrow.png");
        downArrow = new ImageIcon("down-arrow.png");

        // "Add Room" button
        addRoom = new CustomButton("Add Room", rightArrow);
        addRoom.setFont(new Font("Arial", Font.PLAIN, 16));
        addRoom.setBounds(10, 20, 150, 30); // Position of the "Add Room" button
        addRoom.setHorizontalTextPosition(SwingConstants.LEFT);
        addRoom.setIconTextGap(10);

        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleRoomOptions();
            }
        });

        // Panel for dropdown buttons (initially hidden)
        int numRooms = 5; // Number of room buttons
        int heightButton = 40; // Height for each room button
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Absolute layout for pixel control
        buttonPanel.setBounds(10, 60, 200, 0); // Initially collapsed (height = 0)
        buttonPanel.setBackground(new Color(255, 194, 74)); // Same as main background

        // Add components to the main panel
        add(addRoom);
        add(buttonPanel);
    }

    private void toggleRoomOptions() {
        isDropdownOpen = !isDropdownOpen;

        buttonPanel.removeAll(); // Clear existing buttons
        if (isDropdownOpen) {
            // Update "Add Room" button to show dropdown is open
            addRoom.setText("Add Room");
            ImageIcon scaledIcon = scaleIcon(downArrow, 16, 16); // Use scaled-down arrow icon
            addRoom.setIcon(scaledIcon);

            // Add room buttons using your logic
            CustomButton bedroom = new CustomButton("Bedroom");
            bedroom.setBounds(20, 0, 150, 30); // (x, y, width, height)
            buttonPanel.add(bedroom);

            CustomButton bathroom = new CustomButton("Bathroom");
            bathroom.setBounds(20, 40, 150, 30); // Offset vertically
            buttonPanel.add(bathroom);

            CustomButton livingRoom = new CustomButton("Living Room");
            livingRoom.setBounds(20, 80, 150, 30); // Further offset
            buttonPanel.add(livingRoom);

            CustomButton kitchen = new CustomButton("Kitchen");
            kitchen.setBounds(20, 120, 150, 30); // Further offset
            buttonPanel.add(kitchen);

            CustomButton balcony = new CustomButton("Balcony");
            balcony.setBounds(20, 160, 150, 30); // Further offset
            buttonPanel.add(balcony);

            // Adjust dropdown panel size
            buttonPanel.setBounds(10, 60, 250, 200); // Dynamically adjust height
        } else {
            // Update "Add Room" button to show dropdown is closed
            addRoom.setText("Add Room");
            ImageIcon scaledIcon = scaleIcon(rightArrow, 16, 16); // Use scaled right arrow icon
            addRoom.setIcon(scaledIcon);

            // Collapse dropdown
            buttonPanel.setBounds(10, 60, 200, 0);
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    // Utility method to scale icons
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
