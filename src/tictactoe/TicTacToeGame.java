package tictactoe;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import static tictactoe.GameSymbols.Player1Symbol;
import static tictactoe.GameSymbols.Player2Symbol;

public class TicTacToeGame {

    protected final TicTacToeBoard board;
    private int playerToMove;
    private final HashMap<Integer, Character> playerSymbols = new HashMap<>();

    public TicTacToeGame(TicTacToeBoard board) {
        this.board = board;
        playerSymbols.put(0, Player1Symbol);
        playerSymbols.put(1, Player2Symbol);
    }

    public boolean gameIsOver() {
        return boardContainsWinForSymbol(Player1Symbol)
            || boardContainsWinForSymbol(Player2Symbol)
            || board.getEmptyPositions().size() == 0;
    }

    public boolean boardContainsWinForSymbol(char symbol) {
        for (WinningLine line : WinningLines.getInstance().getWinningLines()) {
            if (lineContainsWinForSymbol(line, symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean lineContainsWinForSymbol(WinningLine line, char symbol) {
        for (Point p : line.getPoints()) {
            if (board.getSymbolAtPoint(p) != symbol) {
                return false;
            }
        }
        return true;
    }

    public void play(TicTacToePlayer player1, TicTacToePlayer player2) {
        LinkedList<TicTacToePlayer> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);
        while (!gameIsOver()) {
            TicTacToePlayer player = players.get(playerToMove);
            TicTacToeMove move = player.getMove(this);
            makeMove(move);
        }
    }

    public void makeMove(TicTacToeMove chosenMove) {
        board.play(chosenMove.position.x, chosenMove.position.y, chosenMove.symbol);
        switchPlayer();
    }

    public void switchPlayer() {
        playerToMove = getEnemyPlayer(playerToMove);
    }

    public int getEnemyPlayer(int playerNumber) {
        return 1 - playerNumber;
    }

    public LinkedList<TicTacToeMove> getAvailableMoves() {
        LinkedList<TicTacToeMove> moves = new LinkedList<>();

        for (Point p : board.getEmptyPositions()) {
            moves.add(new TicTacToeMove(p, playerSymbols.get(playerToMove)));
        }

        return moves;
    }


    public Reward getReward() {
        if (player1Wins()) {
            return new Reward(1, -1);
        } else if (player2Wins()) {
            return new Reward(-1, 1);
        }
        return new Reward(0, 0);
    }

    private boolean player1Wins() {
        return boardContainsWinForSymbol(GameSymbols.Player1Symbol);
    }

    private boolean player2Wins() {
        return boardContainsWinForSymbol(GameSymbols.Player2Symbol);
    }

    public TicTacToeBoard getBoard() {
        return board;
    }

    public int getPlayerToMove() {
        return playerToMove;
    }

    public TicTacToeGame getCopy(){
        TicTacToeGame copy = new TicTacToeGame(board.getCopy());
        copy.playerToMove = playerToMove;
        return copy;
    }
}
