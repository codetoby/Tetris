import java.awt.*;
import javax.swing.*;

public class NextPentomino extends JPanel {

    private int[][] piece;
    private int id;

    public NextPentomino(int[][] piece) {

        this.piece = piece;

        setBackground(new Color(150,150,150));
        setPreferredSize(new Dimension(250, 200));

        JLabel text = new JLabel("Next Pentomino:");
        add(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = 30;

        int width = piece[0].length;
        int height = piece.length;

        int x = (getWidth() - width * size) / 2;
        int y = (getHeight() - height * size) / 2;

        g.setColor(new Color(150,150,150));
        g.drawRect(x, y, width * size, height * size);
        for (int i = 1; i < height; i++) {
            int yPos = y + i * size;
            g.drawLine(x, yPos, x + width * size, yPos);
        }
        for (int i = 1; i < width; i++) {
            int xPos = x + i * size;
            g.drawLine(xPos, y, xPos, y + height * size);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (piece[i][j] == 1) {
                    g.setColor(GetColorOfID(id));
                    g.fillRect(x + j * size + 1, y + i * size + 1, size - 1, size - 1);
                }
            }
        }
    }

    public static Color GetColorOfID(int i)
    {
        if(i==0) {return Color.BLUE;}
        else if(i==1) {return Color.ORANGE;}
        else if(i==2) {return Color.CYAN;}
        else if(i==3) {return Color.GREEN;}
        else if(i==4) {return Color.MAGENTA;}
        else if(i==5) {return Color.PINK;}
        else if(i==6) {return Color.RED;}
        else if(i==7) {return Color.YELLOW;}
        else if(i==8) {return new Color(0, 0, 0);}
        else if(i==9) {return new Color(0, 0, 100);}
        else if(i==10) {return new Color(100, 0,0);}
        else if(i==11) {return new Color(0, 100, 0);}
        else {return Color.LIGHT_GRAY;}
    }

    public void setPiece(int[][] piece, int id) {
        this.id = id;
        this.piece = piece;
        repaint();
    }

}
