/*import javax.swing.*;
import java.awt.*;

public class DragAndDropApp {
    public static void main(String[] args) {
        // Create JFrame
        JFrame frame = new JFrame("Drag and Drop Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null); // Absolute layout for free placement of components

        // Create JPanel as the main container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // For absolute positioning of objects
        mainPanel.setBackground(new Color(220, 220, 220));
        mainPanel.setBounds(0, 0, 800, 600);

        // Create draggable components (e.g., labels)
        JLabel label1 = new JLabel("Drag Me 1");
        label1.setBounds(50, 50, 100, 50);
        label1.setOpaque(true);
        label1.setBackground(Color.PINK);
        label1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label2 = new JLabel("Drag Me 2");
        label2.setBounds(200, 50, 100, 50);
        label2.setOpaque(true);
        label2.setBackground(Color.CYAN);
        label2.setHorizontalAlignment(SwingConstants.CENTER);

        // Add drag-and-drop functionality
        DragAndDropHandler dragHandler = new DragAndDropHandler();
        label1.addMouseListener(dragHandler);
        label1.addMouseMotionListener(dragHandler);

        label2.addMouseListener(dragHandler);
        label2.addMouseMotionListener(dragHandler);

        // Add components to the main panel
        mainPanel.add(label1);
        mainPanel.add(label2);

        // Add main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}*/
