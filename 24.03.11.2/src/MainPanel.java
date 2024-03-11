import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
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
    private List<GameObject> gameObjects;
    private Image backgroundImage;

    public MainPanel() {
        setLayout(new GridBagLayout());

        newMapButton = new JButton("New Map");
        startButton = new JButton("Start");

        newMapButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter map size:");
            try {
                mapSize = Integer.parseInt(input);
                generateGameObjects();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        });

        startButton.addActionListener(e -> {
            if (mapSize > 0) {
                createMap(mapSize);
            } else {
                JOptionPane.showMessageDialog(null, "Please create a map first.");
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(newMapButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(startButton, gbc);

        add(buttonPanel, new GridBagConstraints());

        String imagePath = "C:/Users/dulek/Downloads/images/zoro.png";
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void generateGameObjects() {
        gameObjects = new ArrayList<>();
        int totalObstacles = 0;
    
        while (totalObstacles < 20) {
            int randObstacle = (int) (Math.random() * 4);
            GameObject obstacle = null;

            switch (randObstacle) {
                case 0:
                    obstacle = new GameObject("C:\\Users\\dulek\\Downloads\\images\\Tree.png", new Dimension(2, 2), 2);
                    break;
                case 1:
                    obstacle = new GameObject("C:\\Users\\dulek\\Downloads\\images\\Wall.png", new Dimension(10, 1), 2);
                    break;
                case 2:
                    obstacle = new GameObject("C:\\Users\\dulek\\Downloads\\images\\Rock.png", new Dimension(3, 3), 2);
                    break;
                case 3:
                    obstacle = new GameObject("C:\\Users\\dulek\\Downloads\\images\\Mountain.png", new Dimension(15, 15), 2);
                    break;
            }

            if (obstacle != null) {
                gameObjects.add(obstacle);
                totalObstacles += obstacle.getCount();
            }
        }
    }

    private void createMap(int size) {
        JFrame mapFrame = new JFrame("Map");
        mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapFrame.setSize(size * 10, size * 10);
    
        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
    
                int unitSize = 15;
                int[][] grid = new int[size][size]; // 0: Boş, 1: Dolu
    
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        if (x < size / 2) {
                            g.setColor(Color.YELLOW);
                        } else {
                            g.setColor(Color.GRAY);
                        }
                        g.fillRect(x * unitSize, y * unitSize, unitSize, unitSize);
                        g.setColor(Color.BLACK);
                        g.drawRect(x * unitSize, y * unitSize, unitSize, unitSize);
                    }
                }
    
                if (gameObjects != null) {
                    int totalObstacles = 0;
                    for (GameObject gameObject : gameObjects) {
                        Image image = gameObject.getImage();
                        Dimension objSize = gameObject.getSize();
                        int objCount = gameObject.getCount();
    
                        for (int i = 0; i < objCount; i++) {
                            if (totalObstacles >= 10 || (size * size - totalObstacles) < 20) {
                                return;
                            }
    
                            boolean positionValid = false;
                            int randX = 0, randY = 0;
                            while (!positionValid) {
                                randX = (int) (Math.random() * (size - objSize.width + 1));
                                randY = (int) (Math.random() * (size - objSize.height + 1));
                                positionValid = true;
                                for (int x = randX; x < randX + objSize.width; x++) {
                                    for (int y = randY; y < randY + objSize.height; y++) {
                                        if (grid[x][y] == 1) {
                                            positionValid = false;
                                            break;
                                        }
                                    }
                                    if (!positionValid) break;
                                }
                            }
    
                            // Eğer uygunsa çizmesi için
                            for (int x = randX; x < randX + objSize.width; x++) {
                                for (int y = randY; y < randY + objSize.height; y++) {
                                    grid[x][y] = 1;
                                    g.drawImage(image, x * unitSize, y * unitSize, unitSize, unitSize, null);
                                }
                            }
                            totalObstacles++;
                        }
                    }
                }
            }
    
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(size * 15, size * 15);
            }
        };
    
        JScrollPane scrollPane = new JScrollPane(mapPanel);
        mapFrame.add(scrollPane);
        mapFrame.pack();
        mapFrame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(720, 720);
            frame.setLocationRelativeTo(null);
            frame.add(new MainPanel());
            frame.setVisible(true);
        });
    }
}

class GameObject {
    private String imagePath;
    private int count;
    private Dimension size;

    public GameObject(String imagePath, Dimension size, int count) {
        this.imagePath = imagePath;
        this.size = size;
        this.count = count;
    }

    public Image getImage() {
        ImageIcon icon = new ImageIcon(imagePath);
        return icon.getImage();
    }

    public int getCount() {
        return count;
    }

    public Dimension getSize() {
        return size;
    }
}