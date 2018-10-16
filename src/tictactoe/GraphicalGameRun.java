package tictactoe;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;

public class GraphicalGameRun extends JFrame {
    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            GraphicalGameRun ex = new GraphicalGameRun();
            ex.setVisible(true);
        });
    }

    public GraphicalGameRun() {
        initUI();
    }

    private void initUI() {
        add(new GraphicalBoard());

        setSize(300, 300);

        setTitle("Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
