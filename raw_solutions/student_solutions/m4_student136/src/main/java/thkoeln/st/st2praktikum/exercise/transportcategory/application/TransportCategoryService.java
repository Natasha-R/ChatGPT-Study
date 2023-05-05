package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.ArrayList;
import java.util.UUID;


@Service
public class TransportCategoryService
{
    private ArrayList<TransportCategory> transportCategories;
    private ArrayList<Connection> connections;

    private FieldRepository fieldRepository;
    private ConnectionRepository connectionRepository;
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    public TransportCategoryService(TransportCategoryRepository transportCategoryRepository, ConnectionRepository connectionRepository, FieldRepository fieldRepository)
    {
        transportCategories = new ArrayList<TransportCategory>();
        connections = new ArrayList<Connection>();

        this.transportCategoryRepository = transportCategoryRepository;
        this.fieldRepository = fieldRepository;
        this.connectionRepository = connectionRepository;
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category)
    {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategories.add(transportCategory);
        transportCategoryRepository.save(transportCategory);
        return transportCategory.getUuid();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceFieldId,
                              Vector2D sourceVector2D,
                              UUID destinationFieldId,
                              Vector2D destinationVector2D)
    {
        Connection connection = new Connection(transportCategoryRepository.findById(transportCategoryId).get(), fieldRepository.findById(sourceFieldId).get(), sourceVector2D, fieldRepository.findById(destinationFieldId).get(), destinationVector2D);

        connections.add(connection);
        updateDatabase();

        return connection.getUuid();
    }

    public TransportCategory getTransportCategoryByUUID(UUID id)
    {
        for(TransportCategory transportCategory : transportCategories)
        {
            if(transportCategory.getUuid().compareTo(id) == 0)
                return transportCategory;
        }
        return null;
    }

    public void updateDatabase()
    {
        // Clear repositories
        connectionRepository.deleteAll();
        transportCategoryRepository.deleteAll();

        // Fill repositories
        for(TransportCategory transportCategory : transportCategories)
        {
            transportCategoryRepository.save(transportCategory);
        }

        for(Connection connection : connections)
        {
            connectionRepository.save(connection);
        }
    }
}
