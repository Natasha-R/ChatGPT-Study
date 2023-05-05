package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystemRepository;


import java.util.UUID;


@Service
public class TransportSystemService {

    @Autowired
    TransportSystemRepository transportSystemRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    FieldRepository fieldRepository;

    /**
     * This method adds a transport system
     *
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem newTransportSystem = new TransportSystem(system);
        transportSystemRepository.save(newTransportSystem);
        return newTransportSystem.getUuid();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport system. Connections only work in one direction.
     *
     * @param transportSystemId  the transport system which is used by the connection
     * @param sourceFieldId      the ID of the field where the entry point of the connection is located
     * @param sourcePoint        the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint   the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceFieldId,
                              Point sourcePoint,
                              UUID destinationFieldId,
                              Point destinationPoint) {
        TransportSystem temp = transportSystemRepository.findById(transportSystemId).get();
        Connection newConnection = new Connection(temp, sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);
        fieldRepository.findById(sourceFieldId).get().ceckPointToBeWithinBoarders(sourcePoint);
        fieldRepository.findById(sourceFieldId).get().ceckPointToBeWithinBoarders(destinationPoint);
        connectionRepository.save(newConnection);
        return newConnection.getUuid();
    }
}
