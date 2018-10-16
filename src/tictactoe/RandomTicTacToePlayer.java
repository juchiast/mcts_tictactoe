package tictactoe;

import java.util.LinkedList;
import java.util.Random;

public class RandomTicTacToePlayer implements TicTacToePlayer{
    
    @Override
    public TicTacToeMove getMove(TicTacToeGame game) {
        Random random = new Random();
        LinkedList<TicTacToeMove> emptyPostitions = game.getAvailableMoves();

        int chosenPositionIndex = random.nextInt(emptyPostitions.size());
        TicTacToeMove chosenMove = emptyPostitions.get(chosenPositionIndex);
        return chosenMove;

    }
}