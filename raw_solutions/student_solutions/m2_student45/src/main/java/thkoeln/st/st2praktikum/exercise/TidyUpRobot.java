package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

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

    public Boolean execute(Order order) {
        UUID destinationRoomId;
        Integer numberOfSteps;
        Room currentRoom = rooms.get(currentRoomId);
        OrderType orderType = order.getOrderType();

        switch (orderType) {
            case NORTH:
                numberOfSteps = order.getNumberOfSteps();
                moveNorth(numberOfSteps, currentRoom);
                return true;

            case EAST:
                numberOfSteps = order.getNumberOfSteps();
                moveEast(numberOfSteps, currentRoom);
                return true;

            case WEST:
                numberOfSteps = order.getNumberOfSteps();
                moveWest(numberOfSteps, currentRoom);
                return true;

            case SOUTH:
                numberOfSteps = order.getNumberOfSteps();
                moveSouth(numberOfSteps, currentRoom);
                return true;

            case TRANSPORT:
                destinationRoomId = order.getGridId();
                return transport(destinationRoomId);

            case ENTER:
                destinationRoomId = order.getGridId();
                return enter(destinationRoomId);

            default:
                return false;
        }
    }

    private void moveNorth(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX(), position.getY() + possibleSteps, "no"))
            possibleSteps++;

        int x = position.getX();
        int y = position.getY() + possibleSteps;
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private void moveEast(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX() + possibleSteps, position.getY(), "ea"))
            possibleSteps++;

        int x = position.getX() + possibleSteps;
        int y = position.getY();
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private void moveSouth(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX(), position.getY() - possibleSteps, "so"))
            possibleSteps++;

        int x = position.getX();
        int y = position.getY() - possibleSteps;
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private void moveWest(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX() - possibleSteps, position.getY(), "we"))
            possibleSteps++;

        int x = position.getX() - possibleSteps;
        int y = position.getY();
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private Boolean transport(UUID id) {
        Connection connection = getRoomSquareConnection();
        if (connection == null)
            return false;

        UUID firstId = connection.getSourceId();
        UUID secondId = connection.getDestinationId();
        Coordinate firstCoordinate = connection.getSourceCoordinate();
        Coordinate secondCoordinate = connection.getDestinationCoordinate();

        if (id.compareTo(secondId) == 0) {
            return transport(secondId, secondCoordinate);
        }
        else if (id.compareTo(firstId) == 0) {
            return transport(firstId, firstCoordinate);
        }
        else
            return false;
    }

    private Boolean transport(UUID destinationRoomId, Coordinate destinationCoordinate){
        Room currentRoom = rooms.get(currentRoomId);
        Room destinationRoom = rooms.get(destinationRoomId);

        if (!destinationRoom.isSquareOccupied(destinationCoordinate)) {
            currentRoom.removeTidyUpRobotId(position);
            position = destinationCoordinate;
            currentRoomId = destinationRoomId;
            destinationRoom.addTidyUpRobotId(id, destinationCoordinate);
            return true;
        }
        else
            return false;
    }


    private Boolean enter(UUID destinationRoomId) {
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

    public Coordinate getCoordinates() {
        return position;
    }
}
