package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MaintenanceDroid implements GoAble {
    private UUID id;
    private String name;
    private int currentX;
    private int currentY;
    private SpaceShipDeck spaceshipDeck;

    public MaintenanceDroid(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.currentX = 0;
        this.currentY = 0;
        this.spaceshipDeck = spaceshipDeck;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    @Override
    public void goTo(String goCommandString) {
        String direction = goCommandString.substring(1, 3);
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));
        int newX = currentX;
        int newY = currentY;
        boolean obstacleEncountered = false;

        switch (direction) {
            case "no":
                newY += steps;
                for (int i = currentY + 1; i <= newY; i++) {
                    if (spaceshipDeck.hasObstacle(currentX, i)) {
                        newY = i - 1;
                        obstacleEncountered = true;
                        break;
                    }
                }
                break;
            case "ea":
                newX += steps;
                for (int i = currentX + 1; i <= newX; i++) {
                    if (spaceshipDeck.hasObstacle(i, currentY)) {
                        newX = i - 1;
                        obstacleEncountered = true;
                        break;
                    }
                }
                break;
            case "so":
                newY -= steps;
                for (int i = currentY - 1; i >= newY; i--) {
                    if (spaceshipDeck.hasObstacle(currentX, i)) {
                        newY = i + 1;
                        obstacleEncountered = true;
                        break;
                    }
                }
                break;
            case "we":
                newX -= steps;
                for (int i = currentX - 1; i >= newX; i--) {
                    if (spaceshipDeck.hasObstacle(i, currentY)) {
                        newX = i + 1;
                        obstacleEncountered = true;
                        break;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid command direction: " + direction);
        }

        currentX = newX;
        currentY = newY;

        if (obstacleEncountered) {
            System.out.println("Obstacle encountered. Movement stopped.");
        } else {
            System.out.println("Moved " + steps + " steps " + direction + " to (" + currentX + "," + currentY + ")");
        }
    }

    public UUID getSpaceShipDeckId() {
        return spaceshipDeck.getId();
    }

    @Override
    public String getCoordinates() {
        return "(" + currentX + "," + currentY + ")";
    }

    public boolean executeCommand(String commandString, Map<UUID, SpaceShipDeck> spaceShipDecks, Map<UUID, Obstacle> obstacles, Map<UUID, Connection> connections) {
        boolean commandExecuted = false; // Initialize the flag as false

        // Parse the commandString and extract the command, direction, and steps
        String[] parts = commandString.split(" ");
        String command = parts[0];

        switch (command) {
            case "move":
                String direction = parts[1];
                int steps = Integer.parseInt(parts[2]);
                goTo(direction + steps);
                commandExecuted = true; // Command executed successfully
                break;
            case "getSpaceShipDeckId":
                System.out.println("Spaceship Deck ID: " + spaceshipDeck.getId());
                commandExecuted = true; // Command executed successfully
                break;
            case "getCoordinates":
                System.out.println("Current Coordinates: (" + currentX + "," + currentY + ")");
                commandExecuted = true; // Command executed successfully
                break;
            default:
                System.out.println("Invalid command: " + command);
        }
        return commandExecuted;
    }
}