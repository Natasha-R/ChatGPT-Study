package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.entities.*;
import thkoeln.st.st2praktikum.exceptions.UnknownActionException;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobotService {

    private HashMap<UUID, Room> rooms = new HashMap<>();
    private HashMap<UUID, TidyUpRobot> tidyUpRobots = new HashMap<>();
    private HashMap<UUID, Connection> connections = new HashMap<>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(height, width);
        rooms.put(newRoom.getUuid(), newRoom);
        return newRoom.getUuid();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spawns from (2,3) to (10,3)
     */
    public void addWall(UUID roomId, String wallString) {
        Room room = rooms.get(roomId);
        room.addHitAble(new Wall(wallString));
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        Cell sourceCell = new Cell(sourceCoordinate);
        Cell destinationCell = new Cell(destinationCoordinate);
        Room sourceRoom = rooms.get(sourceRoomId);
        Room destinationRoom = rooms.get(destinationRoomId);

        Connection newConnection = new Connection(sourceRoom, sourceCell, destinationRoom, destinationCell);
        sourceRoom.getUseAbles().add(newConnection);
        connections.put(newConnection.getUuid(), newConnection);
        return newConnection.getUuid();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newRobot = new TidyUpRobot(name);
        tidyUpRobots.put(newRobot.getUuid(), newRobot);
        return newRobot.getUuid();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        Command command = new Command(commandString);
        TidyUpRobot tidyUpRobot = tidyUpRobots.get(tidyUpRobotId);
        switch (command.getAction()){
            case "no":
            case "so":
            case "we":
            case "ea":
                tidyUpRobot.go(commandString);
                return true;
            case "en":
                Room initialRoom = rooms.get(UUID.fromString(command.getArgument()));
                return tidyUpRobot.init(initialRoom);
            case "tr":
                Room destinationRoom = rooms.get(UUID.fromString(command.getArgument()));
                return tidyUpRobot.transport(destinationRoom);
            default:
                throw new UnknownActionException();
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobots.get(tidyUpRobotId).getRoom().getUuid();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        return tidyUpRobots.get(tidyUpRobotId).getPosition().toString();
    }
}

