import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionComponent {
    private JFrame frame;
    private JPanel buttonPanel;
    private JButton addRoomButton;
    private boolean isDropdownOpen = false;
    
    public OptionComponent() {
        frame = new JFrame("Add Room Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        
        // Create the "Add Room" button with an arrow
        addRoomButton = new JButton("Add Room >"); // Arrow initially pointing to the right
        addRoomButton.setFont(new Font("Arial", Font.PLAIN, 18));
        
        // Add action listener to toggle the dropdown
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleRoomOptions();
            }
        });
        
        // Create the panel that holds the additional buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // Add components to the frame
        frame.add(addRoomButton, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
    
    // Method to toggle the room options when "Add Room" is clicked
    private void toggleRoomOptions() {
        // Toggle the dropdown state
        isDropdownOpen = !isDropdownOpen;
        
        // Update the arrow direction based on dropdown state
        if (isDropdownOpen) {
            addRoomButton.setText(" ˅ Add Room");
            
            // Add new buttons for rooms
            buttonPanel.add(new JButton("Room 1"));
            buttonPanel.add(new JButton("Room 2"));
            buttonPanel.add(new JButton("Room 3"));
        } else {
            addRoomButton.setText("Add Room >");
            
            // Remove the room buttons
            buttonPanel.removeAll();
        }
        
        // Refresh the panel to show the newly added or removed buttons
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    public static void main(String[] args) {
        new OptionComponent();
    }
}
