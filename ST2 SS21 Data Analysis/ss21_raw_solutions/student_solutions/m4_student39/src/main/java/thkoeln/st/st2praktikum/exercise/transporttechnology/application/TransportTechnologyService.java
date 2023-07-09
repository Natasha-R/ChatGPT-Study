package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;


import java.util.UUID;


@Service
public class TransportTechnologyService {
    protected MiningMachineRepository miningMachineRepository;
    protected TransportTechnologyRepository transportTechnologyRepository;
    protected FieldRepository fieldRepository;

    @Autowired
    public TransportTechnologyService(TransportTechnologyRepository transportTechnologyRepository, FieldRepository fieldRepository, MiningMachineRepository miningMachineRepository){
        this.miningMachineRepository = miningMachineRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.fieldRepository = fieldRepository;
    }
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return transportTechnologyRepository.save(new TransportTechnology(technology)).getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceFieldId,
                              Vector2D sourceVector2D,
                              UUID destinationFieldId,
                              Vector2D destinationVector2D) {
        UUID newConnectionUUID = UUID.randomUUID();
        if(fieldRepository.findById(sourceFieldId).get().checkPositionOnMap(sourceVector2D) && fieldRepository.findById(destinationFieldId).get().checkPositionOnMap(destinationVector2D)){
            transportTechnologyRepository.findById(transportTechnologyId).get().getConnections().add(new Connection(newConnectionUUID, sourceFieldId, destinationFieldId, sourceVector2D, destinationVector2D));
            fieldRepository.findById(sourceFieldId).get().addTransportTechnology(transportTechnologyRepository.findById(transportTechnologyId).get());
            return newConnectionUUID;
        }else{
            throw new RuntimeException("source or destination Position are out of bounds");
        }
    }
}
