package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class SpaceShipDeck implements Deck {
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

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public boolean hasObstacle(int x, int y) {
        Coordinate coordinate = new Coordinate(x, y);
        return obstacles.containsKey(coordinate);
    }

    @Override
    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

    @Override
    public boolean isValidMove(int x, int y) {
        return !hasObstacle(x, y) && !isOutOfBounds(x, y);
    }

    @Override
    public void addObstacle(int startX, int startY, int endX, int endY) {
        Obstacle obstacle = new Obstacle(startX, startY, endX, endY);
        obstacles.put(obstacle.getLowerLeftCoordinate(), obstacle);
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
}