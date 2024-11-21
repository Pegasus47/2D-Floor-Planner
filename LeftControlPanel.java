import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LeftControlPanel extends JPanel {
    private final ImageIcon exportIcon;
    private final ImageIcon buildIcon;
    private final ImageIcon objectsIcon;
    private final ImageIcon shareIcon;
    private final ImageIcon helpIcon;
    private final MainControlPanel mainControlPanel;

    public LeftControlPanel(MainControlPanel mainPanel) {
        this.mainControlPanel = mainPanel;
        setBackground(new Color(255, 194, 74)); // Panel background color
        setPreferredSize(new Dimension(85, 500)); // Fixed width
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical layout

        // Load icons (adjust file paths as needed)
        exportIcon = new ImageIcon("export.png");
        buildIcon = new ImageIcon("build.png");
        objectsIcon = new ImageIcon("objects.png");
        shareIcon = new ImageIcon("share.png");
        helpIcon = new ImageIcon("help.png");

        // Create and add buttons directly
        add(Box.createVerticalStrut(20));
        CustomButton2 exportButton = new CustomButton2("Export", exportIcon);
        exportButton.setAlignmentX(CENTER_ALIGNMENT);
        add(exportButton);

        add(Box.createVerticalStrut(10)); // Spacer

        CustomButton2 buildButton = new CustomButton2("Build", buildIcon);
        buildButton.setAlignmentX(CENTER_ALIGNMENT);
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                mainControlPanel.resetToMainView();
            }
        });
        add(buildButton);

        add(Box.createVerticalStrut(10)); // Spacer

        CustomButton2 objectsButton = new CustomButton2("Objects", objectsIcon);
        objectsButton.setAlignmentX(CENTER_ALIGNMENT);
        objectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                mainControlPanel.showObjectsGrid();
            }
        });
        add(objectsButton);

        add(Box.createVerticalStrut(10)); // Spacer

        CustomButton2 shareButton = new CustomButton2("Share", shareIcon);
        shareButton.setAlignmentX(CENTER_ALIGNMENT);
        add(shareButton);

        add(Box.createVerticalStrut(10)); // Spacer

        CustomButton2 helpButton = new CustomButton2("Help", helpIcon);
        helpButton.setAlignmentX(CENTER_ALIGNMENT);
        add(helpButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smooth edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a rounded rectangle background
        g2d.setColor(getBackground());
        int arcWidth = 30;
        int arcHeight = 30;
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        g2d.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false; // Transparent for rounded corners
    }
}