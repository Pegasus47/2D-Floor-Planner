import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddWallButton extends JPanel {
    ImageIcon rightArrow;
    ImageIcon downArrow;
    CustomButton addWall;
    JPanel buttonPanel;
    boolean isDropdownOpen = false;

    public AddWallButton() {
        setBackground(new Color(255, 194, 74)); 
        setLayout(null); // Use null for precise control over positioning

        // Load icons
        rightArrow = new ImageIcon("right-arrow.png");
        downArrow = new ImageIcon("down-arrow.png");

        // "Add Room" button
        addWall = new CustomButton("Add Wall", rightArrow);
        addWall.setFont(new Font("Arial", Font.PLAIN, 16));
        addWall.setBounds(10, 20, 150, 30); // Position of the "Add Room" button
        addWall.setHorizontalTextPosition(SwingConstants.LEFT);
        addWall.setIconTextGap(10);

        addWall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleWallOptions();
            }
        });

        // Panel for dropdown buttons (initially hidden)
        int numWalls = 5; // Number of room buttons
        int heightButton = 40; // Height for each room button
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Absolute layout for pixel control
        buttonPanel.setBounds(10, 60, 200, 0); // Initially collapsed (height = 0)
        buttonPanel.setBackground(new Color(255, 194, 74)); // Same as main background

        // Add components to the main panel
        add(addWall);
        add(buttonPanel);
    }

    private void toggleWallOptions() {
        isDropdownOpen = !isDropdownOpen;

        buttonPanel.removeAll(); // Clear existing buttons
        if (isDropdownOpen) {
            // Update "Add Room" button to show dropdown is open
            addWall.setText("Add Room");
            ImageIcon scaledIcon = scaleIcon(downArrow, 16, 16); // Use scaled-down arrow icon
            addWall.setIcon(scaledIcon);

            // Add room buttons using your logic
            CustomButton wall = new CustomButton("replace with image");
            wall.setBounds(20, 0, 150, 30); // (x, y, width, height)
            buttonPanel.add(wall);

            // Adjust dropdown panel size
            buttonPanel.setBounds(10, 60, 200, 40); // Dynamically adjust height
        } else {
            // Update "Add Room" button to show dropdown is closed
            addWall.setText("Add Wall");
            ImageIcon scaledIcon = scaleIcon(rightArrow, 16, 16); // Use scaled right arrow icon
            addWall.setIcon(scaledIcon);

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
