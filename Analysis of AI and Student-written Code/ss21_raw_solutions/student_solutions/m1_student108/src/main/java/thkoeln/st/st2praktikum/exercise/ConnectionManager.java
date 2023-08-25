package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.FieldComponent.Connection;
import thkoeln.st.st2praktikum.exercise.FieldComponent.IConnection;
import thkoeln.st.st2praktikum.exercise.FieldComponent.IMachine;
import thkoeln.st.st2praktikum.exercise.FieldComponent.MiningMachine;

import java.util.ArrayList;
import java.util.UUID;

public class ConnectionManager implements IConnectionManager {
    private final ArrayList<IConnection> connections;

    public ConnectionManager() {
        connections = new ArrayList<IConnection>();
    }

    @Override
    public Connection createConnection(Field sourceFieldId, int[] sourceCoordinate, Field destinationFieldId, int[] destinationCoordinate, UUIDManager uuidManager) {
        Connection connection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        connections.add(connection);
        uuidManager.rememberObject(connection);
        return connection;
    }

    @Override
    public boolean useConnection(UUID usedConnectionUUID) {
        IConnection connection = getConnectionFromUUID(usedConnectionUUID);
        if (isConnectionUseable(connection) == false) { return false; }
        IMachine machine = connection.getMachineOnSourceField();
        if (machine == null) { return false; }
        return moveMachine(machine, connection);
    }

    private IConnection getConnectionFromUUID(UUID searchedConnectionUUID) {
        for (int index=0; index<connections.size(); index++) {
            if (connections.get(index).getUUID().toString().equals(searchedConnectionUUID.toString())) {
                return connections.get(index);
            }
        }
        return null;
    }

    private boolean isConnectionUseable(IConnection connection) {
        if (connection == null) { return false; }
        if (connection.getMachineOnDestinationField() != null) { return false; }
        return true;
    }

    private boolean moveMachine(IMachine machine, IConnection connection) {
        machine.setField(connection.getDestinationField());
        machine.setPosition(connection.getDestinationFieldPosition()[0], connection.getDestinationFieldPosition()[1]);
        connection.getSourceField().removeMachine(machine);
        connection.getDestinationField().addMachine(machine);
        return true;
    }

    @Override
    public IConnection getConnection(String sourceFieldUUID, String destinationFieldUUID) {
        for (int index=0; index<connections.size(); index++) {
            if (connections.get(index).getSourceField().getUUID().toString().equals(sourceFieldUUID) &&
                    connections.get(index).getDestinationField().getUUID().toString().equals(destinationFieldUUID)) {
                return connections.get(index);
            }
        }
        return null;
    }

}
