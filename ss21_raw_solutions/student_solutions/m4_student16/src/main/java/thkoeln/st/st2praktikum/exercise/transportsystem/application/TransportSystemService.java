package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystemRepository;

import java.util.UUID;


@Service
public class TransportSystemService {

    @Autowired
    private TransportSystemRepository transportSystemRepository;
    @Autowired
    private FieldRepository fieldRepository;

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
     * This method adds a traversable connection between two fields based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Connection connection = new Connection(transportSystemId, sourceFieldId,sourceCoordinate,destinationFieldId,destinationCoordinate);
        boolean sourceValid = fieldRepository.getFieldByFieldId(sourceFieldId).coordinateIsNotOutOfBounds(sourceCoordinate);
        boolean destinationValid = fieldRepository.getFieldByFieldId(destinationFieldId).coordinateIsNotOutOfBounds(destinationCoordinate);
        Field sourceField = fieldRepository.getFieldByFieldId(sourceFieldId);
        if(sourceField == null) throw new NullPointerException();
        if(sourceValid && destinationValid) {
            transportSystemRepository.getTransportSystemById(connection.getTransportSystemId()).addConnection(connection);
            return connection.getConnectionID();
        }
        throw new IllegalArgumentException();
    }
}
