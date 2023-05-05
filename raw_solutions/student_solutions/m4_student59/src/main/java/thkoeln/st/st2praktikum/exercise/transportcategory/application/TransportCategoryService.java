package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoriesRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;


import java.util.UUID;


@Service
public class TransportCategoryService {

    private final TransportCategoriesRepository transportCategoriesRepository;
    private final FieldRepository fieldRepository;

    public TransportCategoryService(TransportCategoriesRepository transportCategoriesRepository, FieldRepository fieldRepository){
        this.transportCategoriesRepository = transportCategoriesRepository;
        this.fieldRepository = fieldRepository;
    }
    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        final TransportCategory transportCategory = new TransportCategory(category);
        this.transportCategoriesRepository.save(transportCategory);
        return transportCategory.getCategoryId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceFieldId,
                              Point sourcePoint,
                              UUID destinationFieldId,
                              Point destinationPoint) {
        final Connection connection = new Connection(transportCategoryId, sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);
        this.fieldRepository.findById(sourceFieldId).get().addConnection(connection);
        return connection.getConnectionId();
    }
}
