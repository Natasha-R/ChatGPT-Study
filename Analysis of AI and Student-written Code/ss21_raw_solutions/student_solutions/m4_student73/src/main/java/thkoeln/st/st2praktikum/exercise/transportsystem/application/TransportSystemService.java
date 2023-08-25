package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.TransportSystem;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystemRepository;


import java.util.UUID;


@Service
public class TransportSystemService {
    private TransportSystemRepository transportSystemRepository;
    private RoomRepository roomRepository;

    @Autowired
    public TransportSystemService (TransportSystemRepository transportSystemRepository, RoomRepository roomRepository) {
        this.transportSystemRepository = transportSystemRepository;
        this.roomRepository = roomRepository;
    }
    
    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceRoomId,
                              Vector2D sourceVector2D,
                              UUID destinationRoomId,
                              Vector2D destinationVector2D) {
        return roomRepository.findById(sourceRoomId).get().addConnection(transportSystemRepository.findById(transportSystemId).get(), sourceVector2D, destinationRoomId, destinationVector2D);
    }
}
