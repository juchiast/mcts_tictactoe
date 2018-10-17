package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class GraphicalBoard extends JPanel {
    private final EventLoop event_loop;
    private final Player player;
    private final MctsTicTacToePlayer bot;
    private final Game game;
    ArrayBlockingQueue<Integer> queue;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        drawBoard(g);
    }

    public GraphicalBoard() {
        queue = new ArrayBlockingQueue<Integer>(100);
        event_loop = new EventLoop();

        player = new Player(queue);
        bot = new MctsTicTacToePlayer(10000);
        TicTacToeBoard board = new TicTacToeBoard();
        game = new Game(board);

        initBoard();
        event_loop.start();
    }

    private void initBoard() {
        addMouseListener(new BoardAdapter());
    }

    private int getCellSize() {
        return getBoardSize() / 3;
    }

    private int getBoardSize() {
        Dimension window_size = getSize();
        return (int) Math.min(window_size.getHeight(), window_size.getWidth());
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int board_size = getBoardSize();
        int cell_size = getCellSize();
        int stroke_size = Math.max(1, cell_size / 15);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, board_size, board_size);

        g2d.setStroke(new BasicStroke(stroke_size));

        g2d.setColor(Color.black);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                g2d.drawRect(j * cell_size, i * cell_size, cell_size, cell_size);
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
            try {
                queue.put(i * 3 + j);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class Player implements TicTacToePlayer {
        ArrayBlockingQueue<Integer> queue;
        public Player(ArrayBlockingQueue<Integer> q) {
            queue = q;
        }

        @Override
        public TicTacToeMove getMove(TicTacToeGame game) {
            try {
                while (true) {
                    Integer x = queue.take();
                    int i = x / 3;
                    int j = x % 3;
                    LinkedList<TicTacToeMove> moves = game.getAvailableMoves();
                    for (int id = 0; id < moves.size(); id++) {
                        int row = moves.get(id).position.y;
                        int col = moves.get(id).position.x;
                        if (row == i && col == j) {
                            return moves.get(id);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class Game extends TicTacToeGame {
        public Game(TicTacToeBoard board) {
            super(board);
        }

        @Override
        public void makeMove(TicTacToeMove chosenMove) {
            super.makeMove(chosenMove);
            board.printBoard();
        }
    }

    private class EventLoop extends Thread {
        @Override
        public void run() {
            while (true) {
                game.play(player, bot);
            }
        }
    }
}
