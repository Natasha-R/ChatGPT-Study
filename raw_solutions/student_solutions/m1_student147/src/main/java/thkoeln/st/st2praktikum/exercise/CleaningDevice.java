package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.Pair;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class CleaningDevice {
    private UUID id;
    private String name;
    private HashMap<UUID, Space> spaces;
    private UUID currentSpaceId;
    private Coordinate position;

    public CleaningDevice(String name, HashMap<UUID, Space> spaces) {
        this.name = name;
        id = UUID.randomUUID();
        this.spaces = spaces;
    }

    public Boolean execute(String commandString) {
        UUID destinationSpaceId;
        Pair commandPair = getCommandPair(commandString);
        String command = (String) commandPair.getLeft();
        String content = (String) commandPair.getRight();

        switch (command) {
            case "no":
            case "ea":
            case "we":
            case "so":
                int steps = Integer.parseInt(content);
                move(command, steps);
                return true;

            case "tr":
                destinationSpaceId = UUID.fromString(content);
                return transport(destinationSpaceId);

            case "en":
                destinationSpaceId = UUID.fromString(content);
                return set(destinationSpaceId);

            default:
                return false;
        }
    }

    private Pair getCommandPair(String commandString) {
        String str = commandString
                .replace("[", "")
                .replace("]", "");
        String[] command = str.split(",");

        return Pair.of(command[0], command[1]);
    }

    private void move(String direction, int steps) {
        Space currentSpace = spaces.get(currentSpaceId);

        currentSpace.removeCleaningDeviceId(position);
        switch (direction) {
            case "no":
                moveNorth(steps, currentSpace);
                break;

            case "ea":
                moveEast(steps, currentSpace);
                break;

            case "so":
                moveSouth(steps, currentSpace);
                break;

            case "we":
                moveWest(steps, currentSpace);
        }

        currentSpace.addCleaningDeviceId(position, id);
    }

    private void moveNorth(int steps, Space currentSpace){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentSpace.hasNoObstacle(position.getX(), position.getY() + possibleSteps, "no"))
            possibleSteps++;
        position.setY(position.getY() + possibleSteps);
    }

    private void moveEast(int steps, Space currentSpace){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentSpace.hasNoObstacle(position.getX() + possibleSteps, position.getY(), "ea"))
            possibleSteps++;
        position.setX(position.getX() + possibleSteps);
    }

    private void moveSouth(int steps, Space currentSpace){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentSpace.hasNoObstacle(position.getX(), position.getY() - possibleSteps, "so"))
            possibleSteps++;
        position.setY(position.getY() - possibleSteps);
    }

    private void moveWest(int steps, Space currentSpace){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentSpace.hasNoObstacle(position.getX() - possibleSteps, position.getY(), "we"))
            possibleSteps++;
        position.setX(position.getX() - possibleSteps);
    }

    private Boolean transport(UUID id) {
        Connection connection = getSpaceSquareConnection();
        if (connection == null)
            return false;

        UUID sourceId = connection.getSourceId();
        UUID destinationId = connection.getDestinationId();
        Coordinate sourceCoordinate = connection.getSourceCoordinate();
        Coordinate destinationCoordinate = connection.getDestinationCoordinate();
        Space currentSpace = spaces.get(currentSpaceId);

        if (id.compareTo(destinationId) == 0) {
            currentSpace.removeCleaningDeviceId(position);
            position = destinationCoordinate;
            currentSpaceId = destinationId;

            return true;
        }
        else if (id.compareTo(sourceId) == 0) {
            currentSpace.removeCleaningDeviceId(position);
            position = sourceCoordinate;
            currentSpaceId = sourceId;

            return true;
        }
        else
            return false;
    }

    private Boolean set(UUID destinationSpaceId) {
        Space currentSpace = spaces.get(destinationSpaceId);
        Coordinate position = new Coordinate(0, 0);

        if(currentSpaceId == null && !isSpaceSquareOccupied(destinationSpaceId, position)){
            this.position = position;
            currentSpaceId = destinationSpaceId;
            currentSpace.addCleaningDeviceId(position, id);
            return true;
        }
        else
            return false;
    }

    private Boolean isSpaceSquareOccupied(UUID destinationSpaceId, Coordinate coordinate){
        Space currentSpace = spaces.get(destinationSpaceId);
        return currentSpace.isSquareOccupied(coordinate);
    }

    private Connection getSpaceSquareConnection(){
        Space currentSpace = spaces.get(currentSpaceId);
        return currentSpace.getSquareConnection(position);
    }

    public UUID getCleaningDeviceSpaceId() {
        return currentSpaceId;
    }

    public String getCoordinates() {
        return String.format("(%d,%d)", position.getX(), position.getY());
    }
}