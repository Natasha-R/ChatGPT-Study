package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Component.Connection;
import thkoeln.st.st2praktikum.exercise.Component.Field;
import thkoeln.st.st2praktikum.exercise.Component.MiningMachine;

import java.util.ArrayList;
import java.util.UUID;

public class ConnectionManager {
    ArrayList<Connection> connections;

    public ConnectionManager() {
        connections = new ArrayList();
    }

    public UUID createConnection(Field sourceFieldId, Point sourceCoordinate, Field destinationFieldId, Point destinationCoordinate, UUIDManager uuidManager) {
        Connection connection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        connections.add(connection);
        uuidManager.addGameComponent(connection);
        return connection.getUUID();
    }

    public boolean useConnection(UUID usedConnectionUUID) {
        Connection connection = getConnectionFromUUID(usedConnectionUUID);
        if (isConnectionUseable(connection) == false) { return false; }
        MiningMachine machine = connection.getMachineOnSourceField();
        if (machine == null) { return false; }
        return moveMachine(machine, connection);
    }

    private Connection getConnectionFromUUID(UUID searchedConnectionUUID) {
        for (int index=0; index<connections.size(); index++) {
            if (connections.get(index).getUUID().toString().equals(searchedConnectionUUID.toString())) {
                return connections.get(index);
            }
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

    public Connection getConnection(UUID sourceFieldUUID, UUID destinationFieldUUID, Point searchedSourceFieldPosition) {
        for (int index=0; index<connections.size(); index++) {
            if (connections.get(index).verifyConnection(sourceFieldUUID, destinationFieldUUID, searchedSourceFieldPosition)) {
                return connections.get(index);
            }
        }
        return null;
    }
}
