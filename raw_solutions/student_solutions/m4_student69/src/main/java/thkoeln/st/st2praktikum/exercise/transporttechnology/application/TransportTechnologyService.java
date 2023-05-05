package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.UUID;


@Service
public class TransportTechnologyService {
    public TransportTechnologyRepo transportTechnologyRepo;

    @Autowired
    public TransportTechnologyService(TransportTechnologyRepo transportTechnologyRepo) {
        this.transportTechnologyRepo = transportTechnologyRepo;
    }
    
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(UUID.randomUUID(), technology);
        transportTechnologyRepo.save(transportTechnology);
        return transportTechnology.getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceFieldId,
                              Coordinate sourceCoordinate,
                              UUID destinationFieldId,
                              Coordinate destinationCoordinate) {
        TransportTechnology transportTechnology = transportTechnologyRepo.findById(transportTechnologyId).get();
        Connection fieldConnection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        transportTechnology.getConnection().add(fieldConnection);
        transportTechnologyRepo.save(transportTechnology);
        return UUID.randomUUID();
    }
}
