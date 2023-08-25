package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

class Wall {
    private List<Point> wallPoints;
    private Point start;
    private Point end;

    public Wall(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.wallPoints = new ArrayList<>();
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                wallPoints.add(new Point(i, j));
            }
        }
    }

    public List<Point> getWallPoints() {
        return wallPoints;
    }

    public boolean containsPoint(Point point) {
        return wallPoints.contains(point);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    // Check if two walls are intersecting
    public boolean intersects(Wall other) {
        return this.wallPoints.stream().anyMatch(point -> other.wallPoints.contains(point));
    }

    // Length of the wall
    public int getLength() {
        return Math.max(Math.abs(end.x - start.x), Math.abs(end.y - start.y)) + 1;
    }

    // Change wall orientation from vertical to horizontal or vice versa
    public void changeOrientation() {
        int temp = start.y;
        start.y = start.x;
        start.x = temp;

        temp = end.y;
        end.y = end.x;
        end.x = temp;

        wallPoints.clear();
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                wallPoints.add(new Point(i, j));
            }
        }
    }

    // Move the wall by dx, dy
    public void move(int dx, int dy) {
        start.x += dx;
        start.y += dy;
        end.x += dx;
        end.y += dy;

        wallPoints.clear();
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                wallPoints.add(new Point(i, j));
            }
        }
    }
}

