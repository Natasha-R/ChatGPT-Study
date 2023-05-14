package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CleaningDeviceService {


    // lists for all created objects
    List<Space> spaceList = new ArrayList<>();
    List<Connection> connectionList = new ArrayList<>();
    List<CleaningDevice> cleaningDeviceList = new ArrayList<>();


    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width);
        spaceList.add(space);
        return space.getSpaceId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall    the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
testSpaceBounds(spaceId,wall.getStart());
testSpaceBounds(spaceId, wall.getEnd());
        Space space = getSpace(spaceId);
        space.getWallList().add(wall);
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
       testSpaceBounds( sourceSpaceId,  sourcePoint);
       testSpaceBounds( destinationSpaceId,  destinationPoint);




        Connection connection = new Connection(sourceSpaceId, sourcePoint
                , destinationSpaceId, destinationPoint);
        connectionList.add(connection);
        return connection.getConnectionId();
    }

    private void testSpaceBounds(UUID spaceId, Point point) {
    Space space=getSpace(spaceId);
    if(point.getX()>=space.width||point.getY()>=space.height)
        throw new UnsupportedOperationException();
    }

    /**
     * This method adds a new cleaning device
     *
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleaningDeviceList.add(cleaningDevice);
        return cleaningDevice.getCleaningDeviceId();
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
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {

        // interupt the command

        boolean isExecuted = false;
        switch (task.getTaskType()) {
            case ENTER:
                isExecuted = initialCleaningDevice(cleaningDeviceId, task.getGridId().toString());
                break;
            case TRANSPORT:
                isExecuted = transportCleaningDevice(cleaningDeviceId, task.getGridId().toString());
                break;
            case NORTH:
                up_and_down(getCleaningDevice(cleaningDeviceId), task.getNumberOfSteps(), 1);
                break;
            case SOUTH:
                up_and_down(getCleaningDevice(cleaningDeviceId), task.getNumberOfSteps(), -1);
                break;
            case EAST:
                right_and_left(getCleaningDevice(cleaningDeviceId),task.getNumberOfSteps(), 1);
                break;
            case WEST:
                right_and_left(getCleaningDevice(cleaningDeviceId), task.getNumberOfSteps(), -1);
                break;

        }


        return isExecuted;
    }

    // put the dvice in given place
    public boolean initialCleaningDevice(UUID cleaningDeviceId, String spaceId) {
        Point point = new Point(0, 0);
        // check if the given place is empty
        if (!isCoordinateEmpty(spaceId, 0, 0)) return false;
        for (CleaningDevice cleaningDevice : cleaningDeviceList)
            if (cleaningDevice.getCleaningDeviceId().equals(cleaningDeviceId)) {
                cleaningDevice.setSpaceId(UUID.fromString(spaceId));
                cleaningDevice.setPosition(point);
                for (Space space : spaceList)
                    if (space.getSpaceId().equals(UUID.fromString(spaceId)))
                        space.getCleaningDeviceList().add(cleaningDevice);

                return true;
            }

        return false;
    }

    /*this method to check if coordinate is empty
     * @param sourceSpaceId         the ID of the space where the coordinate is located
     * @param x and y  the cordinate location
     */
    public boolean isCoordinateEmpty(String spaceId, int x, int y) {
        Space space = getSpace(UUID.fromString(spaceId));
        for (CleaningDevice cleaningDevice : space.getCleaningDeviceList())
            if (cleaningDevice.getPosition().getX() == x
                    && cleaningDevice.getPosition().getY() == y)
                return false;

        return true;
    }

    /*this method to transport CleaningDevice  from it's space to anthor
     * @param cleaningDeviceId tranceprted device id
     * @param sourceSpaceId         the ID of the space where the cleaningDevice will transport to
     */
    public boolean transportCleaningDevice(UUID cleaningDeviceId, String spaceId) {


        CleaningDevice cleaningDevice = getCleaningDevice(cleaningDeviceId);
        for (Connection connection : connectionList) {
            if (connection.getSourceSpaceId().equals(cleaningDevice.getSpaceId())
                    && connection.getDestinationSpaceId().equals(UUID.fromString(spaceId))
                    && cleaningDevice.getPosition().getX() == connection.getSourceCoordinate().getX()
                    && cleaningDevice.getPosition().getY() == connection.getSourceCoordinate().getY()
                    && isCoordinateEmpty(spaceId, connection.getDestinationCoordinate().getX(),
                    connection.getDestinationCoordinate().getY())
            ) {
                cleaningDevice.setSpaceId(UUID.fromString(spaceId));
                cleaningDevice.getPosition().setX(connection.getDestinationCoordinate().getX());
                cleaningDevice.getPosition().setY(connection.getDestinationCoordinate().getY());
                return true;

            }

        }


        return false;
    }


    /*this method to to move the cleaningDevice horizontal
     * @param cleaningDevice  the device to move
     * @param movment number of steps
     * @param right_or_left move direction
     */
    private boolean right_and_left(CleaningDevice cleaningDevice, int movment, int right_or_left) {
        Space space = getSpace(cleaningDevice.getSpaceId());
        if (!isCoordinateEmpty(space.getSpaceId().toString(),
                cleaningDevice.getPosition().getX() + right_or_left, cleaningDevice.getPosition().getY())) return false;
        int right = 0;// variable to correct numbers between coordinate x and wall x;
        if (right_or_left > 0) right = 1;
        boolean can_move = true;
        for (int i = 1; i <= movment; i++) {
            can_move = true;
            if (cleaningDevice.getPosition().getX() + right_or_left <= space.getWidth() - 1
                    && cleaningDevice.getPosition().getX() + right_or_left >= 0) {
                for (Wall wall : space.getWallList()) {
                    if (wall.getStart().getX() == wall.getEnd().getX()) {
                        if (cleaningDevice.getPosition().getX() + right == wall.getStart().getX()
                                && cleaningDevice.getPosition().getY() >= wall.getStart().getY()
                                && cleaningDevice.getPosition().getY() < wall.getEnd().getY()) {
                            can_move = false;
                            break;
                        }
                    }
                }
            } else can_move = false;
            if (can_move) cleaningDevice.getPosition().setX(cleaningDevice.getPosition().getX() + right_or_left);
            else break;
        }


        return can_move;
    }
    /*this method to to move the cleaningDevice  vertical
     * @param cleaningDevice  the device to move
     * @param movment number of steps
     * @param up_or_down move direction
     */

    private boolean up_and_down(CleaningDevice cleaningDevice, int movment, int up_or_down) {
        Space space = getSpace(cleaningDevice.getSpaceId());
        if (!isCoordinateEmpty(space.getSpaceId().toString(),
                cleaningDevice.getPosition().getX(), cleaningDevice.getPosition().getY() + up_or_down)) return false;


        int up = 0;// variable to correct numbers between coordinate y and wall y;
        if (up_or_down > 0) up = 1;
        boolean can_move = true;

        for (int i = 1; i <= movment; i++) {
            can_move = true;
            if (cleaningDevice.getPosition().getY() + up_or_down <= space.getHeight() - 1
                    && cleaningDevice.getPosition().getY() + up_or_down >= 0) {
                for (Wall wall : space.getWallList()) {
                    if (wall.getStart().getY() == wall.getEnd().getY()) {
                        if (cleaningDevice.getPosition().getY() + up == wall.getStart().getY()
                                && cleaningDevice.getPosition().getX() >= wall.getStart().getX()
                                && cleaningDevice.getPosition().getX() < wall.getEnd().getX()) {
                            can_move = false;
                            break;
                        }
                    }
                }
            } else can_move = false;
            if (can_move) cleaningDevice.getPosition().setY(cleaningDevice.getPosition().getY() + up_or_down);
            else break;
        }
        return can_move;
    }

    // method to find CleaningDevice by id
    private CleaningDevice getCleaningDevice(UUID cleaningDeviceId) {
        for (CleaningDevice cleaningDevice : cleaningDeviceList)
            if (cleaningDevice.getCleaningDeviceId().equals(cleaningDeviceId))
                return cleaningDevice;
        return null;
    }
    // method to find Space by id

    private Space getSpace(UUID spaceId) {
        for (Space space : spaceList)
            if (space.getSpaceId().equals(spaceId))
                return space;
        return null;

    }


    /**
     * This method returns the space-ID a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        for (CleaningDevice cleaningDevice : cleaningDeviceList)
            if (cleaningDevice.getCleaningDeviceId().equals(cleaningDeviceId))
                return cleaningDevice.getSpaceId();


        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public Point getPoint(UUID cleaningDeviceId) {
        for (CleaningDevice cleaningDevice : cleaningDeviceList)
            if (cleaningDevice.getCleaningDeviceId().equals(cleaningDeviceId))
                return cleaningDevice.getPosition();


        throw new UnsupportedOperationException();
    }


}
