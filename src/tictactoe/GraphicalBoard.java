package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GraphicalBoard extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        drawBoard(g);
    }

    public GraphicalBoard() {
        initBoard();
    }

    private void initBoard() {
        addMouseListener(new BoardAdapter());
    }

    private int getCellSize() {
        int cell_size = getBoardSize() / 3;
        return cell_size;
    }

    private int getBoardSize() {
        Dimension window_size = getSize();
        int size = (int) Math.min(window_size.getHeight(), window_size.getWidth());
        return size;
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int board_size = getBoardSize();
        int cell_size = getCellSize();
        int border_size = Math.max(1, cell_size / 15);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, board_size, board_size);


        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(border_size));
        for (int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                g2d.drawRect(j*cell_size, i * cell_size, cell_size, cell_size);
            }
        }
    }

    private class BoardAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int cell = getCellSize();
            int j = x / cell;
            int i = y / cell;
            System.out.println(i + " " + j);
        }
    }
}
