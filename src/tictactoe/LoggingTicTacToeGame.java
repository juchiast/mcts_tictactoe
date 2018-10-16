package tictactoe;

public class LoggingTicTacToeGame extends TicTacToeGame{
    public LoggingTicTacToeGame(TicTacToeBoard board) {
        super(board);
    }

    @Override
    public void makeMove(TicTacToeMove chosenMove) {
        super.makeMove(chosenMove);
        board.printBoard();
    }
}
