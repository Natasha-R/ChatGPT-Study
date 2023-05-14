package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;


import java.util.UUID;


@Service
public class TransportSystemService {

    private TransportSystemRepository transportSystemRepository;
    private FieldRepository fieldRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public TransportSystemService (TransportSystemRepository transportSystemRepository,
                                   FieldRepository fieldRepository,
                                   ConnectionRepository connectionRepository){
        this.transportSystemRepository = transportSystemRepository;
        this.fieldRepository = fieldRepository;
        this.connectionRepository = connectionRepository;
    }
    
    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        return transportSystemRepository.save(transportSystem).getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        TransportSystem transportSystem = transportSystemRepository.findById(transportSystemId).get();

        Field sourceField = fieldRepository.findById(sourceFieldId).get();
        Field destinationField = fieldRepository.findById(destinationFieldId).get();
        Connection connection = transportSystem.createConnection(sourceField,sourcePoint,destinationField,destinationPoint);

        return connectionRepository.save(connection).getId();
    }
}
