import java.awt.*;
import javax.swing.*;

public class MainApp extends JFrame {
    public MainApp() {
        // Set up the frame
        JFrame frame = new JFrame("2D Floor Planner");
        frame.setTitle("Floor Plan Example 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(40, 40, 40));

        // Add a wrapper panel to create padding around the TopPanel
        JPanel topPanelWrapper = new JPanel(new BorderLayout());
        topPanelWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Top, Left, Bottom, Right
        topPanelWrapper.setOpaque(false); // Allow background to show through
        topPanelWrapper.add(new TopPanel(), BorderLayout.CENTER);

        // Add panels
        frame.add(new ParentPanel(), BorderLayout.WEST);
        frame.add(topPanelWrapper, BorderLayout.NORTH); // Add wrapped TopPanel
        frame.add(new CanvasPanel(), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}
