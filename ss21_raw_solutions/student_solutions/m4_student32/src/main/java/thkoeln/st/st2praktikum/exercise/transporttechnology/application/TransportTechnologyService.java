package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.UUID;


@Service
public class TransportTechnologyService {
    private FieldRepository fieldRepository;
    private TransportTechnologyRepository transportTechnologyRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public TransportTechnologyService(FieldRepository fieldRepository, TransportTechnologyRepository transportTechnologyRepository, ConnectionRepository connectionRepository) {
        this.fieldRepository = fieldRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.connectionRepository = connectionRepository;
    }


    public Field findFieldById(UUID id) {
        return fieldRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No dungeon with ID " + id + " can be found."));
    }

    public TransportTechnology findTtById(UUID id) {
        return transportTechnologyRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No dungeon with ID " + id + " can be found."));
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        UUID id = UUID.randomUUID();
        transportTechnologyRepository.save(new TransportTechnology(id, technology));
        return id;
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     *
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinate of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceFieldId,
                              Coordinate sourceCoordinate,
                              UUID destinationFieldId,
                              Coordinate destinationCoordinate) {
        Field sourceField = findFieldById(sourceFieldId);
        Field destinationField = findFieldById(destinationFieldId);

        if (sourceCoordinate.getY() > sourceField.getHeight() || sourceCoordinate.getX() > sourceField.getWidth()) {
            throw new RuntimeException("Connection out of Bounds");
        }

        if (destinationCoordinate.getY() > destinationField.getHeight() || destinationCoordinate.getX() > destinationField.getWidth()) {
            throw new RuntimeException("Connection out of Bounds");
        }

        TransportTechnology transportTechnology = findTtById(transportTechnologyId);

        UUID conId = sourceField.addConnection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate, transportTechnologyId);
        connectionRepository.save(new Connection(conId, sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate, transportTechnologyId));

        return conId;
    }
}
