import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Grid extends JPanel {

    private int size;
    private int height;
    private int width;
    private int[][] grid;
    private int id;
    private ArrayList<EmptySpace> emptySpaces;

    public Grid(int size, int width, int height, int[][] grid) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.grid = grid;
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(width, height));
    }

    public static Color GetColorOfID(int i) {
        if (i == 0) {
            return Color.BLUE;
        } else if (i == 1) {
            return Color.ORANGE;
        } else if (i == 2) {
            return Color.CYAN;
        } else if (i == 3) {
            return Color.GREEN;
        } else if (i == 4) {
            return Color.MAGENTA;
        } else if (i == 5) {
            return Color.PINK;
        } else if (i == 6) {
            return Color.RED;
        } else if (i == 7) {
            return Color.YELLOW;
        } else if (i == 8) {
            return new Color(0, 0, 0);
        } else if (i == 9) {
            return new Color(0, 0, 100);
        } else if (i == 10) {
            return new Color(100, 0, 0);
        } else if (i == 11) {
            return new Color(0, 100, 0);
        } else {
            return Color.LIGHT_GRAY;
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width, height);
        for (int i = 0; i < width; i += size) {
            g.drawLine(i, 0, i, height);
        }
        for (int i = 0; i < height; i += size) {
            g.drawLine(0, i, width, i);
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == id || grid[i][j] != -1) {
                    g.setColor(GetColorOfID(grid[i][j]));
                    g.fillRect(j * size + 1, i * size + 1, size - 1, size - 1);
                }
            }
        }

        if (emptySpaces != null) {
            g.setColor(Color.BLACK); 
            for (EmptySpace space : emptySpaces) {
                int x = space.x;
                int y = space.y;
                int score = space.score;
                g.drawString(String.valueOf(score), y * size + size / 2, x * size + size / 2);
            }
        }

    }

    public void setGrid(int[][] _grid, int _id) {
        // this.emptySpaces = emptySpaces;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = _grid[i][j];
            }
        }
        id = _id;
        revalidate();
        repaint();

    }

    public void setGrid(int[][] _grid, int _id, ArrayList<EmptySpace> emptySpaces) {
        this.emptySpaces = emptySpaces;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = _grid[i][j];
            }
        }
        id = _id;
        revalidate();
        repaint();

    }

}
