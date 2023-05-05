package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TranportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.ArrayList;
import java.util.UUID;


@Service
public class TransportCategoryService {
    @Autowired
    private TransportCategoryRepository transportCategoryRepository;
    private ArrayList<Connection> allConnectionPoint = new ArrayList<>();
    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TranportCategory tranportCategory=new TranportCategory(category);
        transportCategoryRepository.save( tranportCategory );
        return tranportCategory.getId();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceSpaceShipDeckId,
                              Point sourcePoint,
                              UUID destinationSpaceShipDeckId,
                              Point destinationPoint) {
        Connection connection=new Connection();
        connection.setTransportCategoryId( transportCategoryId );
        connection.setIdSource( sourceSpaceShipDeckId );
        connection.setIdDestination( destinationSpaceShipDeckId );
        connection.setPointSource( sourcePoint );
        connection.setPointDestination( destinationPoint );
        allConnectionPoint.add(connection);
        return connection.getConnectionID();

    }
    public ArrayList<Connection> getAllConnectionPoint(){
        return this.allConnectionPoint;
    }

}
