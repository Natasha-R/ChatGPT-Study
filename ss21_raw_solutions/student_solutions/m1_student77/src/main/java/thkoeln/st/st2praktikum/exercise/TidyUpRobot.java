package thkoeln.st.st2praktikum.exercise;

import org.modelmapper.internal.Pair;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobot {
    private UUID id;
    private String name;
    private HashMap<UUID, Room> rooms;
    private UUID currentRoomId;
    private Coordinate position;

    public TidyUpRobot(String name, HashMap<UUID, Room> rooms) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.rooms = rooms;
    }

    public Boolean execute(String commandString) {
        Pair commandPair = this.getCommandPair(commandString);
        String direction = (String)commandPair.getLeft();
        String content = (String)commandPair.getRight();

        UUID destinationRoomId;
        switch(direction) {
            case "no":
            case "ea":
            case "we":
            case "so":
                int steps = Integer.parseInt(content);
                this.move(direction, steps);
                return true;
            case "tr":
                destinationRoomId = UUID.fromString(content);
                return this.transport(destinationRoomId);
            case "en":
                destinationRoomId = UUID.fromString(content);
                return this.set(destinationRoomId);
            default:
                return false;
        }
    }

    private Pair getCommandPair(String commandString) {
        String str = commandString.replace("[", "").replace("]", "");
        String[] command = str.split(",");
        return Pair.of(command[0], command[1]);
    }

    private void move(String direction, int steps) {
        Room currentRoom = (Room)this.rooms.get(this.currentRoomId);
        currentRoom.removeTidyUpRobotId(this.position);
        switch(direction) {
            case "no":
                this.moveNorth(steps, currentRoom);
                break;
            case "ea":
                this.moveEast(steps, currentRoom);
                break;
            case "so":
                this.moveSouth(steps, currentRoom);
                break;
            case "we":
                this.moveWest(steps, currentRoom);
        }
        currentRoom.addTidyUpRobotId(this.position, this.id);
    }

    private void moveNorth(int steps, Room currentRoom) {
        int i =0;
        while(i <steps&&currentRoom.barrierCheck(this.position.getX(), this.position.getY() + i, "no")) {
            i++;
        }
        this.position.setY(this.position.getY() + i);
    }


    private void moveEast(int steps, Room currentRoom) {
        int i =0;
            while (i < steps && currentRoom.barrierCheck(this.position.getX() + i, this.position.getY(), "ea")) {
                i++;
            }
            this.position.setX(this.position.getX() + i);
    }

    private void moveSouth(int steps, Room currentRoom) {
        int i =0;
        while(i <steps&&currentRoom.barrierCheck(this.position.getX(), this.position.getY() - i, "so")) {
            i++;
        }
        this.position.setY(this.position.getY() - i);
    }

    private void moveWest(int steps, Room currentRoom) {
        int i =0;
        while(i <steps&&currentRoom.barrierCheck(this.position.getX()-i, this.position.getY() , "we")) {
            i++;
        }
        this.position.setX(this.position.getX() - i);
    }

    private Boolean transport(UUID id) {
        Connection thisConnection = this.getRoomSquareConnection();
        if (thisConnection == null) {
            return false;
        } else {
            UUID sourceId = thisConnection.getSourceId();
            UUID destinationId = thisConnection.getDestinationId();
            Coordinate sourceCoordinate = thisConnection.getSourceCoordinate();
            Coordinate destinationCoordinate = thisConnection.getDestinationCoordinate();
            Room currentRoom = this.rooms.get(this.currentRoomId);
            if (id.compareTo(destinationId) == 0) {
                currentRoom.removeTidyUpRobotId(this.position);
                this.currentRoomId = destinationId;
                this.position = destinationCoordinate;
                return true;
            } else if (id.compareTo(sourceId) == 0) {
                currentRoom.removeTidyUpRobotId(this.position);
                this.currentRoomId = sourceId;
                this.position = sourceCoordinate;
                return true;
            } else {
                return false;
            }
        }
    }

    private Boolean set(UUID destinationRoomId) {
        Room currentRoom = this.rooms.get(destinationRoomId);
        Coordinate position = new Coordinate(0, 0);
        if (this.currentRoomId == null && !this.isRoomSquareOccupied(destinationRoomId, position)) {
            this.position = position;
            this.currentRoomId = destinationRoomId;
            currentRoom.addTidyUpRobotId(position, this.id);
            return true;
        } else {
            return false;
        }
    }

    private Boolean isRoomSquareOccupied(UUID destinationRoomId, Coordinate coordinate) {
        Room currentRoom = this.rooms.get(destinationRoomId);
        return currentRoom.isSquareOccupied(coordinate);
    }

    private Connection getRoomSquareConnection() {
        Room currentRoom = this.rooms.get(this.currentRoomId);
        return currentRoom.getSquareConnection(this.position);
    }

    public UUID getTidyUpRobotRoomId() {
        return this.currentRoomId;
    }

    public String getCoordinates() {
        return String.format("(%d,%d)", this.position.getX(), this.position.getY());
    }

    public UUID getId() {
        return this.id;
    }
    }


