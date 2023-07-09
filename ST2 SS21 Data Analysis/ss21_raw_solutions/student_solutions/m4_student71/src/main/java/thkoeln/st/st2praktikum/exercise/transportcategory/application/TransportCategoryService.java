package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.TileRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;


import java.util.HashMap;
import java.util.UUID;


@Service
public class TransportCategoryService {

    HashMap<UUID, TransportCategory> transportCategories = new HashMap<>();

    private final FieldRepository fieldRepository;

    @Autowired
    public TransportCategoryService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        var TransportCategory = new TransportCategory(category);
        transportCategories.put(TransportCategory.getUuid(),TransportCategory);
        return TransportCategory.getUuid();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceFieldId,
                              Coordinate sourceCoordinate,
                              UUID destinationFieldId,
                              Coordinate destinationCoordinate) {

        var sourceField = fieldRepository.findById(sourceFieldId).get();
        var connection = new Connection(sourceField,fieldRepository.findById(destinationFieldId).get(), sourceCoordinate, destinationCoordinate);

        var transportCategory = transportCategories.get(transportCategoryId);
        transportCategory.getConnections().add(connection);

        sourceField.setConnection(connection);

        return connection.getUuid();
    }
}
