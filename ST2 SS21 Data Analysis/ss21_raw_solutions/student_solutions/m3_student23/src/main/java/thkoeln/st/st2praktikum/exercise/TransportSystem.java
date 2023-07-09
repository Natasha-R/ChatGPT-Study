package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.UUID;

@Embeddable
public class TransportSystem {
    private UUID uuid;
    private String name;

    private final ArrayList<Connection> connections = new ArrayList<>();

    protected TransportSystem () {}

    public TransportSystem (String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public UUID addConnection (Connection connection) {
        this.connections.add(connection);
        return connection.getUUID();
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "TransportSystem{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}
