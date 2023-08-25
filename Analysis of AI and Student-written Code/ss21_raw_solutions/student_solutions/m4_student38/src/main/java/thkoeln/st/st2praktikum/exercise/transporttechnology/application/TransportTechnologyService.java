package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    private HashMap<UUID, TransportTechnology> transportTechnologies;

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    private FieldRepository fieldRepository;

    
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        if (technology == null)
            throw new IllegalArgumentException();

        if (transportTechnologies == null)
            transportTechnologies = new HashMap<>();

        UUID transportTechnologyID = UUID.randomUUID();
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologies.put(transportTechnologyID,transportTechnology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnologyID;
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceFieldId,
                              Point sourcePoint,
                              UUID destinationFieldId,
                              Point destinationPoint) {
        if (sourceFieldId == null || sourcePoint == null || destinationFieldId == null || destinationPoint == null)
            throw new IllegalArgumentException("Null values are not allowed");

        Iterable<Field> fields_It = fieldRepository.findAll();
        HashMap<UUID,Field> fields = new HashMap<>();
        for (Field field : fields_It)
            fields.put(field.getFieldID(),field);

        if (fields.get(sourceFieldId).getHeight() <= sourcePoint.getY() || fields.get(sourceFieldId).getWidth() <= sourcePoint.getX() ||
                fields.get(destinationFieldId).getHeight() <= destinationPoint.getY() || fields.get(destinationFieldId).getWidth() <= destinationPoint.getX())
            throw new IllegalArgumentException("Connection has to be in bounds!");

        if (transportTechnologies.get(transportTechnologyId).connections == null)
            transportTechnologies.get(transportTechnologyId).connections = new LinkedList<>();

        UUID connectionID = UUID.randomUUID();
        Connection connection = new Connection(transportTechnologyId,sourceFieldId,destinationFieldId,sourcePoint,destinationPoint);
        transportTechnologies.get(transportTechnologyId).connections.add(connection);
        TransportTechnology transportTechnology = transportTechnologies.get(transportTechnologyId);
        transportTechnologyRepository.save(transportTechnology);
        return connectionID;
    }
}
