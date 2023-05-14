package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceShipDeck implements ISpaceShipDeck {
    private final UUID spaceShipDeckId;
    private final int height;
    private final int width;
    private final List<ObstacleInterface> obstacles;

    public SpaceShipDeck(UUID spaceShipDeckId, int height, int width) {
        this.spaceShipDeckId = spaceShipDeckId;
        this.height = height;
        this.width = width;
        this.obstacles = new ArrayList<>();
    }

    public UUID getSpaceShipDeckId() {
        return spaceShipDeckId;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addObstacle(ObstacleInterface obstacle) {
        obstacles.add(obstacle);
    }

    public void removeObstacle(ObstacleInterface obstacle) {
        obstacles.remove(obstacle);
    }

    public boolean isObstacle(int x, int y) {
        for (ObstacleInterface obstacle : obstacles) {
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
