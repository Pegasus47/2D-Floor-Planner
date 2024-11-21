import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

public class ParentPanel extends JPanel {
    private CanvasPanel canvasPanel;
    MainControlPanel mainControlPanel;

    public ParentPanel(CanvasPanel canvas) {
        this.canvasPanel = canvas;
        setBackground(new Color(40, 40, 40)); // Background of parent panel
        setPreferredSize(new Dimension(325, 0)); // Fixed parent panel size
        setLayout(new BorderLayout()); // Use BorderLayout for better control

        // Create the LeftControlPanel with save and load actions
        JPanel leftControlWrapper = new JPanel();
        leftControlWrapper.setBackground(new Color(40, 40, 40)); // Match parent background
        leftControlWrapper.setLayout(new BoxLayout(leftControlWrapper, BoxLayout.Y_AXIS));
        leftControlWrapper.setBorder(new EmptyBorder(0, 10, 300, 10));
        LeftControlPanel leftControlPanel = new LeftControlPanel();
        leftControlWrapper.add(leftControlPanel);

        // Ensure MainControlPanel is properly added to the center
        MainControlPanel mainControlPanel = new MainControlPanel(canvasPanel);

        // Add panels to parent panel
        add(leftControlWrapper, BorderLayout.WEST); // Left aligned
        add(mainControlPanel, BorderLayout.CENTER); // Ensure MainControlPanel is centered
    }

}
