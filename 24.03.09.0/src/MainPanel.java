import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class MainPanel extends JPanel {
    private JButton newMapButton;
    private JButton startButton;
    private int mapSize;

    public MainPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLUE);

        newMapButton = new JButton("New Map");
        startButton = new JButton("Start");

        newMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter map size :");
                try {
                    mapSize = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mapSize > 0) {
                    createMap(mapSize);
                } else {
                    JOptionPane.showMessageDialog(null, "Please create a map first.");
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.BLUE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Butonlar arasındaki boşluk
        buttonPanel.add(newMapButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(startButton, gbc);

        add(buttonPanel, new GridBagConstraints());

    }

    private void createMap(int size) {
        JFrame mapFrame = new JFrame("Map");
        mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Harita boyutuna uygun JFrame boyutunu ayarla
        mapFrame.setSize(size * 10, size * 10);

        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int unitSize = 20; // Birim kare boyutu

                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        if (x < size / 2) {
                            g.setColor(Color.YELLOW); // Sol taraf sarı renk
                        } else {
                            g.setColor(Color.GRAY); // Sağ taraf gri renk
                        }
                        g.fillRect(x * unitSize, y * unitSize, unitSize, unitSize); // Birimkare içi boyama
                        g.setColor(Color.BLACK);
                        g.drawRect(x * unitSize, y * unitSize, unitSize, unitSize); // Birimkare sınırları
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(size * 20, size * 20); // Harita boyutu
            }
        };

        JScrollPane scrollPane = new JScrollPane(mapPanel);
        mapFrame.add(scrollPane);
        mapFrame.pack();
        mapFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Main Panel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(720, 720);
                frame.setLocationRelativeTo(null); // Ekranın ortasında görünmesi için
                frame.add(new MainPanel());
                frame.setVisible(true);
            }
        });
    }
}
