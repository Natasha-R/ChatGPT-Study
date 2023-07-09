package thkoeln.st.st2praktikum.exercise;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class CleaningDeviceService {

    PersistentData Storage = new PersistentData();
    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {

        UUID newRoom= UUID.randomUUID();
        Spaces newLvl = new Spaces(height, width, newRoom);

        // create a new level
        Storage.storeNewLevel(newLvl);

        // crate outta barriers
        newLvl.createOuttaWall(Storage.getLevel(newRoom));
        return newRoom;
    }

    /**
     * This method adds a barrier to a given space.
     * @param spaceId the ID of the space the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID spaceId, String barrierString) {

        // get the current level to work on
        Spaces currentLevel = Storage.getLevel(spaceId);

        // add the inner barrier for the current level
        currentLevel.createBarrier(Storage.getIntWallPositons(barrierString), currentLevel);

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

        // create random UUID and portal object
        UUID newPortalID = UUID.randomUUID();
        Portals newPortal = new Portals(newPortalID, sourceSpaceId, destinationSpaceId, sourceCoordinate, destinationCoordinate);

        // store portal object in persistant arraylist
        Storage.portal.add(newPortal);

        // store info of portal in the according level fields with matching destination
        Storage.getLevel(sourceSpaceId).singleField[newPortal.SourceCoordX][newPortal.SourceCoordY].isPortal = true;
        Storage.getLevel(sourceSpaceId).singleField[newPortal.SourceCoordX][newPortal.SourceCoordY].destination = newPortal.DestinationLvl;
        Storage.getLevel(sourceSpaceId).singleField[newPortal.SourceCoordX][newPortal.SourceCoordY].portalID = Storage.getPortal(newPortalID).portalID;

        Storage.getLevel(destinationSpaceId).singleField[newPortal.DestinationCoordX][newPortal.DestinationCoordY].isPortal = true;
        Storage.getLevel(destinationSpaceId).singleField[newPortal.DestinationCoordX][newPortal.DestinationCoordY].destination = sourceSpaceId;
        Storage.getLevel(destinationSpaceId).singleField[newPortal.DestinationCoordX][newPortal.DestinationCoordY].portalID = Storage.getPortal(newPortalID).portalID;

        return newPortalID;
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {

        UUID newRobotID = UUID.randomUUID();
        Robots newRobot = new Robots(newRobotID, name);

        Storage.robot.add(newRobot);

        return newRobotID;
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a barrier or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {

        boolean success = Storage.moveRobot(cleaningDeviceId, commandString);

        return success;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){

        Robots robot = Storage.getRobot(cleaningDeviceId);

        return robot.positionZ;
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){

        Robots robot = Storage.getRobot(cleaningDeviceId);

        return "(" + robot.positionX + "," + robot.positionY + ")";
    }
}
