package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class MaintenanceDroid {
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

    public void executeCommand(String commandString, Map<UUID, SpaceShipDeck> spaceShipDecks, Map<UUID, Obstacle> obstacles, Map<UUID, Connection> connections, Map<UUID, MaintenanceDroid> maintenanceDroids, UUID spaceshipDeckId) {
        // Parse the commandString to extract the direction and steps
        String[] commandParts = commandString.split(",");
        String direction = commandParts[0].substring(1).toLowerCase();
        int steps = Integer.parseInt(commandParts[1].substring(0, commandParts[1].length() - 1));

        // Retrieve the specified spaceship deck
        SpaceShipDeck spaceShipDeck = spaceShipDecks.get(spaceshipDeckId);
        if (spaceShipDeck == null) {
            System.out.println("Invalid spaceship deck ID specified.");
            return;
        }

        ArrayList<Obstacle> obstacleList = new ArrayList<>(obstacles.values());

        // Convert the list of Obstacle objects into a 2D int array
        int[][] obstaclesArray = new int[obstacleList.size()][4];
        for (int i = 0; i < obstacleList.size(); i++) {
            Obstacle obstacle = obstacleList.get(i);
            obstaclesArray[i][0] = obstacle.getX();
            obstaclesArray[i][1] = obstacle.getY();
            obstaclesArray[i][2] = obstacle.getX() + obstacle.getWidth();
            obstaclesArray[i][3] = obstacle.getY() + obstacle.getHeight();
        }

        // Call the move method to update the droid's position
        move(direction, steps, obstaclesArray, spaceShipDeck.getWidth() - 1, spaceShipDecks.size() - 1);

        // Check for collision with spaceship deck boundaries
        int maxX = spaceShipDeck.getWidth() - 1;
        int maxY = spaceShipDecks.size() - 1;
        if (x < 0 || x > maxX || y < 0 || y > maxY) {
            System.out.println("Spaceship deck boundary encountered, movement stopped.");
            x = Math.min(Math.max(x, 0), maxX);
            y = Math.min(Math.max(y, 0), maxY);
        }

        // Check for collision with obstacles
        for (Obstacle obstacle : obstacles.values()) {
            int startX = Math.min(obstacle.getX(), obstacle.getX());
            int endX = Math.max(obstacle.getX(), obstacle.getX());
            int startY = Math.min(obstacle.getY(), obstacle.getY());
            int endY = Math.max(obstacle.getY(), obstacle.getY());

            if ((x >= startX && x <= endX) && (y >= startY && y <= endY)) {
                System.out.println("Obstacle encountered, movement stopped.");
                x = Math.max(x, startX + 1);
                y = Math.max(y, startY + 1);
                break;
            }
        }
        // Update the maintenance droid's position in the maintenanceDroids map
        maintenanceDroids.put(id, this);
    }
}