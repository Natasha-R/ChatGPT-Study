package thkoeln.st.st2praktikum.exercise.Space;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle.CleaningDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CleaningDeviceTransporter {

    private final Map<Space, List<Connection>> connectionMap = new HashMap<>();

    public void addConnection (Connection connection) {
        Space sourceSpace = connection.getSourceSpace();
        if (!connectionMap.containsKey(sourceSpace)) {
            connectionMap.put(sourceSpace, new ArrayList<>());
        }
        connectionMap.get(sourceSpace).add(connection);
    }

    public Boolean transportCleaningDevice (CleaningDevice device, Space destinationSpace) {
        Space sourceSpace = device.getSpace();
        Coordinate sourceCoordinate = device.getPosition();

        List<Connection> spaceConnectionList = connectionMap.get(sourceSpace);
        if (spaceConnectionList == null) return false;

        for (Connection connection : spaceConnectionList) {
            if (connection.getEntry().equals(sourceCoordinate) &&
                connection.getDestinationSpace().equals(destinationSpace)) {
                return device.transportToSpace(destinationSpace, connection.getExit());
            }
        }
        return false;
    }
}
