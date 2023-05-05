package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;

import java.util.UUID;


@Service
public class TransportTechnologyService {

    private final TransportTechnologyRepository transportTechnologyRepository;
    private final SpaceRepository spaceRepository;

    public TransportTechnologyService(TransportTechnologyRepository transportTechnologyRepository, SpaceRepository spaceRepository) {
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.spaceRepository = spaceRepository;
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        final thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology transportTechnology = new thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology(technology);
        this.transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getTechnologyId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the point of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceSpaceId,
                              Coordinate sourceCoordinate,
                              UUID destinationSpaceId,
                              Coordinate destinationCoordinate) {
        final Connection connection = new Connection(transportTechnologyId, sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);
        this.spaceRepository.findById(sourceSpaceId).get().addConnection(connection);
        return connection.getConnectionId();
    }
}
