package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Obstacle implements ObstacleInterface {
    private UUID id;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public Obstacle(UUID id, int startX, int startY, int endX, int endY) {
        this.id = id;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public UUID getId() {
        return id;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public static Obstacle parseObstacleString(String obstacleString) {
        String[] parts = obstacleString.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid obstacle string: " + obstacleString);
        }
        UUID id = UUID.fromString(parts[0]);
        int startX = Integer.parseInt(parts[1]);
        int startY = Integer.parseInt(parts[2]);
        int endX = Integer.parseInt(parts[3]);
        int endY = Integer.parseInt(parts[4]);
        return new Obstacle(id, startX, startY, endX, endY);
    }
}