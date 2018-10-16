package tictactoe;

import java.awt.Point;
import java.util.LinkedList;

public class WinningLine {

    private final LinkedList<Point> points;

    public WinningLine() {
        points = new LinkedList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public LinkedList<Point> getPoints(){
        return points;
    }
}
