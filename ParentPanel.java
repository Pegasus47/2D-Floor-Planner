import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ParentPanel extends JPanel {
    public ParentPanel() {
        setBackground(new Color(40, 40, 40)); // Background of parent panel
        setPreferredSize(new Dimension(325, 0)); // Fixed parent panel size
        setLayout(new BorderLayout()); // Use BorderLayout for better control

        // Create the LeftControlPanel and wrap it to ensure proper sizing
        JPanel leftControlWrapper = new JPanel();
        leftControlWrapper.setBackground(new Color(40, 40, 40)); // Match parent background
        leftControlWrapper.setLayout(new BoxLayout(leftControlWrapper, BoxLayout.Y_AXIS));
        leftControlWrapper.setBorder(new EmptyBorder(0, 10, 300, 10));
        LeftControlPanel leftControlPanel = new LeftControlPanel();
        leftControlWrapper.add(leftControlPanel);

        // Wrap the main control panel in a container for spacing and centering
        JPanel mainControlWrapper = new JPanel();
        mainControlWrapper.setBackground(new Color(40, 40, 40)); // Match parent background
        mainControlWrapper.setLayout(new BoxLayout(mainControlWrapper, BoxLayout.Y_AXIS));
        mainControlWrapper.setBorder(new EmptyBorder(0, 10, 190, 10));
        mainControlWrapper.add(Box.createVerticalGlue()); // Add vertical spacing
        mainControlWrapper.add(new MainControlPanel());
        mainControlWrapper.add(Box.createVerticalGlue()); // Add vertical spacing

        // Add panels to parent panel
        add(leftControlWrapper, BorderLayout.WEST); // Left aligned
        add(mainControlWrapper, BorderLayout.CENTER); // Centered
    }
}
