package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class TransportTechnology implements IComponent{
    @Id
    private final UUID uuid = UUID.randomUUID();
    private final String description;
    private ArrayList<IConnection> connections;

    public TransportTechnology() { this.description = ""; }
    public TransportTechnology(String description) {
        this.description = description;
        this.connections = new ArrayList<>();
    }

    public UUID createConnection(Field sourceFieldId, Point sourceCoordinate, Field destinationFieldId, Point destinationCoordinate, UUIDManager uuidManager, ConnectionRepository connectionRepository, UUIDManagerRepository uuidManagerRepository) {
        Connection connection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        connections.add(connection);
        uuidManager.addGameComponent(connection);
        uuidManagerRepository.save(uuidManager);
        connectionRepository.save(connection);
        return connection.getUUID();
    }

    public IConnection verifyConnection(UUID searchedSourceFieldUUID, UUID searchedDestinationFieldUUID, Point searchedSourceFieldPosition) {
        for (int index=0; index<connections.size(); index++) {
            if (connections.get(index).verifyConnection(searchedSourceFieldUUID, searchedDestinationFieldUUID, searchedSourceFieldPosition)) {
                return connections.get(index);
            }
        }
        return null;
    }

    public IConnection getConnectionFromUUID(UUID searchedConnectionUUID) {
        for (int index=0; index<connections.size(); index++) {
            if (connections.get(index).getUUID().toString().equals(searchedConnectionUUID.toString())) {
                return connections.get(index);
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
