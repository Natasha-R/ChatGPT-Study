package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class TidyUpRobotService {
    private ArrayList<Roomable> rooms = new ArrayList<>();
    private ArrayList<AbstractRobot> robots = new ArrayList<>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Roomable newRoom = new Room(height, width);
        rooms.add(newRoom);
        return newRoom.getId();
    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID roomId, String obstacleString) {
        ObstacleWallFactory factory = new ObstacleWallFactory();
        Roomable targetRoom = findRoom(roomId);
        targetRoom.addObstacle(factory.createObstacle(targetRoom, obstacleString));
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
        Roomable sourceRoom = findRoom(sourceRoomId);
        Roomable destinationRoom = findRoom(destinationRoomId);
        ConnectionFactory factory = new ConnectionFactory();
        Connectable connection = factory.createConnection(sourceRoom, destinationRoom, sourceCoordinate, destinationCoordinate);
        sourceRoom.addConnection(connection);
        return connection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        AbstractRobot newRobot = new Robot(name);
        robots.add(newRobot);
        return newRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        RobotCommander robotCommander = new RobotCommander(rooms, 0, 0);
        return robotCommander.controlRobot(findRobot(tidyUpRobotId), commandString);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        AbstractRobot robot = findRobot(tidyUpRobotId);
        return robot.getPosition().getRoomID();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        AbstractRobot robot = findRobot(tidyUpRobotId);
        return robot.getPosition().coordinatesToString();
    }

    public void debugPrintAllEntities() {
        System.out.println("--------------------------------------------");
        for (AbstractRobot robot : robots) {
            robot.debugPrintRobotStatus();
        }
        for (Roomable room : rooms) {
            room.debugPrintRoomStatus();
        }
        System.out.println("--------------------------------------------");
    }

    private Roomable findRoom(UUID roomID){
        for (Roomable room : rooms) {
            if (room.getId().equals(roomID))
                return room;
        }
        return null;
    }

    private AbstractRobot findRobot(UUID robotID){
        for (AbstractRobot robot : robots) {
            if (robot.getId().equals(robotID))
                return robot;
        }
        return null;
    }
}
