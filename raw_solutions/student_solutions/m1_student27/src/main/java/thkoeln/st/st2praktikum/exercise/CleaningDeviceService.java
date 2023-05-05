package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.entyties.*;
import thkoeln.st.st2praktikum.exercise.services.ConnectionService;
import thkoeln.st.st2praktikum.exercise.services.SpaceService;
import thkoeln.st.st2praktikum.exercise.services.ObstacleService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CleaningDeviceService {

    SpaceService spaceService = new SpaceService();
    ObstacleService obstacleService = new ObstacleService();
    ConnectionService connectionService = new ConnectionService();


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
        Space space = spaceService.addSpace(height, width);
        spaceList.add(space);
        return space.getSpaceId();
    }


    public void addObstacle(UUID spaceId, String obstacleString) {
        Obstacle obstacle=obstacleService.addObstacle(spaceId, obstacleString);
        Space space=getSpace(spaceId);
        space.getObstacleList().add(obstacle);



    }


    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        Connection connection = connectionService.addConnection(sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);
        connectionList.add(connection);
        return connection.getConnectionId();
    }


    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleaningDeviceList.add(cleaningDevice);
        return cleaningDevice.getCleaningDeviceId();
    }


    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {

        // interupt the command
        String commandparts[] = commandString.replace("[", "")
                .replace("]", "").split(",");
        String commandInstruction = commandparts[0];
        String commandvalue = commandparts[1];
        boolean isExecuted = false;
        switch (commandInstruction) {
            case "en":
                isExecuted = initialCleaningDevice(cleaningDeviceId, commandvalue);
                break;
            case "tr":
                isExecuted = transportCleaningDevice(cleaningDeviceId, commandvalue);
                break;
            case "no":
                up_and_down(getCleaningDevice(cleaningDeviceId), Integer.parseInt(commandvalue), 1);
                break;
            case "so":
                up_and_down(getCleaningDevice(cleaningDeviceId), Integer.parseInt(commandvalue), -1);
                break;
            case "ea":
                right_and_left(getCleaningDevice(cleaningDeviceId), Integer.parseInt(commandvalue), 1);
                break;
            case "we":
                right_and_left(getCleaningDevice(cleaningDeviceId), Integer.parseInt(commandvalue), -1);
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
        Space space =getSpace(UUID.fromString(spaceId));
        for (CleaningDevice cleaningDevice : space.getCleaningDeviceList())
                    if (cleaningDevice.getPosition().getX() == x
                            && cleaningDevice.getPosition().getY() == y)
                        return false;

        return true;
    }

    public boolean transportCleaningDevice(UUID cleaningDeviceId, String spaceId) {


        CleaningDevice cleaningDevice =getCleaningDevice(cleaningDeviceId);
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
        int right = 0;// variable to correct numbers between coordinate x and Obstacle x;
        if (right_or_left > 0) right = 1;
        boolean can_move = true;
        for (int i = 1; i <= movment; i++) {
            can_move = true;
            if (cleaningDevice.getPosition().getX() + right_or_left <= space.getWidth() - 1
                    && cleaningDevice.getPosition().getX() + right_or_left >= 0) {
                for (Obstacle obstacle : space.getObstacleList()) {
                    if (obstacle.getStart().getX() == obstacle.getEnd().getX()) {
                        if (cleaningDevice.getPosition().getX() + right == obstacle.getStart().getX()
                                && cleaningDevice.getPosition().getY() >= obstacle.getStart().getY()
                                && cleaningDevice.getPosition().getY() < obstacle.getEnd().getY()) {
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


        int up = 0;// variable to correct numbers between coordinate y and obstacle y;
        if (up_or_down > 0) up = 1;
        boolean can_move = true;

        for (int i = 1; i <= movment; i++) {
            can_move = true;
            if (cleaningDevice.getPosition().getY() + up_or_down <= space.getHeight() - 1
                    && cleaningDevice.getPosition().getY() + up_or_down >= 0) {
                for (Obstacle obstacle : space.getObstacleList()) {
                    if (obstacle.getStart().getY() == obstacle.getEnd().getY()) {
                        if (cleaningDevice.getPosition().getY() + up == obstacle.getStart().getY()
                                && cleaningDevice.getPosition().getX() >= obstacle.getStart().getX()
                                && cleaningDevice.getPosition().getX() < obstacle.getEnd().getX()) {
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
    public String getCoordinates(UUID cleaningDeviceId) {
        for (CleaningDevice cleaningDevice : cleaningDeviceList)
            if (cleaningDevice.getCleaningDeviceId().equals(cleaningDeviceId))
                return cleaningDevice.getPosition().toString();


        throw new UnsupportedOperationException();
    }


}
