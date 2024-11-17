import java.awt.*;
import javax.swing.*;

public class MainApp extends JFrame {
    public MainApp() {
        // Set up the frame
        setTitle("Floor Plan Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 1024);
        setLayout(new BorderLayout());

        // Add panels
        add(new ParentPanel(), BorderLayout.WEST);
        add(new TopPanel(), BorderLayout.NORTH);
        add(new CanvasPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}

