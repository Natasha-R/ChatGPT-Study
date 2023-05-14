package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.SpaceConnection;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.ISpaceService;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.SpaceService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import java.util.List;

@Service
public class TransportService implements ITransportService {

    private final SpaceRepository spaceRepository;
    private final CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    public TransportService(SpaceRepository spaceRepository, CleaningDeviceRepository cleaningDeviceRepository) {
        this.spaceRepository = spaceRepository;
        this.cleaningDeviceRepository = cleaningDeviceRepository;
    }

    @Override
    public boolean transport(IWalkable walkable) {
        final SpaceConnection connection = getConnection(walkable);
        if (connection == null)
            return false;

        final ISpaceService space = walkable.getSpace();
        if (space != null)
            space.removeDevice(walkable);

        walkable.setSpace(connection.getDestination().getId());
        final ISpaceService destinationSpace = new SpaceService(connection.getDestination(), spaceRepository, cleaningDeviceRepository);
        destinationSpace.addDevice(walkable.getUuid());
        walkable.jump(new Point(connection.getDestinationPoint().getX(), connection.getDestinationPoint().getY()));

        return true;
    }

    // returns a SpaceConnection if the device coordinate equal connection coordinate
    private SpaceConnection getConnection(IWalkable walkable) {
        final ISpaceService space = walkable.getSpace();
        final List<SpaceConnection> connections = space.getSourceConnections();
        final Point devicePosition = walkable.leftBottom();
        return connections.stream().filter(spaceConnection -> spaceConnection.getSourcePoint().equals(devicePosition)).findFirst().orElse(null);
    }
}

interface ITransportService {
    boolean transport(IWalkable walkable);
}

