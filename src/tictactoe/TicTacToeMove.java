package tictactoe;

import java.awt.Point;

public class TicTacToeMove {
    Point position;
    char symbol;

    public TicTacToeMove(Point position, char symbol){
        this.position = position;
        this.symbol = symbol;
    }

    public Point getPosition(){
        return position;
    }

    public char getSymbol(){
        return symbol;
    }
}
