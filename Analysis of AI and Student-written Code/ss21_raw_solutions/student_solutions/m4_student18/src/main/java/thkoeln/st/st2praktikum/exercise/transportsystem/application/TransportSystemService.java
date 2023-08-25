package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.ApplicationController;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;


import java.util.UUID;


@Service
public class TransportSystemService {

    ApplicationController applicationController;

    @Autowired
    public TransportSystemService(ApplicationController applicationController){
        this.applicationController = applicationController;
    }
    
    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        applicationController.getTransportSystemRepository().save(transportSystem);
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

        TransportSystem transportSystem = applicationController.getTransportSystemRepository().findById(transportSystemId).get();
        Room sourceRoom = applicationController.getRoomRepository().findById(sourceRoomId).get();

        if (sourceRoom.getHeight() <= sourceVector2D.getY() || sourceRoom.getWidth() <= sourceVector2D.getX() || applicationController.getRoomRepository().findById(destinationRoomId).get().getHeight() <= destinationVector2D.getY() || applicationController.getRoomRepository().findById(destinationRoomId).get().getWidth() <= destinationVector2D.getX())
            throw new RuntimeException("Connection Out Of Bounce");

        Connection roomConnection = new Connection(transportSystemId, sourceVector2D, destinationVector2D);
        sourceRoom.getConnections().put(destinationRoomId, roomConnection);
        applicationController.getConnectionRepository().save(roomConnection);

        transportSystem.getConnections().add(roomConnection);
        applicationController.getTransportSystemRepository().save(transportSystem);

        applicationController.getRoomRepository().save(sourceRoom);

        return roomConnection.getId();
    }
}
