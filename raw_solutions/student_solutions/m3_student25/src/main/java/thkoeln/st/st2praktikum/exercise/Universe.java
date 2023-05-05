package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class Universe implements UniverseInterface {

    private final SpaceShipDeckRepository spaceShipDecks;
    private final MaintenanceDroidRepository maintenanceDroids;
    private final TransportCategoriesRepository transportCategories;

    @Autowired
    public Universe(SpaceShipDeckRepository spaceShipDecks, MaintenanceDroidRepository maintenanceDroids, TransportCategoriesRepository transportCategories) {
        this.spaceShipDecks = spaceShipDecks;
        this.maintenanceDroids = maintenanceDroids;
        this.transportCategories = transportCategories;
    }


    /**
     * This method creates a new spaceShipDeck.
     *
     * @param height the height of the spaceShipDeck
     * @param width  the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck newSpaceShipDeck = new SpaceShipDeck(height, width);
        spaceShipDecks.save(newSpaceShipDeck);
        return newSpaceShipDeck.getSpaceShipDeckID();
    }

    /**
     * This method adds a obstacle to a given spaceShipDeck.
     *
     * @param spaceShipDeckId the ID of the spaceShipDeck the obstacle shall be placed on
     * @param obstacle        the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {

        if (obstacle.getStart().getX().equals(obstacle.getEnd().getX())) {
            for (int i = obstacle.getStart().getY(); i < obstacle.getEnd().getY(); i++) {
                spaceShipDecks.findById(spaceShipDeckId).get().getAllObstacles().add(new Obstacle(new Coordinate(obstacle.getStart().getX(), i), new Coordinate(obstacle.getEnd().getX(), i + 1)));
            }
        }
        if (obstacle.getStart().getY().equals(obstacle.getEnd().getY())) {
            for (int i = obstacle.getStart().getX(); i < obstacle.getEnd().getX(); i++) {
                spaceShipDecks.findById(spaceShipDeckId).get().getAllObstacles().add(new Obstacle(new Coordinate(i, obstacle.getStart().getY()), new Coordinate(i + 1, obstacle.getEnd().getY())));
            }
        }
    }

    /**
     * This method adds a transport category
     *
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategories.save(transportCategory);
        return transportCategory.getTransportCategoryUUID();
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     *
     * @param sourceSpaceShipDeckId      the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate           the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate      the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate, UUID transportCategoryID) {
        Connection connection = new Connection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate, transportCategoryID);
        try {
            spaceShipDecks.findById(sourceSpaceShipDeckId).get().findSquareAt(sourceCoordinate).setConnection(connection);
            return connection.getConnectionID();
        } catch (SquareNotFoundException e) {
//            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        maintenanceDroids.save(maintenanceDroid);
        return maintenanceDroid.getMaintenanceDroidID();
    }

    /**
     * This method lets the maintenance droid execute a command.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order              the given command, encoded as a String:
     *                           "[direction, steps]" for movement, e.g. "[we,2]"
     *                           "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another spaceShipDeck
     *                           "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        Command command = StringService.translateOrderToCommand(order);
        MaintenanceDroid maintenanceDroid = maintenanceDroids.findById(maintenanceDroidId).get();
        try {
            if (command.isStepCommand()) {
                return UniverseService.isStepCommand(command, maintenanceDroid, this);
            }
            if (command.isUUIDCommand()) {
                return UniverseService.isUUIDCommand(command, maintenanceDroid, this);
            }
        } catch (MaintenanceDroidPositionIsNull | IllegalStateException | SquareNotFoundException e) {
//            System.out.println(e.toString());
            return false;
        }
        //        System.out.println("executeCommand Failure");
        return false;
    }

    public Boolean isTransportOccupied(UUID squareID, UUID maintenancesDroidID) {
        AtomicReference<Boolean> result = new AtomicReference<>();
        result.set(false);
        maintenanceDroids.findAll().forEach((entry) -> {
            if (entry.getMaintenanceDroidID() != maintenancesDroidID && entry.getSpaceShipDeckId() != null) {
                if (entry.getSquare().getSquareID() == squareID) {
                    result.set(true);
                }
            }
        });
        return result.get();
    }

    public Boolean isSquareOccupied(Direction direction, UUID squareID, UUID maintenancesDroidID, SpaceShipDeck spaceShipDeck) throws SquareNotFoundException {
        AtomicReference<Boolean> result = new AtomicReference<>();
        result.set(false);
        UUID nextSquareID = spaceShipDeck.getNextSquareinDirection(direction, squareID).getSquareID();
        maintenanceDroids.findAll().forEach(maintenanceDroid -> {
            if (maintenanceDroid.getMaintenanceDroidID() != maintenancesDroidID) {
                if (maintenanceDroid.getSquare() != null) {
                    if (maintenanceDroid.getSquare().getSquareID().equals(nextSquareID)) result.set(true);
                }
            }
        });
        return result.get();
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        return maintenanceDroids.findById(maintenanceDroidId).get().getSpaceShipDeckId();
    }

    @Override
    public SpaceShipDeckRepository getSpaceShipDecks() {
        return this.spaceShipDecks;
    }

    @Override
    public MaintenanceDroidRepository getMaintenanceDroids() {
        return this.maintenanceDroids;
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public Coordinate getCoordinates(UUID maintenanceDroidId) {
        return maintenanceDroids.findById(maintenanceDroidId).get().getSquare().getCoordinates();
    }
}
