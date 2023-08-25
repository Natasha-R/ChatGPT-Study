package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.control.Controller;
import thkoeln.st.st2praktikum.exercise.control.Environment;
import thkoeln.st.st2praktikum.exercise.droids.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.map.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.map.Locatable;
import thkoeln.st.st2praktikum.exercise.map.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.map.connection.Connection;
import thkoeln.st.st2praktikum.exercise.map.connection.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.map.connection.TransportCategory;
import thkoeln.st.st2praktikum.exercise.map.connection.TransportCategoryRepository;

import java.util.UUID;


@Service
@Getter
public class MaintenanceDroidService {

    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;

    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck( Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck( height, width );
        spaceShipDeckRepository.save( spaceShipDeck );
        return spaceShipDeck.getGridID();
    }

    /**
     * This method adds a barrier to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier( UUID spaceShipDeckId, Barrier barrier ) {

        spaceShipDeckRepository.findById( spaceShipDeckId ).get().addBarriers( barrier );

    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory( String category ) {
        TransportCategory transportCategory = new TransportCategory( category );
        transportCategoryRepository.save( transportCategory );
        return transportCategory.getTransportCategoryId();
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
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        Locatable sourcePointOnDeck = getPointFromGrid( sourceSpaceShipDeckId , sourcePoint.toString() );
        Locatable destinationPointOnDeck = getPointFromGrid( destinationSpaceShipDeckId , destinationPoint.toString() );
        TransportCategory transportCategory = transportCategoryRepository.findById(transportCategoryId).get();
        Connection newConnection = new Connection(transportCategory , sourcePointOnDeck , destinationPointOnDeck );
        connectionRepository.save( newConnection );
        return newConnection.getConnectionID();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        maintenanceDroidRepository.save(maintenanceDroid);
        return maintenanceDroidRepository.findById(maintenanceDroid.getDroidID()).get().getDroidID();
    }

    /**
     * This method lets the maintenance droid execute a order.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        return maintenanceDroidRepository.findById(maintenanceDroidId).get().executeOrder( order , this);
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return getMaintenanceDroidPoint( maintenanceDroidId ).getSpaceShipDeckID();
    }

    /**
     * This method returns the point a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the point of the maintenance droid
     */
    public Locatable getMaintenanceDroidPoint(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findById( maintenanceDroidId ).get().getPosition();
    }

    public SpaceShipDeck getSpaceShipDeckByID(UUID spaceShipDeckID){
        return spaceShipDeckRepository.findById( spaceShipDeckID ).get();
    }

    public Locatable getPointFromGrid(UUID gridID, String coordinateString){
        return spaceShipDeckRepository.findById(gridID).get().getPointFromSpaceShipDeck(coordinateString);
    }


}
