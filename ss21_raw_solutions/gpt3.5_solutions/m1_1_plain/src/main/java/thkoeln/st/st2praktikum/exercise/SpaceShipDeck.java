package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceShipDeck {
    private Integer height;
    private Integer width;
    private List<Obstacle> obstacles;
    private UUID spaceShipDeckId;

    public SpaceShipDeck(UUID spaceShipDeckId, int height, int width) {
        this.spaceShipDeckId = spaceShipDeckId;
        this.height = height;
        this.width = width;
        this.obstacles = new ArrayList<>();
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void removeObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }

    public boolean isObstacle(int x, int y) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoundary(int x, int y) {
        return x == 0 || x == width || y == 0 || y == height;
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x <= width && y >= 0 && y <= height;
    }
}
