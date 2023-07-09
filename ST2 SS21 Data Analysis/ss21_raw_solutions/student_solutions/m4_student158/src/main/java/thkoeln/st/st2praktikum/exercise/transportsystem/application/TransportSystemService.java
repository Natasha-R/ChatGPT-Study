package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.repository.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.repository.TransportSystemRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class TransportSystemService {


    @Autowired
    TransportSystemRepository transportSystemRepository;

    @Autowired
    SpaceRepository spaceRepository;
    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = TransportSystem.fromString(system);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceSpaceId,
                              Coordinate sourceCoordinate,
                              UUID destinationSpaceId,
                              Coordinate destinationCoordinate) {
        Optional<Space> sourceSpace = spaceRepository.findById(sourceSpaceId);
        if(sourceSpace.isPresent()){
            Connection connection = Connection.fromShit(transportSystemId, sourceSpaceId, destinationSpaceId, sourceCoordinate, destinationCoordinate, spaceRepository);
            sourceSpace.get().addConnection(connection);
            return connection.getId();
        } else {
            throw new IllegalArgumentException("Source Space does not exist");
        }
    }
}
