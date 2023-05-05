package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.IConnection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    @Getter
    @Autowired
    TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    private FieldService fieldService;

    @Autowired
    public TransportTechnologyService(FieldService fieldService1) {
        this.fieldService = fieldService1;
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getUUID();
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
        Optional<TransportTechnology> technologyOptional = transportTechnologyRepository.findById(transportTechnologyId);

        UUID connectionUUID = technologyOptional.get().createConnection(
                fieldService.getFieldRepository().findById(sourceFieldId).get(),
                sourcePoint,
                fieldService.getFieldRepository().findById(destinationFieldId).get(),
                destinationPoint,
                connectionRepository);
        return connectionUUID;
    }

    public Connection getConnection(UUID sourceFieldUUID, UUID destinationFieldUUID, Point searchedSourceFieldPosition) {
        List<TransportTechnology> technologies = new ArrayList<>();
        transportTechnologyRepository
                .findAll()
                .forEach(technologies::add);
        for (int index=0; index<technologies.size(); index++) {
            Connection connection = technologies.get(index).verifyConnection(sourceFieldUUID, destinationFieldUUID, searchedSourceFieldPosition);
            if (connection != null) return connection;
        }
        return null;
    }
}
