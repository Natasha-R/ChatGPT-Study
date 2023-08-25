package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TechnologyManager {
    @Id
    private UUID id = UUID.randomUUID();

    @ElementCollection( targetClass = TransportTechnology.class )
    private List<TransportTechnology> transportTechnologies = new ArrayList<>();

    public TransportTechnology createTransporterTechnology(String description, TransportTechnologyRepository transportTechnologyRepository) {
        TransportTechnology technology = new TransportTechnology(description);
        transportTechnologies.add(technology);
        transportTechnologyRepository.save(technology);
        return technology;
    }

    public boolean useConnection(UUID usedConnectionUUID) {
        Connection connection = (Connection) getConnectionFromUUID(usedConnectionUUID);
        if (isConnectionUseable(connection) == false) { return false; }
        MiningMachine machine = connection.getMachineOnSourceField();
        if (machine == null) { return false; }
        return moveMachine(machine, connection);
    }

    private IConnection getConnectionFromUUID(UUID searchedConnectionUUID) {
        for (int index=0; index<transportTechnologies.size(); index++) {
            IConnection connection = transportTechnologies.get(index).getConnectionFromUUID(searchedConnectionUUID);
            if (connection != null) return connection;
        }
        return null;
    }

    private boolean isConnectionUseable(Connection connection) {
        if (connection == null) { return false; }
        if (connection.getMachineOnDestinationField() != null) { return false; }
        return true;
    }

    private boolean moveMachine(MiningMachine machine, Connection connection) {
        machine.setField((Field) connection.getDestinationField());
        Point newPosition = new Point(connection.getDestinationPosition().getX(), connection.getDestinationPosition().getY());
        machine.setPosition(newPosition);
        connection.getSourceField().removeMachine(machine);
        connection.getDestinationField().addMachine(machine);
        return true;
    }

    public IConnection getConnection(UUID sourceFieldUUID, UUID destinationFieldUUID, Point searchedSourceFieldPosition) {
        for (int index=0; index<transportTechnologies.size(); index++) {
            IConnection connection = transportTechnologies.get(index).verifyConnection(sourceFieldUUID, destinationFieldUUID, searchedSourceFieldPosition);
            if (connection != null) return connection;
        }
        return null;
    }
}
