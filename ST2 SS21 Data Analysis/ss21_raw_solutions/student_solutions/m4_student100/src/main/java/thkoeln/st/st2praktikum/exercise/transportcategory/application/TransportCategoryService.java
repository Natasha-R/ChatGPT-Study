package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryList;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;


@Service
public class TransportCategoryService {
    TransportCategoryList transportCategoryList = new TransportCategoryList();
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    MiningMachineRepository miningMachineRepository;
    @Autowired
    TransportCategoryRepository transportCategoryRepository;




    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        UUID transportcategory = UUID.randomUUID();
        TransportCategory newTransportcategory = new TransportCategory(transportcategory, category);
        transportCategoryRepository.save(newTransportcategory);
        transportCategoryList.add(newTransportcategory);
        return transportcategory;
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

        UUID connectionuuid = UUID.randomUUID();
        Connection newConnection = new Connection(sourceFieldId,sourceCoordinate,destinationFieldId, destinationCoordinate, connectionuuid,  transportCategoryId);
     for(TransportCategory transportCategory : transportCategoryList.getTransportCategoryList()) {
         if(transportCategory.getTransportcategoryuuid() == transportCategoryId ) {
             transportCategoryRepository.deleteById(transportCategoryId);
             transportCategory.getListConnection().add(newConnection);
             transportCategoryRepository.save(transportCategory);
         }
     }
        return connectionuuid;
    }
}
