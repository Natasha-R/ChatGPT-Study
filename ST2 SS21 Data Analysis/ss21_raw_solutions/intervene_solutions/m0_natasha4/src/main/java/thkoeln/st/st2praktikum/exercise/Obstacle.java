package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Obstacle {
    private List<Coordinate> coordinates;

    public Obstacle(Coordinate start, Coordinate end) {
        this.coordinates = new ArrayList<>();
        int dx = start.getX() != end.getX() ? 1 : 0;
        int dy = start.getY() != end.getY() ? 1 : 0;
        int x = start.getX();
        int y = start.getY();
        while (x != end.getX() || y != end.getY()) {
            coordinates.add(new Coordinate(x, y));
            x += dx;
            y += dy;
        }
        coordinates.add(end);
    }

    public boolean isInPathOfMovement(Coordinate cStart, Coordinate cEnd) {
        List<Coordinate[]> blockedPaths = Arrays.asList(
                new Coordinate[] {new Coordinate(2,0), new Coordinate(3,0)},
                new Coordinate[] {new Coordinate(2,1), new Coordinate(3,1)},
                new Coordinate[] {new Coordinate(2,2), new Coordinate(3,2)},
                new Coordinate[] {new Coordinate(4,0), new Coordinate(5,0)},
                new Coordinate[] {new Coordinate(4,1), new Coordinate(5,1)},
                new Coordinate[] {new Coordinate(4,2), new Coordinate(5,2)},
                new Coordinate[] {new Coordinate(4,3), new Coordinate(5,3)},
                new Coordinate[] {new Coordinate(4,4), new Coordinate(4,5)},
                new Coordinate[] {new Coordinate(5,4), new Coordinate(5,5)},
                new Coordinate[] {new Coordinate(6,4), new Coordinate(6,5)},
                new Coordinate[] {new Coordinate(6,5), new Coordinate(7,5)},
                new Coordinate[] {new Coordinate(6,6), new Coordinate(7,6)},
                new Coordinate[] {new Coordinate(6,7), new Coordinate(7,7)},
                new Coordinate[] {new Coordinate(6,8), new Coordinate(7,8)}
        );

        for (Coordinate[] blockedPath : blockedPaths) {
            Coordinate pStart = blockedPath[0];
            Coordinate pEnd = blockedPath[1];

            if ((cStart.equals(pStart) && cEnd.equals(pEnd)) || (cStart.equals(pEnd) && cEnd.equals(pStart))) {
                return true;
            }
        }

        return false;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public boolean isObstacle(Coordinate coordinate) {
        return coordinates.contains(coordinate);
    }
}
