package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Repositories.FieldRepository;
import thkoeln.st.st2praktikum.exercise.Repositories.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;


import java.util.Optional;
import java.util.UUID;


@Service
public class TransportSystemService {
    private final TransportSystemRepository transportSystemRepository;
    private final FieldRepository fieldRepository;

    public TransportSystemService(TransportSystemRepository transportSystemRepository,FieldRepository fieldRepository){
      this.transportSystemRepository = transportSystemRepository;
      this.fieldRepository = fieldRepository;
    }
    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getTsID();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceFieldId,
                              Point sourceCoordinate,
                              UUID destinationFieldId,
                              Point destinationCoordinate) {
        Field source = searchfieldbyID(sourceFieldId);
        Field destination = searchfieldbyID(destinationFieldId);
        Connection connection = new Connection(source,sourceCoordinate,destination,destinationCoordinate);
        isConnectionInBounds(sourceFieldId,sourceCoordinate,destinationFieldId, destinationCoordinate);
        for (TransportSystem t : transportSystemRepository.findAll()){
            if(transportSystemId == t.getTsID()) t.getConnections().add(connection);
        }

        return connection.getSourceField().getFieldID();
    }

    void isConnectionInBounds(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate){
        for (Field value : fieldRepository.findAll()) {
            if(value.getFieldID()==destinationFieldId){
                if(value.getWidth() < destinationCoordinate.getX() || value.getHeight() < destinationCoordinate.getY()) throw new RuntimeException("destination Out of Bounds");
            }
            if (value.getFieldID() == sourceFieldId) {
                if(sourceCoordinate.getX() > value.getWidth() || sourceCoordinate.getY() > value.getHeight())throw new RuntimeException("source out of bounds");
            }
        }
    }

    public Field searchfieldbyID(UUID id){
        Optional<Field> field = fieldRepository.findById(id);
        if (field.isPresent()){
            return field.get();
        }
        else throw new RuntimeException("field not found");
    }
}
