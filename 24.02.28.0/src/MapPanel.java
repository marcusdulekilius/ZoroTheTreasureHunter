import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MapPanel extends JPanel {
    private final int ROWS = 100;
    private final int COLS = 100;
    private boolean[][] map;
    private int paintedCount = 0;
    private int mouseX = -1;
    private int mouseY = -1;

    public MapPanel() {
        map = new boolean[ROWS][COLS];
        setPreferredSize(new Dimension(800, 800)); // Panel boyutunu ayarla

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX() / (getWidth() / COLS);
                int mouseY = e.getY() / (getHeight() / ROWS);
                if (mouseX >= 0 && mouseX < COLS && mouseY >= 0 && mouseY < ROWS) {
                    if (!map[mouseY][mouseX]) {
                        map[mouseY][mouseX] = true;
                        paintedCount++;
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellWidth = getWidth() / COLS;
        int cellHeight = getHeight() / ROWS;

        // Haritayı çiz
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (map[row][col]) {
                    g.setColor(Color.BLUE); // Boyanmış kareleri mavi yap
                    g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
                }
            }
        }

        // Altta boyanan birimkare sayısını yazdır
        g.setColor(Color.GREEN);
        g.drawString("Colored Squares: " + paintedCount, 10, getHeight() - 10);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Map Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MapPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
