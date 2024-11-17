import java.awt.*;
import javax.swing.*;

public class FloorPlanApp extends JFrame {

    public FloorPlanApp() {
        // Main frame setup
        setTitle("Floor Plan Designer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);

        // Parent panel for both left panels
        JPanel leftParentPanel = new JPanel();
        leftParentPanel.setLayout(new BorderLayout());

        // Slim navigation panel
        JPanel slimPanel = new JPanel();
        slimPanel.setLayout(new BoxLayout(slimPanel, BoxLayout.Y_AXIS));
        slimPanel.setBackground(Color.DARK_GRAY);
        addNavigationButtons(slimPanel); // Add buttons for Export, Build, etc.

        // Wider control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(255, 185, 80)); // Orange background
        addControlOptions(controlPanel); // Add options like "Add Wall", "Add Room"

        // Add slim and control panels to the parent panel
        leftParentPanel.add(slimPanel, BorderLayout.WEST);
        leftParentPanel.add(controlPanel, BorderLayout.CENTER);

        // Add parent panel to the main frame
        add(leftParentPanel, BorderLayout.WEST);

        // Show frame
        setVisible(true);
    }

    // Add buttons to the slim navigation panel
    private void addNavigationButtons(JPanel slimPanel) {
        JButton exportButton = new JButton("Export");
        JButton buildButton = new JButton("Build");
        JButton objectsButton = new JButton("Objects");
        JButton shareButton = new JButton("Share");
        JButton helpButton = new JButton("Help");

        // Customize button appearance
        exportButton.setBackground(Color.GRAY);
        exportButton.setForeground(Color.WHITE);

        slimPanel.add(exportButton);
        slimPanel.add(buildButton);
        slimPanel.add(objectsButton);
        slimPanel.add(shareButton);
        slimPanel.add(helpButton);
    }

    // Add control options to the wider panel
    private void addControlOptions(JPanel controlPanel) {
        controlPanel.add(new JLabel("Add Wall"));
        controlPanel.add(new JLabel("Add Room"));
        controlPanel.add(new JLabel("Place Doors"));
        controlPanel.add(new JLabel("Place Windows"));
    }

    public static void main(String[] args) {
        new FloorPlanApp();
    }
}
