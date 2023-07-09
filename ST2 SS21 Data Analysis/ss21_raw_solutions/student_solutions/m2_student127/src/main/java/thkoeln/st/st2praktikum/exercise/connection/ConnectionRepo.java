package thkoeln.st.st2praktikum.exercise.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConnectionRepo {
    private final HashMap<UUID, Connection> connections;

    public void add(Connection newConnection) {
        connections.put(newConnection.getId(), newConnection);
    }
    public void removeById(UUID connectionId) {
        connections.remove(connectionId);
    }

    public Connection findById(UUID connectionId) {
        return connections.get(connectionId);
    }
    public List<Connection> findByRoomId (UUID roomId) {
        List<Connection> newList = new ArrayList<>();
        connections.forEach((key, value) -> {
            if(value.getSourceRoomId().equals(roomId)) {
                newList.add(value);
            }
        });
        return newList;
    }

    public ConnectionRepo() {
        this.connections = new HashMap<>();
    }
}
