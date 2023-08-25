package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.ObjectHolder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineCommands;


import java.util.UUID;


@Service
public class TransportCategoryService {
    private MiningMachineCommands commands = ObjectHolder.getMiningMachineCommands();
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private TransportCategoryRepository transportCategoryRepository;



    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory temp = new TransportCategory(category);
        commands.getTransportCategories().add(temp);
        transportCategoryRepository.save(temp);
        return temp.getId();

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
        Connection temp = new Connection();
        temp.setSourceField(commands.findField(sourceFieldId));
        temp.setSourcePoint(sourcePoint);
        temp.setDestinationField(commands.findField(destinationFieldId));
        temp.setDestinationPoint(destinationPoint);
        temp.setTransportCategory(commands.findTransportCategory(transportCategoryId));
        commands.getConnections().add(temp);
        connectionRepository.save(temp);
        return temp.getId();

    }
}
