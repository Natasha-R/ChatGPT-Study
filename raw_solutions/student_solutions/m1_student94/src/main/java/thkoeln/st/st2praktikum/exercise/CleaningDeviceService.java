package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.cleaningDevice.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.Instructable;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.inner.CleaningDeviceMovement;
import thkoeln.st.st2praktikum.exercise.map.Map;
import thkoeln.st.st2praktikum.exercise.map.Operable;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstaclePassable;
import thkoeln.st.st2praktikum.exercise.space.Space;
import thkoeln.st.st2praktikum.exercise.space.SpaceManageable;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

import java.util.UUID;

public class CleaningDeviceService {
    public final Operable map=new Map();

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        SpaceManageable space= new Space(height,width);
        map.addSpace(space);
        return space.getId();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceId, String obstacleString) {
        ObstaclePassable obstacle=new Obstacle(obstacleString, (Walkable)map.getSpaceByItsId(spaceId));
        map.getSpaceByItsId(spaceId).addObstacle(obstacle);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        Connectable connection= new Connection((Walkable)map.getSpaceByItsId(sourceSpaceId),sourceCoordinate,(Walkable)map.getSpaceByItsId(destinationSpaceId),destinationCoordinate );
        return map.getSpaceByItsId(sourceSpaceId).addConnection(connection);

    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        Instructable cleaningDevice = new CleaningDevice(name);
        map.addCleaningDevice(cleaningDevice);
        return cleaningDevice.getId();

    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {

        CleaningDeviceMovement cleaningDeviceMovement= new CleaningDeviceMovement(commandString,map);
        return map.getCleaningDeviceByItsId(cleaningDeviceId).analysedCommand(cleaningDeviceMovement);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return map.getCleaningDeviceByItsId(cleaningDeviceId).getSpace().getId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){
        return map.getCleaningDeviceByItsId(cleaningDeviceId).getPosition().toString();
    }
}
