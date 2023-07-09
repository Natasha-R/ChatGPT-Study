package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Exception.ConnectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConnectionNetwork {

    private final Map<Space, List<Connection>> connectionMap = new HashMap<>();

    public void addConnection (Connection connection) {
        Space sourceSpace = connection.getSourceSpace();
        if (!connectionMap.containsKey(sourceSpace)) {
            connectionMap.put(sourceSpace, new ArrayList<>());
        }
        connectionMap.get(sourceSpace).add(connection);
    }

    public Vector2D getExit (Space sourceSpace, Vector2D entry, Space destinationSpace) {
        ConnectionException notFound =
                new ConnectionException("Keine passende Connection gefunden");

        List<Connection> spaceConnectionList = connectionMap.get(sourceSpace);
        if (spaceConnectionList == null) throw notFound;

        for (Connection connection : spaceConnectionList) {
            if (connection.getEntry().equals(entry) &&
                connection.getDestinationSpace().equals(destinationSpace)) {
                return connection.getExit();
            }
        }

        throw notFound;
    }
}
