package thkoeln.st.st2praktikum.exercise;

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
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceId, Barrier barrier) {

        // get the current level to work on
        Spaces currentLevel = Storage.getLevel(spaceId);

        // add the inner barrier for the current level
        currentLevel.createBarrier(barrier, currentLevel);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2D) {

        // create random UUID and portal object
        UUID newPortalID = UUID.randomUUID();
        Portals newPortal = new Portals(newPortalID, sourceSpaceId, destinationSpaceId, sourceVector2D, destinationVector2D);

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
     * This method lets the cleaning device execute a order.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a barrier or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {

        return Storage.moveRobot(cleaningDeviceId, order);
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
     * This method returns the vector2Ds a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector2Ds of the cleaning device
     */
    public Vector2D getVector2D(UUID cleaningDeviceId){

        return Storage.getRobotPos(cleaningDeviceId);


    }
}
