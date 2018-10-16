package tictactoe;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;

public class BufferedReaderPlayer implements TicTacToePlayer {

    @Override
    public TicTacToeMove getMove(TicTacToeGame game) {
        System.out.println("Select an option: ");
        LinkedList<TicTacToeMove> moves = game.getAvailableMoves();
        for (int i = 0; i < moves.size(); i++){
            int row = moves.get(i).position.y;
            int col = moves.get(i).position.x;
            System.out.println(String.format("%d) Row: %d, Col: %d", i+1, row, col));
        }

        Scanner s = new Scanner(new InputStreamReader(System.in));

        int selectedOption = s.nextInt() -1;

        return moves.get(selectedOption);

    }
}
