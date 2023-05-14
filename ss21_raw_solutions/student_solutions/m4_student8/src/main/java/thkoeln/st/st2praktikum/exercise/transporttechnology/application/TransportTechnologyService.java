package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.application.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.UUID;


@Service
public class TransportTechnologyService
{

    private TransportTechnologyRepository transportTechnologyRepository;

    private ConnectionRepository connectionRepository;

    private RoomRepository roomRepository;

    @Autowired
    public TransportTechnologyService(TransportTechnologyRepository transportTechnologyRepository, ConnectionRepository connectionRepository,RoomRepository roomRepository)
    {
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.connectionRepository = connectionRepository;
        this.roomRepository = roomRepository;
    }

    
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology)
    {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        this.transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getTechnologyID();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceRoomId,
                              Coordinate sourceCoordinate,
                              UUID destinationRoomId,
                              Coordinate destinationCoordinate)
    {
        Connection connection = new Connection(sourceRoomId,sourceCoordinate,destinationRoomId,destinationCoordinate,transportTechnologyId);
        this.connectionRepository.save(connection);
        this.roomRepository.findById(sourceRoomId).get().addRoomConnection(connection);
        return connection.identify();
    }
}
