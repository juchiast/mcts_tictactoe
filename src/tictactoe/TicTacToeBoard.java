package tictactoe;

import java.awt.Point;
import java.util.LinkedList;
import static tictactoe.GameSymbols.EmptySpaceSymbol;

public class TicTacToeBoard {

    private final char[][] grid;

    public TicTacToeBoard() {
        grid = new char[3][3];
        emptyBoard();
    }

    private void emptyBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid[row][col] = EmptySpaceSymbol;
            }
        }
    }

    public LinkedList<Point> getEmptyPositions() {
        LinkedList<Point> points = new LinkedList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (spaceOnBoardIsEmpty(row, col)) {
                    points.add(new Point(col, row));
                }
            }
        }

        return points;
    }

    private boolean spaceOnBoardIsEmpty(int row, int col) {
        return grid[row][col] == EmptySpaceSymbol;
    }

    public void play(int x, int y, char symbol) {
        grid[y][x] = symbol;
    }


    public char getSymbolAtPoint(Point p) {
        return grid[p.y][p.x];
    }

    public void printBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                System.out.print(grid[row][col]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public TicTacToeBoard getCopy(){
        TicTacToeBoard copy = new TicTacToeBoard();
        for (int row = 0; row < 3; row ++){
            for (int col =0; col < 3; col++){
                copy.grid[row][col] = grid[row][col];
            }
        }
        return copy;
    }
}
