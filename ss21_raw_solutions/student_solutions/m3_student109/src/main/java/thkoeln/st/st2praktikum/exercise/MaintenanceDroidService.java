package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MaintenanceDroidService {

    private List<SpaceShipDeck> spaceShipDecks = new ArrayList<>();
    private List<MaintenanceDroid> maintenanceDroids = new ArrayList<>();
    private List<TransportSystem> transportSystems = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    private TransportSystemRepository transportSystemRepository;
    private MaintenanceDroidRepository maintenanceDroidRepository;
    private SpaceShipDeckRepository spaceShipDeckRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public MaintenanceDroidService(TransportSystemRepository transportSystemRepository, MaintenanceDroidRepository maintenanceDroidRepository, SpaceShipDeckRepository spaceShipDeckRepository, ConnectionRepository connectionRepository) {
        this.transportSystemRepository = transportSystemRepository;
        this.maintenanceDroidRepository = maintenanceDroidRepository;
        this.spaceShipDeckRepository = spaceShipDeckRepository;
        this.connectionRepository = connectionRepository;
    }

    /**
     * This method creates a new spaceShipDeck.
     *
     * @param height the height of the spaceShipDeck
     * @param width  the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        UUID spaceShipDeskId = UUID.randomUUID();
        spaceShipDecks.add(new SpaceShipDeck(height, width, spaceShipDeskId));
        return spaceShipDeskId;
    }

    /**
     * This method adds a obstacle to a given spaceShipDeck.
     *
     * @param spaceShipDeckId the ID of the spaceShipDeck the obstacle shall be placed on
     * @param obstacle        the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
        for (int i = 0; i < spaceShipDecks.size(); i++) {
            if (spaceShipDecks.get(i).getSpaceShipDeskId().equals(spaceShipDeckId)) {
                if (spaceShipDecks.get(i).getHeight() < obstacle.getStart().getY() || spaceShipDecks.get(i).getWidth() < obstacle.getStart().getX() ||
                        spaceShipDecks.get(i).getHeight() < obstacle.getEnd().getY() || spaceShipDecks.get(i).getWidth() < obstacle.getEnd().getX()) {
                    throw new RuntimeException("Obstacle invalid");
                }
                spaceShipDecks.get(i).addObstacleToList(obstacle);
            }
        }
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        UUID transportId = UUID.randomUUID();
        transportSystems.add(new TransportSystem(transportId, system));
        return transportId;
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     *
     *
     * @param transportSystemId
     * @param sourceSpaceShipDeckId      the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceVector2D             the vector2Ds of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationVector2D        the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceSpaceShipDeckId, Vector2D sourceVector2D, UUID destinationSpaceShipDeckId, Vector2D destinationVector2D) {
        UUID connectionId = UUID.randomUUID();
        for (TransportSystem transportSystem : transportSystems) {
            if (transportSystem.getTransportSystemID().equals(transportSystemId)) {
                for (SpaceShipDeck spaceShipDeck : spaceShipDecks) {
                    if (spaceShipDeck.getSpaceShipDeskId().equals(sourceSpaceShipDeckId)) {
                        if (spaceShipDeck.getHeight() < sourceVector2D.getY() || spaceShipDeck.getWidth() < sourceVector2D.getX()) {
                            throw new RuntimeException("Connection invalid");
                        }
                        for (SpaceShipDeck destinationSpaceShipDeck : spaceShipDecks) {
                            if (destinationSpaceShipDeck.getSpaceShipDeskId().equals(destinationSpaceShipDeckId)) {
                                if (destinationSpaceShipDeck.getHeight() < sourceVector2D.getY() || destinationSpaceShipDeck.getWidth() < sourceVector2D.getX()) {
                                    throw new RuntimeException("Connection invalid");
                                }
                            }
                        }
                        Connection c = new Connection(transportSystemId, sourceSpaceShipDeckId, sourceVector2D, destinationSpaceShipDeckId, destinationVector2D, connectionId);
                        connections.add(c);
                        spaceShipDeck.addConnection(c);
                        database();
                    }
                }
            }
        }
        return connectionId;
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        UUID maintenanceDroidsId = UUID.randomUUID();
        maintenanceDroids.add(new MaintenanceDroid(name, maintenanceDroidsId));
        return maintenanceDroidsId;
    }

    /**
     * This method lets the maintenance droid execute a task.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task               the given task
     *                           NORTH, WEST, SOUTH, EAST for movement
     *                           TRANSPORT for transport - only works on squares with a connection to another spaceShipDeck
     *                           ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        System.out.println(task.getTaskType());
        switch (task.getTaskType()) {
            case NORTH:
            case EAST:
            case WEST:
            case SOUTH:
                return move(maintenanceDroidId, task.getTaskType(), task.getNumberOfSteps().toString());
            case TRANSPORT:
                return transport(maintenanceDroidId, task.getGridId().toString());
            case ENTER:
                return entry(maintenanceDroidId, task.getGridId().toString());
            default:
                return false;
        }
    }

    private boolean move(UUID maintenanceDroidId, TaskType command, String step) {
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (maintenanceDroid.getMaintenanceDroidsId().equals(maintenanceDroidId)) {
                for (SpaceShipDeck spaceShipDeck : spaceShipDecks) {
                    if (spaceShipDeck.getSpaceShipDeskId().equals(maintenanceDroid.getSpaceShipDeckId())) {
                        spaceShipDeck.moveMaintenanceDroid(maintenanceDroid, command, step, maintenanceDroids);
                        database();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean entry(UUID maintenanceDroidId, String step) {
        for (SpaceShipDeck spaceShipDeck : spaceShipDecks) {
            if (UUID.fromString(step).equals(spaceShipDeck.getSpaceShipDeskId())) {
                for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
                    if (maintenanceDroid.getMaintenanceDroidsId().equals(maintenanceDroidId)) {
                        if (spaceShipDeck.checkPositionForDroid(maintenanceDroids, 0, 0)) {
                            maintenanceDroid.setSpaceShipDeckId(UUID.fromString(step));
                            maintenanceDroid.setVector2D(new Vector2D(0, 0));
                            database();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean transport(UUID maintenanceDroidId, String step) {
        int c = 0;
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (maintenanceDroid.getMaintenanceDroidsId().equals(maintenanceDroidId)) {
                for (SpaceShipDeck spaceShipDeck : spaceShipDecks) {
                    if (maintenanceDroid.getSpaceShipDeckId().equals(spaceShipDeck.getSpaceShipDeskId())) {
                        MaintenanceDroid tmp = spaceShipDeck.transport(maintenanceDroid, step, maintenanceDroids);
                        if (maintenanceDroid.getSpaceShipDeckId().equals(tmp.getMaintenanceDroidsId())) {
                            maintenanceDroids.set(c, tmp);
                            database();
                            return true;
                        }
                    }
                }
            }
            c++;
        }
        database();
        return false;
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        for (int i = 0; i < maintenanceDroids.size(); i++) {
            if (maintenanceDroids.get(i).getMaintenanceDroidsId().equals(maintenanceDroidId)) {
                return maintenanceDroids.get(i).getSpaceShipDeckId();
            }
        }
        return null;
    }

    /**
     * This method returns the vector2Ds a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector2Ds of the maintenance droid
     */
    public Vector2D getVector2D(UUID maintenanceDroidId) {
        for (int i = 0; i < maintenanceDroids.size(); i++) {
            if (maintenanceDroids.get(i).getMaintenanceDroidsId().equals(maintenanceDroidId)) {
                return maintenanceDroids.get(i).getVector2D();
            }
        }
        return null;
    }

    public void database(){
        transportSystemRepository.deleteAll();
        maintenanceDroidRepository.deleteAll();
        spaceShipDeckRepository.deleteAll();
        connectionRepository.deleteAll();

        for( TransportSystem transportSystem : transportSystems ) transportSystemRepository.save( transportSystem );
        for( Connection connection :  connections) connectionRepository.save( connection );
        for( SpaceShipDeck spaceShipDeck : spaceShipDecks ) spaceShipDeckRepository.save( spaceShipDeck );
        for( MaintenanceDroid maintenanceDroid : maintenanceDroids ) maintenanceDroidRepository.save( maintenanceDroid );
    }

}


