package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;


import java.util.UUID;


@Service
public class TransportTechnologyService {

    TransportTechnologyRepository transportTechnologyRepository;
    FieldRepository fieldRepository;

    @Autowired
    public TransportTechnologyService(TransportTechnologyRepository transportTechnologyRepository,
                                      FieldRepository fieldRepository){
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        this.transportTechnologyRepository.save(transportTechnology);
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
    public UUID addConnection(
            UUID transportTechnologyId,
            UUID sourceFieldId,
            Coordinate sourceCoordinate,
            UUID destinationFieldId,
            Coordinate destinationCoordinate
    ) {

        Connection connection = new Connection(
                fieldRepository.findById(sourceFieldId).orElseThrow(),
                fieldRepository.findById(destinationFieldId).orElseThrow(),
                sourceCoordinate,
                destinationCoordinate
        );

        TransportTechnology technology =
                this.transportTechnologyRepository
                        .findById(transportTechnologyId)
                        .orElseThrow();

        technology.addConnection(connection);

        return connection.getId();
    }
}
