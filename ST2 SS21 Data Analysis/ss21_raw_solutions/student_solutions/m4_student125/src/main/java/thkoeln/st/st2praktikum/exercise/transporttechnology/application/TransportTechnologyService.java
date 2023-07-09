package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Connection;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

@Service
public class TransportTechnologyService {

    @Autowired
    public TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    public RoomRepository roomRepository;

    public HashMap<UUID, TransportTechnology> transportTechnologies = new HashMap<>();

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology)
    {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologies.put(transportTechnology.getTechnologyId(), transportTechnology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getTechnologyId();
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
        Iterable<Room> roomIterator = roomRepository.findAll();
        HashMap<UUID, Room> rooms = new HashMap<>();

        for (Room room : roomIterator)
        {
            rooms.put(room.getRoomId(), room);
        }

        if (transportTechnologies == null)
            transportTechnologies = new HashMap<>();

        if (transportTechnologies.get(transportTechnologyId).connections == null)
            transportTechnologies.get(transportTechnologyId).connections = new LinkedList<>();

        Connection connection = new Connection(transportTechnologyId, sourceRoomId, sourceCoordinate, destinationRoomId, destinationCoordinate);
        transportTechnologies.get(transportTechnologyId).connections.add(connection);
        TransportTechnology transportTechnology = transportTechnologies.get(transportTechnologyId);
        transportTechnologyRepository.save(transportTechnology);

        Room room = rooms.get(connection.getSourceId());
        room.getRoomGrid()[connection.getSourceCoordinate().getX()][connection.getSourceCoordinate().getY()].setConnection(connection);
        room.getRoomGrid()[connection.getSourceCoordinate().getX()][connection.getSourceCoordinate().getY()].isConnection = true;

        Room newRoom = new Room(room.getRoomId(), room.getWidth(), room.getHeight(), room.roomGrid);

        roomRepository.deleteById(room.getRoomId());
        roomRepository.save(newRoom);

        return connection.getConnectionId();
    }
}
