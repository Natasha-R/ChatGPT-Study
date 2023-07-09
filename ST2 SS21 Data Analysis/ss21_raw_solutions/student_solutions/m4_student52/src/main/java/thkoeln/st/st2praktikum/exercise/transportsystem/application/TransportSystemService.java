package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepo;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;


import java.util.UUID;


@Service
public class TransportSystemService {
    private ConnectionRepo connectionRepo;
    private TransportSystemRepo transportSystemRepo;
    private FieldRepo fieldRepo;

    @Autowired
    public TransportSystemService(ConnectionRepo connectionRepo, TransportSystemRepo transportSystemRepo, FieldRepo fieldRepo){
        this.connectionRepo = connectionRepo;
        this.transportSystemRepo = transportSystemRepo;
        this.fieldRepo = fieldRepo;
    }
    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        var ts = new TransportSystem(system);
        transportSystemRepo.save(ts);
        return ts.getId();
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
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceFieldId,
                              Point sourcePoint,
                              UUID destinationFieldId,
                              Point destinationPoint) {
        var transportSystem = transportSystemRepo.get(transportSystemId);
        var srcField = fieldRepo.get(sourceFieldId);

        var c = new Connection(sourcePoint, destinationPoint);
        connectionRepo.save(c);

        transportSystem.getConnections().add(c);
        transportSystemRepo.save(transportSystem);


        srcField.getConnections().put(destinationFieldId, c);
        fieldRepo.save(srcField);
        return c.getId();

    }
}
