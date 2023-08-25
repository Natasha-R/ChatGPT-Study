package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TraversableConnection;


import java.util.UUID;


@Service
public class TransportTechnologyService {
    private TraversableConnectionRepository traversableConnectionRepository;

    @Autowired
    public TransportTechnologyService(TraversableConnectionRepository traversableConnectionRepository){

        this.traversableConnectionRepository = traversableConnectionRepository;
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);

        return transportTechnology.getTransportTechnologyId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceRoomId,
                              Point sourcePoint,
                              UUID destinationRoomId,
                              Point destinationPoint) {
        TraversableConnection traversableConnection = new TraversableConnection(transportTechnologyId,sourceRoomId,sourcePoint,destinationRoomId,destinationPoint);
        TraversableConnectionMap.addToMap(traversableConnection, traversableConnection.getSourceRoomId() );
        traversableConnectionRepository.save(traversableConnection);
        return traversableConnection.getConnectionId();
    }
}
