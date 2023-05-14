package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class SpaceShipDeck {
    @Id
    private UUID id;
    private int height;
    private int width;
    private Map<Coordinate, Obstacle> obstacles;

    public SpaceShipDeck(UUID id, int height, int width) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.obstacles = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addObstacle(int startX, int startY, int endX, int endY) {
        Obstacle obstacle = new Obstacle(startX, startY, endX, endY);
        obstacles.put(obstacle.getLowerLeftCoordinate(), obstacle);
    }

    public boolean hasObstacle(int x, int y) {
        Coordinate coordinate = new Coordinate(x, y);
        return obstacles.containsKey(coordinate);
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

    public boolean isValidMove(int x, int y) {
        return !hasObstacle(x, y) && !isOutOfBounds(x, y);
    }

    private static class Obstacle {
        private Coordinate start;
        private Coordinate end;

        public Obstacle(int startX, int startY, int endX, int endY) {
            this.start = new Coordinate(startX, startY);
            this.end = new Coordinate(endX, endY);
        }

        public Coordinate getLowerLeftCoordinate() {
            return new Coordinate(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY()));
        }
    }

    private static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Coordinate)) {
                return false;
            }
            Coordinate other = (Coordinate) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }
    }
}
