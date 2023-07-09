package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private UUID id;

    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public UUID getId() {
        return id;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }
    // Method to parse obstacleString and create Obstacle object
    public static Obstacle parseObstacle(String obstacleString) {
        // Split the obstacleString by commas to extract the start and end coordinates
        String[] coordinates = obstacleString.split(",");
        if (coordinates.length != 4) {
            throw new IllegalArgumentException("Invalid obstacleString: " + obstacleString);
        }

        // Convert the string coordinates to integers
        int startX = Integer.parseInt(coordinates[0].trim());
        int startY = Integer.parseInt(coordinates[1].trim());
        int endX = Integer.parseInt(coordinates[2].trim());
        int endY = Integer.parseInt(coordinates[3].trim());

        // Create and return a new Obstacle object
        return new Obstacle(startX, startY, endX, endY);
    }
}