package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MaintenanceDroid implements Movable {
    private int x;
    private int y;
    private UUID id;
    private String name;

    public MaintenanceDroid(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.x = 0; // Set default x position to 0
        this.y = 0; // Set default y position to 0
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void move(String direction, int steps, int[][] obstacles, int maxX, int maxY) {
        int prevX = x;
        int prevY = y;
        switch (direction) {
            case "no": // Move north
                y = Math.max(y - steps, 0);
                break;
            case "ea": // Move east
                x = Math.min(x + steps, maxX);
                break;
            case "so": // Move south
                y = Math.min(y + steps, maxY);
                break;
            case "we": // Move west
                x = Math.max(x - steps, 0);
                break;
            default:
                System.out.println("Invalid direction: " + direction);
                break;
        }

        // Check for collision with obstacles or spaceship deck boundaries
        for (int[] obstacle : obstacles) {
            int startX = Math.min(obstacle[0], obstacle[2]);
            int endX = Math.max(obstacle[0], obstacle[2]);
            int startY = Math.min(obstacle[1], obstacle[3]);
            int endY = Math.max(obstacle[1], obstacle[3]);

            if ((prevX >= startX && prevX <= endX) && (y >= startY && y <= endY)) {
                x = prevX;
                System.out.println("Obstacle encountered, movement stopped.");
                break;
            } else if ((x >= startX && x <= endX) && (prevY >= startY && prevY <= endY)) {
                y = prevY;
                System.out.println("Obstacle encountered, movement stopped.");
                break;
            }
        }

        // Check for collision with spaceship deck boundaries
        if (x == 0 || x == maxX || y == 0 || y == maxY) {
            System.out.println("Spaceship deck boundary encountered, movement stopped.");
            x = prevX;
            y = prevY;
        }
    }

    public void printCurrentPosition() {
        System.out.println("Maintenance Droid is currently at (" + x + "," + y + ").");
    }
}
