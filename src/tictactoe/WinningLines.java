package tictactoe;

import java.awt.Point;
import java.util.LinkedList;

public class WinningLines {

    private final LinkedList<WinningLine> lines = new LinkedList<>();
    private static WinningLines winningLines;
    private  WinningLines(){
        addWinningLines();
    }

    private void addWinningLines() {
        addWinningRows();
        addWinningColumns();
        addTopLeftBottomRightWinningLine();
        addTopRightBottomLeftWinningLine();
    }

    private void addWinningRows() {
        for (int row = 0; row < 3; row++) {
            WinningLine winningLine = new WinningLine();
            for (int col = 0; col < 3; col++) {
                winningLine.addPoint(new Point(col, row));
            }
            lines.add(winningLine);
        }
    }

    private void addWinningColumns() {
        for (int col = 0; col < 3; col++) {
            WinningLine winningLine = new WinningLine();
            for (int row = 0; row < 3; row++) {
                winningLine.addPoint(new Point(col, row));
            }
            lines.add(winningLine);
        }
    }

    private void addTopLeftBottomRightWinningLine() {
        WinningLine line = new WinningLine();
        line.addPoint(new Point(0, 0));
        line.addPoint(new Point(1, 1));
        line.addPoint(new Point(2, 2));
        lines.add(line);
    }

    private void addTopRightBottomLeftWinningLine() {
        WinningLine topRightBottomLeft = new WinningLine();
        topRightBottomLeft.addPoint(new Point(2, 0));
        topRightBottomLeft.addPoint(new Point(1, 1));
        topRightBottomLeft.addPoint(new Point(0, 2));
        lines.add(topRightBottomLeft);
    }

    public static WinningLines getInstance(){
        if (winningLines == null){
            winningLines = new WinningLines();
        }
        return winningLines;
    }

    public LinkedList<WinningLine> getWinningLines(){
        return lines;
    }
}
