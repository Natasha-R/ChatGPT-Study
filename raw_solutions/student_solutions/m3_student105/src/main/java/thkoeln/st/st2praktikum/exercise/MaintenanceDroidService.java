package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.DeckRepository;
import thkoeln.st.st2praktikum.exercise.repositories.MaintenanceDroidRepository;

import java.util.UUID;

@Service
public class MaintenanceDroidService {

    @Autowired
    Spaceship ship;

    @Autowired
    DeckRepository deckRepo;

    @Autowired
    MaintenanceDroidRepository droidRepo;

    @Autowired
    ConnectionRepository connectionRepo;


    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        Deck d = new Deck(height, width);
        ship.addDeck(d);
        deckRepo.save(d);
        return d.getId();
    }

    /**
     * This method adds a barrier to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrier the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        Deck d = ship.findDeckById(spaceShipDeckId);
        d.addBarrier(barrier);
        deckRepo.save(d);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology t = ship.addTechnology(technology);
        return t.getId();
    }


    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        Deck src = ship.findDeckById(sourceSpaceShipDeckId);
        Deck dest = ship.findDeckById(destinationSpaceShipDeckId);
        TransportTechnology technology = ship.findTechnologyById(transportTechnologyId);
        Connection connection = new Connection(src, dest, sourceCoordinate, destinationCoordinate);
        ship.addConnection(connection, technology);
        connectionRepo.save(connection);
        return connection.getId();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid droid = new MaintenanceDroid(name);
        ship.addDroid(droid);
        return droid.getId();
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        MaintenanceDroid affected = droidRepo.findById(maintenanceDroidId).orElseThrow();
        boolean success = ship.execute(order, affected);
        droidRepo.save(affected);
        return success;
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return droidRepo.findById(maintenanceDroidId).orElseThrow(RuntimeException::new).getGrid().getId();
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public Coordinate getCoordinate(UUID maintenanceDroidId){
        return droidRepo.findById(maintenanceDroidId).orElseThrow(RuntimeException::new).getCurrentPosition();
    }
}
