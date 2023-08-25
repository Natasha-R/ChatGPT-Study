package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransportSystemManager {
    private final ArrayList<TransportSystem> transportSystems = new ArrayList<>();

    public UUID addTransportSystem(String name) {
        TransportSystem transportSystem = new TransportSystem(name);
        this.transportSystems.add(transportSystem);
        return transportSystem.getUuid();
    }

    public UUID addConnectionToTransportSystemByUUID(UUID transportSystemId, UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        TransportSystem transportSystem = this.getTransportSystemByUUID(transportSystemId);
        Connection newConnection = new Connection(sourceSpaceShipDeckId, sourcePoint, destinationSpaceShipDeckId, destinationPoint);

        return transportSystem.addConnection(newConnection);
    }

    public ArrayList<Connection> getConnectionsBySourceDeckID(UUID sourceSpaceShipId) {
        ArrayList<Connection> allConnections = this.getAllConnections();

        return allConnections.stream()
                .filter(c -> c.getSourceSpaceShipDeckId().equals(sourceSpaceShipId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Connection> getAllConnections() {
        ArrayList<Connection> allConnections = new ArrayList<>();
        for (TransportSystem transportSystem : this.transportSystems) {
            allConnections.addAll(transportSystem.getConnections());
        }
        return allConnections;
    }

    public ArrayList<Connection> getConnectionsFromTransportSystemById(UUID transportSystemUUID) {
        return this.getTransportSystemByUUID(transportSystemUUID).getConnections();
    }

    public TransportSystem getTransportSystemByUUID(UUID transportSystemUUID) {
        Optional<TransportSystem> transportSystem = this.transportSystems.stream().filter(b -> b.getUuid().equals(transportSystemUUID)).findFirst();
        if (transportSystem.isEmpty())
            throw new InvalidParameterException("No Transport System matches the given UUID");
        return transportSystem.get();
    }

    public ArrayList<TransportSystem> getTransportSystems() {
        return transportSystems;
    }
}
