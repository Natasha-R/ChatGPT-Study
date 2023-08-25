package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceService;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.ConnectedTransition;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.ConnectedTransitionRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;

import java.util.UUID;


@Service
public class TransportTechnologyService {

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    private ConnectedTransitionRepository connectedTransitionRepository;

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        return transportTechnologyRepository.save(transportTechnology).getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceSpaceId,
                              Vector2D sourceVector2D,
                              UUID destinationSpaceId,
                              Vector2D destinationVector2D) {

        ConnectedTransition connectedTransition = new ConnectedTransition(SpaceService.getServiceSupplier().get().getSpace(sourceSpaceId), sourceVector2D,
                SpaceService.getServiceSupplier().get().getSpace(destinationSpaceId), destinationVector2D, getTransportTechnology(transportTechnologyId));

        return connectedTransitionRepository.save(connectedTransition).getId();
    }

    public TransportTechnology getTransportTechnology(UUID transportTechnologyId) {
        return transportTechnologyRepository.findById(transportTechnologyId).get();
    }
}
