package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.entities.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.entities.Connection;
import thkoeln.st.st2praktikum.exercise.entities.Coordinate;
import thkoeln.st.st2praktikum.exercise.entities.Space;
import thkoeln.st.st2praktikum.exercise.interfaces.WorldStorage;

import java.util.HashMap;
import java.util.UUID;


public class World implements WorldStorage {
    private final HashMap<UUID, Space> spaceMap = new HashMap<>();
    private final HashMap<UUID, Connection> connectionMap = new HashMap<>();
    private final HashMap<UUID, CleaningDevice> cleaningDeviceMap = new HashMap<>();

    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(int height, int width) {
        Space newSpace = new Space(height, width);
        spaceMap.put(newSpace.getId(), newSpace);
        return newSpace.getId();
    }

    /**
     * This method adds a obstacle to a given space.
     *
     * @param spaceId        the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        spaceMap.get(spaceId).addObstacle(obstacle);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     *
     * @param sourceSpaceId         the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationSpaceId    the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, thkoeln.st.st2praktikum.exercise.Coordinate sourceCoordinate, UUID destinationSpaceId, thkoeln.st.st2praktikum.exercise.Coordinate destinationCoordinate) {
        Connection newConnection = new Connection(
                spaceMap.get(sourceSpaceId),
                new Coordinate(sourceCoordinate),
                spaceMap.get(destinationSpaceId),
                new Coordinate(destinationCoordinate)
        );

        connectionMap.put(newConnection.getId(), newConnection);
        return newConnection.getId();
    }

    /**
     * This method adds a new cleaning device
     *
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice newCleaningDevice = new CleaningDevice(name, this);
        cleaningDeviceMap.put(newCleaningDevice.getId(), newCleaningDevice);
        return newCleaningDevice.getId();
    }

    /**
     * This method lets the cleaning device execute a command.
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order    the given command, encoded as a String:
     *                         "[direction, steps]" for movement, e.g. "[we,2]"
     *                         "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another space
     *                         "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the cleaning device hits a obstacle or
     * another cleaning device, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public boolean executeCommand(UUID cleaningDeviceId, Order order) {
        CleaningDevice cleaningDevice = cleaningDeviceMap.get(cleaningDeviceId);

        switch (order.getOrderType()) {
            case ENTER:
                return place(cleaningDevice, order.getGridId().toString());
            case TRANSPORT:
                return transport(cleaningDevice, order.getGridId().toString());
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                cleaningDeviceMap.get(cleaningDeviceId).walkTo(order);
                return true;
            default:
                return false;
        }
    }

    public boolean transport(CleaningDevice cleaningDevice, String destinationSpaceId) {
        for (Connection connection : connectionMap.values()) {
            if (connection.getOriginCoordinate().toString().equals(cleaningDevice.getPosition().toString())) {
                if (connection.getOriginSpace() == cleaningDevice.getSpace()) {
                    if (connection.getDestinationSpace().getId().toString().equals(destinationSpaceId)) {
                        cleaningDevice.setPosition(connection.getDetinationCoordinate());
                        cleaningDevice.setSpace(connection.getDestinationSpace());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean place(CleaningDevice cleaningDevice, String spaceId) {
        Coordinate startingPosition = new Coordinate(0, 0);

        for (Space space : spaceMap.values()) {
            if (space.getId().toString().equals(spaceId)) {
                for (CleaningDevice clDev : cleaningDeviceMap.values()) {
                    if (clDev.getSpace() != null) {
                        if (clDev.getPosition().toString().equals(startingPosition.toString()) && clDev.getSpace().equals(space)) {
                            return false;
                        }
                    }
                }
                cleaningDevice.setSpace(space);
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        return cleaningDeviceMap.get(cleaningDeviceId).getSpace().getId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public thkoeln.st.st2praktikum.exercise.Coordinate getCoordinates(UUID cleaningDeviceId) {
        return new thkoeln.st.st2praktikum.exercise.Coordinate(cleaningDeviceMap.get(cleaningDeviceId).getPosition().getX(), cleaningDeviceMap.get(cleaningDeviceId).getPosition().getY());
    }

    public HashMap<UUID, CleaningDevice> getCleaningDeviceMap() {
        return cleaningDeviceMap;
    }

    public HashMap<UUID, Space> getSpaceMap() {
        return spaceMap;
    }

    public HashMap<UUID, Connection> getConnectionMap() {
        return connectionMap;
    }
}
