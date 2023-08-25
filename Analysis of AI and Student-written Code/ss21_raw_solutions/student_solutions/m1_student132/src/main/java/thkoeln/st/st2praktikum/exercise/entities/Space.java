package thkoeln.st.st2praktikum.exercise.entities;

import java.util.HashMap;
import java.util.UUID;

public class Space extends AbstractEntity {

    private final int height;
    private final int width;

    private final HashMap<UUID, Obstacle> obstacleMap = new HashMap<>();

    public Space(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void addObstacle(String obstacleString) {
        Obstacle newObstacle = new Obstacle(obstacleString, this);
        obstacleMap.put(newObstacle.getId(), newObstacle);
    }

    public void deleteObstacle(UUID uuid) {
        if (!obstacleMap.isEmpty()) obstacleMap.remove(uuid);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Boolean notBlocked(Move move) {
        for (Obstacle obstacle : obstacleMap.values()) {
            if (obstacle.getIllegalMoves().contains(move)) {
                return false;
            }
        }
        return true;
    }
}