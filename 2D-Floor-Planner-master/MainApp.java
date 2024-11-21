import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

        // Add Save and Load buttons to the top panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton saveButton = new JButton("Save");
        saveButton.setFocusable(false);
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trigger save functionality
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Rooms");
                int userSelection = fileChooser.showSaveDialog(mainFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    RoomXMLHandler.saveRoomsToXML(canvasPanel.getRooms(), filePath);
                    JOptionPane.showMessageDialog(mainFrame, "Rooms saved successfully!");
                }
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.setFocusable(false);
        loadButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loadButton.setForeground(Color.WHITE);
        loadButton.setBackground(new Color(70, 130, 180));
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trigger load functionality
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Load Rooms");
                int userSelection = fileChooser.showOpenDialog(mainFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    ArrayList<Rooms> loadedRooms = RoomXMLHandler.loadRoomsFromXML(filePath);
                    canvasPanel.setRooms(loadedRooms);
                    canvasPanel.repaint(); // Refresh the UI with loaded rooms
                    JOptionPane.showMessageDialog(mainFrame, "Rooms loaded successfully!");
                }
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        canvasPanel = new CanvasPanel();

        ParentPanel parentPanel = new ParentPanel(canvasPanel);

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
