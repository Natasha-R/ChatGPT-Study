package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Barrier {
    @Getter private Point start;
    @Getter private Point end;

    private void check(){
        if (start.getX() != end.getX() && start.getY() != end.getY())
            throw new InvalidCommandException("Diagonals not sut supported");
        if (start.getX() > end.getX())
            switchPoints();
        if (start.getY() > end.getY())
            switchPoints();
    }

    private void switchPoints(){
        var t = start;
        start = end;
        end = t;
    }

    public Barrier(Point start, Point end) {
        this.start = start;
        this.end = end;
        check();
    }

    public Barrier(int startX, int startY, int endX, int endY){
        start = new Point(startX, startY);
        end = new Point(endX, endY);
        check();
    }


    public Barrier(String s){
        var arr = s.split("-");
        if (arr.length != 2) throw new InvalidCommandException("Invalid arguments size, expected 2 got " + arr.length);
        start = new Point(arr[0]);
        end = new Point(arr[1]);
        check();
    }

    public Boolean blocks(Point point, OrderType dir){
        if (dir == OrderType.NORTH && start.getY() == point.getY() + 1 && point.getX() >= start.getX() && point.getX() < end.getX())
            return true;
        if (dir == OrderType.EAST && start.getX() == point.getX() + 1 && point.getY() >= start.getY() && point.getY() < end.getY())
            return true;
        if (dir == OrderType.SOUTH && start.getY() == point.getY() && point.getX() >= start.getX() && point.getX() < end.getX())
            return true;
        if (dir == OrderType.WEST && start.getX() == point.getX() && point.getY() >= start.getY() && point.getY() < end.getY())
            return true;
        return false;
    }
}
