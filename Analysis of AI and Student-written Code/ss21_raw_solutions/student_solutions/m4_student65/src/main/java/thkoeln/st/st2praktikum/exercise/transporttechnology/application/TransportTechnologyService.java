package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    private HashMap<UUID, TransportTechnology> transportTechnology = new HashMap<>();


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
        UUID uuid = UUID.randomUUID();
        transportTechnology.put(uuid,new TransportTechnology(technology,uuid));
        transportTechnologyRepository.save(transportTechnology.get(uuid));
        return uuid;
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
        //UUID uuid = UUID.randomUUID();
        //connections.put(uuid,new Connection(sourceFieldId, sourcePoint, destinationFieldId, destinationPoint, uuid));
        //connectionRepository.save(connections.get(i));

        List<Field> fields = Streamable.of(fieldRepository.findAll()).toList();
        UUID uuid = transportTechnology.get(transportTechnologyId).addConnection(fields, sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);
        transportTechnologyRepository.save(transportTechnology.get(transportTechnologyId));
        //UUID uuid = UUID.randomUUID();
        //CheckIfConnectionOutOfBounds(sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);
        //connections.put(uuid,new Connection(sourceFieldId, sourcePoint, destinationFieldId, destinationPoint, uuid));
        // connectionRepository.save(connections.get(uuid));
        return uuid;
    }
}
