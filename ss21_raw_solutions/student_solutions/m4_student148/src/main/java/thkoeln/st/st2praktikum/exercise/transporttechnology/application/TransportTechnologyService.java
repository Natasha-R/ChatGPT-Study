package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import thkoeln.st.st2praktikum.exercise.field.domain.*;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
public class TransportTechnologyService {

    private final HashMap<UUID, TransportTechnology> transportTechnologies = new HashMap<>();
    private static final HashMap<UUID, Connection> connections = new HashMap<>();

    private final TransportTechnologyRepository transportTechnologyRepository;
    private final FieldRepository fieldRepository;

    @Autowired
    private TransportTechnologyService(TransportTechnologyRepository transportTechnologyRepository,
                                       FieldRepository fieldRepository) {
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologies.put(transportTechnology.getId(), transportTechnology);
        return transportTechnologyRepository.save(transportTechnology).getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology.
     * Connections only work in one direction.
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
        Optional<Field> sf = fieldRepository.findById(sourceFieldId);
        Optional<Field> df = fieldRepository.findById(destinationFieldId);
        if (sf.isEmpty() || df.isEmpty())
            throw new IllegalArgumentException("One or more of the specified fields fo not exist!");
        else {
            Field sourceField = sf.get();
            Field destinationField = df.get();
            TransportTechnology transportTechnology = transportTechnologies.get(transportTechnologyId);
            if (sourceCoordinate.getX() < sourceField.getWidth()
                    && sourceCoordinate.getY() < sourceField.getHeight()
                    && destinationCoordinate.getX() < destinationField.getWidth()
                    && destinationCoordinate.getY() < destinationField.getHeight()) {
                Connection connection = new Connection(transportTechnology, sourceField, sourceCoordinate,
                        destinationField, destinationCoordinate);
                connections.put(connection.getId(), connection);
                transportTechnology.setConnections(connection);
                transportTechnologyRepository.save(transportTechnology);
                return connection.getId();
            } else throw new IllegalArgumentException("A connection can't exist beyond the fields boundries!");
        }
    }

    public static HashMap<UUID, Connection> getConnections() {
        return connections;
    }
}
