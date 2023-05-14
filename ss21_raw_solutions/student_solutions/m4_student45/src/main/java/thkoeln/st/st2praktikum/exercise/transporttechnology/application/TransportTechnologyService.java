package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.repository.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.repository.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.repository.TransportTechnologyRepository;


import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    private final RoomRepository roomRepository;
    private final TransportTechnologyRepository transportTechnologyRepository;
    private final ConnectionRepository connectionRepository;

    @Autowired
    public TransportTechnologyService(TransportTechnologyRepository transportTechnologyRepository, RoomRepository roomRepository, ConnectionRepository connectionRepository) {
        this.roomRepository = roomRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.connectionRepository = connectionRepository;
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     *
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);

        transportTechnologyRepository.save(transportTechnology);

        return transportTechnology.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     *
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId          the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate      the coordinate of the entry point
     * @param destinationRoomId     the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     *
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        TransportTechnology transportTechnology = transportTechnologyRepository.findById(transportTechnologyId).orElseThrow(EntityNotFoundException::new);
        Room sourceRoom = roomRepository.findById(sourceRoomId).orElseThrow(EntityNotFoundException::new);
        Room destinationRoom = roomRepository.findById(destinationRoomId).orElseThrow(EntityNotFoundException::new);

        Connection connection = transportTechnology.addConnection(sourceRoom, sourceCoordinate, destinationRoom, destinationCoordinate);
        connectionRepository.save(connection);
        roomRepository.save(sourceRoom);
        roomRepository.save(destinationRoom);

        return connection.getId();
    }
}
