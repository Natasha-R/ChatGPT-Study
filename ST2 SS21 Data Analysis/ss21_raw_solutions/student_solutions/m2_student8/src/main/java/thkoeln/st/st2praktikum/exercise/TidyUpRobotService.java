package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.connection.Connectable;

import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.robot.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.robot.Transportable;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.Roomable;

import java.util.UUID;

public class TidyUpRobotService {

    private RepositorySimulator simulator = new RepositorySimulator();
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Roomable room = new Room(width,height);
        this.simulator.addRoom(room);
        return room.identify();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall)
    {
        this.simulator.getRoomByID(roomId).addWallToRoom(wall);
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate)
    {
        Roomable sourceRoom = simulator.getRoomByID(sourceRoomId);
        Roomable destinationRoom = simulator.getRoomByID(destinationRoomId);
        Connectable connection = new Connection(sourceRoom,sourceCoordinate,destinationRoom,destinationCoordinate);
        this.simulator.getRoomByID(sourceRoomId).addRoomConnection(connection);
        this.simulator.addConnection(connection);
        return connection.identify();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        Transportable robot = new TidyUpRobot(name);
        this.simulator.addRobot(robot);
        return robot.identify();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task)
    {

        Transportable robot = simulator.getRobotByID(tidyUpRobotId);
        UUID roomID = task.getGridId();
        Roomable sourceRoom = robot.showRoom();
        Connectable connection = null;

        if (sourceRoom != null)
            connection  = simulator.getConnectionByID(sourceRoom.hasConnectionToRoom(roomID));

        if (roomID != null)
            this.simulator.getRoomByID(roomID).addRobotToRoom(robot);

        Roomable room = simulator.getRoomByID(roomID);
        return this.simulator.getRobotByID(tidyUpRobotId).moveInDirection(task.getTaskType(),task.getNumberOfSteps(),connection,room);

    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return this.simulator.getRobotByID(tidyUpRobotId).showTransportableRoomID();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId){
        return this.simulator.getRobotByID(tidyUpRobotId).showActualPosition();
    }
}
