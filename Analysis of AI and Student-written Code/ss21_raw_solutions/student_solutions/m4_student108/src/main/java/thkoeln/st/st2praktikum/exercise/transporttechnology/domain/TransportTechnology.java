package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.exercise.domainprimitives.IComponent;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.ConnectionRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
public class TransportTechnology implements IComponent {
    @Id
    private final UUID uuid = UUID.randomUUID();
    private final String description;

    @OneToMany
    private List<Connection> connections;

    public TransportTechnology() { this.description = ""; }
    public TransportTechnology(String description) {
        this.description = description;
        this.connections = new ArrayList<>();
    }

    public UUID createConnection(Field sourceFieldId, Point sourceCoordinate, Field destinationFieldId, Point destinationCoordinate, ConnectionRepository connectionRepository) {
        Connection connection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        connections.add(connection);
        connectionRepository.save(connection);
        return connection.getUUID();
    }

    public Connection verifyConnection(UUID searchedSourceFieldUUID, UUID searchedDestinationFieldUUID, Point searchedSourceFieldPosition) {
        for (int index=0; index<connections.size(); index++) {
            if (connections.get(index).verifyConnection(searchedSourceFieldUUID, searchedDestinationFieldUUID, searchedSourceFieldPosition)) {
                return connections.get(index);
            }
        }
        return null;
    }

    /*public Connection getConnectionFromUUID(UUID searchedConnectionUUID) {
        return connectionRepository.findById(searchedConnectionUUID).get();
    }*/

    public String getDescription() {
        return description;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
