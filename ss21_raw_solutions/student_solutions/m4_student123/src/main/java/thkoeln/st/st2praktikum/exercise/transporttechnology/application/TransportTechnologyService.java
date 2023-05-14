package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.Optional;
import java.util.UUID;


@Service
public class TransportTechnologyService {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getTransportTechnologyID();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceRoomId,
                              Vector2D sourceVector2D,
                              UUID destinationRoomId,
                              Vector2D destinationVector2D) {
        Room source = roomService.getRoom(sourceRoomId);
        Room destination =  roomService.getRoom(destinationRoomId);
        if(source.checkIfVectorOutOfBounds(sourceVector2D))
            throw new IllegalArgumentException("Coordinates are Out of Bounds: Source " + sourceVector2D.toString());
        if(destination.checkIfVectorOutOfBounds(destinationVector2D))
            throw new IllegalArgumentException("Coordinates are Out of Bounds: Destination " + destinationVector2D.toString());
        Connection connection = new Connection( sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);
        this.connectionRepository.save(connection);
        transportTechnologyRepository.findById(transportTechnologyId).get().addConnection(connection);
        return connection.getConnectionId();
    }

    public Optional<Connection> findConnection(UUID destinationRoomId, UUID sourceRoomId, Vector2D startCoordinates){
        for (TransportTechnology transportTechnology : transportTechnologyRepository.findAll()) {
            for (Connection connection : transportTechnology.getConnectionList()) {
                if (connection.getSourceRoomId().compareTo(sourceRoomId) == 0 &&
                        connection.getDestinationRoomId().compareTo(destinationRoomId) == 0) {
                    if (startCoordinates.equals(connection.getSourceCoordinate())) {
                        return Optional.of(connection);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
