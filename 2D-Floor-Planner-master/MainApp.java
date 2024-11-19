import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    CanvasPanel canvasPanel;

    public MainApp() {
        JFrame mainFrame = new JFrame("2D Floor Planner");
        mainFrame.setTitle("Floor Plan Example 1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1440, 1024);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(40, 40, 40));

        // Initialize and add existing panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.setOpaque(false);
        topPanel.add(new TopPanel(), BorderLayout.CENTER);
        canvasPanel = new CanvasPanel();

        ParentPanel parentPanel = new ParentPanel(canvasPanel);

        // Initialize CanvasPanel with room drawing functionality


        // Add a button for room creation in the TopPanel or another convenient location
        /*JButton roomButton = new JButton("Create A Room");
        roomButton.setFocusable(false);
        roomButton.setFont(new Font("Arial", Font.PLAIN, 16));
        roomButton.setForeground(Color.BLUE);
        roomButton.setBackground(Color.LIGHT_GRAY);
        roomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.setDrawingEnabled(true);
            }
        });
        // Add the button to the top panel
        topPanel.add(roomButton, BorderLayout.EAST);
*/
        // Add all components to the main frame
        mainFrame.add(parentPanel, BorderLayout.WEST);
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(canvasPanel, BorderLayout.CENTER);
        DragAndDrop dragAndDrop = new DragAndDrop(canvasPanel.getRooms());
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}
