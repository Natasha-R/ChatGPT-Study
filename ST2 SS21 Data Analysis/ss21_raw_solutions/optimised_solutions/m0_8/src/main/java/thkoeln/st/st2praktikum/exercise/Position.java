package thkoeln.st.st2praktikum.exercise;

import java.awt.*;

public class Position {
    private Point point;

    public Position(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", point.x, point.y);
    }
}
