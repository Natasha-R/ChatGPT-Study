package thkoeln.st.st2praktikum.exercise;

import java.util.Iterator;
import java.util.UUID;

public class CleaningDeviceService {
    UUIDHashMap<IMovable> mapOfMovables = new UUIDHashMap<>();
    UUIDHashMap<Space> mapOfSpaces = new UUIDHashMap<>();

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space newSpace = new Space(height, width);
        this.mapOfSpaces.add(newSpace);

        return newSpace.getUUID();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Space fetchedSpace = this.mapOfSpaces.get(spaceId);
        fetchedSpace.addObstacleToSpace(obstacle);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, Point sourcePoint, UUID destinationSpaceId, Point destinationPoint) {
        Space sourceSpace = this.mapOfSpaces.get(sourceSpaceId);
        Space destinationSpace = this.mapOfSpaces.get(destinationSpaceId);

        return sourceSpace.addConnectionToSpace(sourcePoint, destinationSpace, destinationPoint);
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        this.mapOfMovables.add(cleaningDevice);

        return cleaningDevice.getUUID();
    }

    /**
     * This method lets the cleaning device execute a task.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        IMovable cleaningDevice = this.mapOfMovables.get(cleaningDeviceId);

        // Als Sonderfall betrachten, da sonst zirkuläre Abh. bestehen würde
        if(task.getTaskType().equals(TaskType.ENTER))
        {
            UUID spaceId = task.getGridId();

            Iterator<IMovable> movableIterator = this.mapOfMovables.iterator();
            IMovable someDevice;

            while(movableIterator.hasNext()) {
                someDevice = movableIterator.next();

                if(null == someDevice.getSpace() || !someDevice.getSpace().getUUID().equals(spaceId))
                {
                    continue;
                }

                if(!someDevice.getPosition().equals(new Point(0, 0)))
                {
                    continue;
                }

                return false;
            }

            Space enterSpace = this.mapOfSpaces.get(spaceId);
            cleaningDevice.setSpace(enterSpace);

            return true;
        }


        return cleaningDevice.executeTask(task);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        return this.mapOfMovables.get(cleaningDeviceId).getSpace().getUUID();
    }

    /**
     * This method returns the points a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the points of the cleaning device
     */
    public Point getPoint(UUID cleaningDeviceId){
        return this.mapOfMovables.get(cleaningDeviceId).getPosition();
    }
}
