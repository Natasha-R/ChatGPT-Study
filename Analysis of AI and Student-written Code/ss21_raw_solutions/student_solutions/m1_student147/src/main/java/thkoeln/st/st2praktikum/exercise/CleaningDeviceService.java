package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class CleaningDeviceService {

    private HashMap<UUID, CleaningDevice> cleaningDevices;
    private HashMap<UUID, Space> spaces;

    public CleaningDeviceService(){
        cleaningDevices = new HashMap<UUID, CleaningDevice>();
        spaces = new HashMap<UUID, Space>();
    }

    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width);
        UUID id = space.getId();

        spaces.put(id, space);

        return id;
    }


    public void addObstacle(UUID spaceId, String obstacleString) {
        Space space = spaces.get(spaceId);
        space.addObstacle(obstacleString);
    }


    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        Connection connection = new Connection(sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);
        Space sourceSpace = spaces.get(sourceSpaceId);
        Space destinationSpace = spaces.get(destinationSpaceId);

        sourceSpace.addConnection(connection);
        destinationSpace.addConnection(connection);

        return connection.getId();
    }


    public UUID addCleaningDevice (String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name, spaces);
        UUID id = cleaningDevice.getId();

        cleaningDevices.put(id, cleaningDevice);

        return id;
    }

    public Boolean executeCommand(UUID cleaningDeviceSpaceId, String commandString) {
        CleaningDevice cleaningDevice = cleaningDevices.get(cleaningDeviceSpaceId);
        return cleaningDevice.execute(commandString);
    }

    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceSpaceId){
        CleaningDevice cleaningDevice  = cleaningDevices.get(cleaningDeviceSpaceId);
        return cleaningDevice.getCleaningDeviceSpaceId();
    }

    public String getCoordinates(UUID cleaningDeviceSpaceId){
        CleaningDevice cleaningDevice = cleaningDevices.get(cleaningDeviceSpaceId);
        return cleaningDevice.getCoordinates();
    }
}
