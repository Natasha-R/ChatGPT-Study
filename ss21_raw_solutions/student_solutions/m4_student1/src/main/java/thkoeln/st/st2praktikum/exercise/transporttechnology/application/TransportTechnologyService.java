package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.repository.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    private List<TransportTechnology> transportTechnologies = new ArrayList<>();
    private FieldRepository fieldRepository;

    @Autowired
    public TransportTechnologyService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        this.transportTechnologies.add(transportTechnology);
        return transportTechnology.getUuid();
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
        Connection connection = new Connection(sourceFieldId, sourcePoint, destinationFieldId, destinationPoint, transportTechnologyId);
        Optional<Field> optionalField = this.fieldRepository.findById(sourceFieldId);
        if(optionalField.isEmpty()) throw new RuntimeException();
        Field field = optionalField.get();
        field.addConnection(connection);

        return connection.getId();
    }
}
