package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.space.ISpace;
import thkoeln.st.st2praktikum.exercise.space.SpaceConnection;

import java.util.List;

public class TransportService implements ITransportService {

    @Override
    public boolean transport(IWalkable walkable) {
        final SpaceConnection connection = getConnection(walkable);
        if (connection == null)
            return false;

        final ISpace space = walkable.getSpace();
        if(space != null)
            space.removeDevice(walkable);

        walkable.setSpace(connection.getDestination());
        connection.getDestination().addDevice(walkable);
        walkable.jump(connection.getDestinationPosition());

        return true;
    }


    // returns a SpaceConnection if the device coordinate equal connection coordinate
    private SpaceConnection getConnection(IWalkable walkable) {
        final ISpace space = walkable.getSpace();
        final List<SpaceConnection> connections = space.getSourceConnections();
        final Point devicePosition = walkable.leftBottom();
        return connections.stream().filter(spaceConnection -> spaceConnection.getSourcePosition().equals(devicePosition)).findFirst().orElse(null);
    }
}

interface ITransportService {
    boolean transport(IWalkable walkable);
}

