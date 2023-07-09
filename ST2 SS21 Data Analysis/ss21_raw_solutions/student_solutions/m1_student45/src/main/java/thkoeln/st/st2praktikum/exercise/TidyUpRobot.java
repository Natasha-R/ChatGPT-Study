package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.Pair;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class TidyUpRobot {
    private UUID id;
    private String name;
    private HashMap<UUID, Room> rooms;
    private UUID currentRoomId;
    private Coordinate position;

    public TidyUpRobot(String name, HashMap<UUID, Room> rooms) {
        this.name = name;
        id = UUID.randomUUID();
        this.rooms = rooms;
    }

    public Boolean execute(String commandString) {
        UUID destinationRoomId;
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
                destinationRoomId = UUID.fromString(content);
                return transport(destinationRoomId);

            case "en":
                destinationRoomId = UUID.fromString(content);
                return set(destinationRoomId);

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
        Room currentRoom = rooms.get(currentRoomId);

        currentRoom.removeTidyUpRobotId(position);
        switch (direction) {
            case "no":
                moveNorth(steps, currentRoom);
                break;

            case "ea":
                moveEast(steps, currentRoom);
                break;

            case "so":
                moveSouth(steps, currentRoom);
                break;

            case "we":
                moveWest(steps, currentRoom);
        }

        currentRoom.addTidyUpRobotId(position, id);
    }

    private void moveNorth(int steps, Room currentRoom){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.x, position.y + possibleSteps, "no"))
            possibleSteps++;
        position.y += possibleSteps;
    }

    private void moveEast(int steps, Room currentRoom){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.x + possibleSteps, position.y, "ea"))
            possibleSteps++;
        position.x += possibleSteps;
    }

    private void moveSouth(int steps, Room currentRoom){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.x, position.y - possibleSteps, "so"))
            possibleSteps++;
        position.y -= possibleSteps;
    }

    private void moveWest(int steps, Room currentRoom){
        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.x - possibleSteps, position.y, "we"))
            possibleSteps++;
        position.x -= possibleSteps;
    }

    private Boolean transport(UUID id) {
        Connection connection = getRoomSquareConnection();
        if (connection == null)
            return false;

        UUID sourceId = connection.getSourceId();
        UUID destinationId = connection.getDestinationId();
        Coordinate sourceCoordinate = connection.getSourceCoordinate();
        Coordinate destinationCoordinate = connection.getDestinationCoordinate();
        Room currentRoom = rooms.get(currentRoomId);

        if (id.compareTo(destinationId) == 0) {
            currentRoom.removeTidyUpRobotId(position);
            position = destinationCoordinate;
            currentRoomId = destinationId;

            return true;
        }
        else if (id.compareTo(sourceId) == 0) {
            currentRoom.removeTidyUpRobotId(position);
            position = sourceCoordinate;
            currentRoomId = sourceId;

            return true;
        }
        else
            return false;
    }

    private Boolean set(UUID destinationRoomId) {
        Room currentRoom = rooms.get(destinationRoomId);
        Coordinate position = new Coordinate(0, 0);

        if(currentRoomId == null && !isRoomSquareOccupied(destinationRoomId, position)){
            this.position = position;
            currentRoomId = destinationRoomId;
            currentRoom.addTidyUpRobotId(position, id);
            return true;
        }
        else
            return false;
    }

    private Boolean isRoomSquareOccupied(UUID destinationRoomId, Coordinate coordinate){
        Room currentRoom = rooms.get(destinationRoomId);
        return currentRoom.isSquareOccupied(coordinate);
    }

    private Connection getRoomSquareConnection(){
        Room currentRoom = rooms.get(currentRoomId);
        return currentRoom.getSquareConnection(position);
    }

    public UUID getTidyUpRobotRoomId() {
        return currentRoomId;
    }

    public String getCoordinates() {
        return String.format("(%d,%d)", position.x, position.y);
    }
}
