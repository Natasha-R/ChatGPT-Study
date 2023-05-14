package thkoeln.st.st2praktikum.exercise;


import thkoeln.st.st2praktikum.servicies.SpaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CleaningDeviceService {


    private final List<CleaningDevice> cleaningDevices = new ArrayList<>();
    private final SpaceService spaceService = new SpaceService();


    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        return spaceService.creatSpace(height, width);
    }

    /**
     * This method adds a wall to a given space.
     *
     * @param spaceId    the ID of the space the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceId, String wallString) {
        spaceService.addWall(spaceId, wallString);
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
    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        return spaceService.addConnection(sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);

    }

    /**
     * This method adds a new cleaning device
     *
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleaningDevices.add(cleaningDevice);
        return cleaningDevice.getCleaningDeviceId();
    }

    /**
     * This method lets the cleaning device execute a command.
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString    the given command, encoded as a String:
     *                         "[direction, steps]" for movement, e.g. "[we,2]"
     *                         "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another space
     *                         "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the cleaning device hits a wall or
     * another cleaning device, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
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
            default:
                moveCleaningDevice(getCleaningDevice(cleaningDeviceId), Integer.parseInt(commandvalue),  commandInstruction);


        }


        return isExecuted;
    }

    // put the dvice in given place
    public boolean initialCleaningDevice(UUID cleaningDeviceId, String spaceId) {
        final String cornerPoint = "(0,0)";
        Point point = new Point(cornerPoint);
      Space space=spaceService.getSpaceById(UUID.fromString(spaceId));

        // check if the given place is empty
        if(!space.isCoordinateExistAndEmpty(new Point(0,0),"en"))return false;
        CleaningDevice cleaningDevice = getCleaningDevice(cleaningDeviceId);
        cleaningDevice.setPosition(point);
        cleaningDevice.setSpaceId(UUID.fromString(spaceId));
        space.getCleaningDevices().put(cleaningDevice.getCleaningDeviceId(), cleaningDevice);

        return true;
    }

    /*this method to transport CleaningDevice  from it's space to anthor
     * @param cleaningDeviceId tranceprted device id
     * @param sourceSpaceId         the ID of the space where the cleaningDevice will transport to
     */
    public boolean transportCleaningDevice(UUID cleaningDeviceId, String spaceId) {
        return spaceService.transportCleaningDevice(getCleaningDevice(cleaningDeviceId), spaceId);
    }




    private boolean moveCleaningDevice(CleaningDevice cleaningDevice, int steps, String direction) {


        Space space = spaceService.getSpaceById(cleaningDevice.getSpaceId());

        for (int i = 1; i <= steps; i++) {
            if (!space.isCoordinateExistAndEmpty(cleaningDevice.getPosition(), direction))    return false;
            for (Wall wall : space.getWalls()) if (wall.isCross(cleaningDevice.getPosition(), direction)) return false;

            cleaningDevice.move(direction);

        }
        return true;
    }


    // method to find CleaningDevice by id
    private CleaningDevice getCleaningDevice(UUID cleaningDeviceId) {
        for (CleaningDevice cleaningDevice : cleaningDevices)
            if (cleaningDevice.getCleaningDeviceId().equals(cleaningDeviceId))
                return cleaningDevice;

        // throw if CleaningDevice not found
        throw new UnsupportedOperationException();
    }


    /**
     * This method returns the space-ID a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        for (CleaningDevice cleaningDevice : cleaningDevices)
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
        for (CleaningDevice cleaningDevice : cleaningDevices)
            if (cleaningDevice.getCleaningDeviceId().equals(cleaningDeviceId))
                return cleaningDevice.getPosition().toString();


        throw new UnsupportedOperationException();
    }


}
