package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Wall {
    private final Point start;
    private final Point end;

    public Wall(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public boolean intersects(Point point) {
        return (point.x > start.x && point.x < end.x && point.y >= start.y && point.y < end.y) ||
                (point.y > start.y && point.y < end.y && point.x >= start.x && point.x < end.x);
    }
}
