package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.repository.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import java.util.Optional;
import java.util.UUID;


@Service
public class MaintenanceDroidService {

    private MaintenanceDroidRepository maintenanceDroidRepository;
    private final SpaceShipDeckService spaceShipDeckService;
    private MaintenanceDroidDtoMapper maintenanceDroidDtoMapper = new MaintenanceDroidDtoMapper();

    @Autowired
    public MaintenanceDroidService(MaintenanceDroidRepository maintenanceDroidRepository, SpaceShipDeckService spaceShipDeckService) {
        this.maintenanceDroidRepository = maintenanceDroidRepository;
        this.spaceShipDeckService = spaceShipDeckService;
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        this.maintenanceDroidRepository.save(maintenanceDroid);
        return maintenanceDroid.getUuid();
    }

    /**
     * This method lets the maintenance droid execute a order.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order              the given order
     *                           NORTH, WEST, SOUTH, EAST for movement
     *                           TRANSPORT for transport - only works on squares with a connection to another spaceship deck
     *                           ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a wall or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        saveOrderInMaintenanceDroid(maintenanceDroidId, order);
        if (order.isInitialisationCommand()) {
            return initialiseDroid(maintenanceDroidId, order);
        }

        if (order.isTransportCommand()) {
            return transportDroid(maintenanceDroidId, order);
        }

        if (order.isMoveCommand()) {
            return moveDroid(maintenanceDroidId, order);
        }

        return false;
    }

    private Boolean moveDroid(UUID maintenanceDroidId, Order command) {
        // get Droid
        MaintenanceDroid maintenanceDroid = findById(maintenanceDroidId);
        // get SpaceshipDeck
        SpaceShipDeck spaceShipDeck = spaceShipDeckService.findById(maintenanceDroid.getSpaceShipDeckId());

        maintenanceDroidRepository.save(spaceShipDeck.moveDroid(maintenanceDroidId, command));

        return true;
    }

    private Boolean transportDroid(UUID maintenanceDroidId, Order order) {
        try {
            SpaceShipDeck source = getSpaceShipByDroidUUID(maintenanceDroidId);
            MaintenanceDroid maintenanceDroid = findById(maintenanceDroidId);
            Connection connection = source.transportDroid(maintenanceDroid);

            SpaceShipDeck destination = spaceShipDeckService.findById(connection.getDestinationSpaceShipDeckId());
            maintenanceDroid.setSpaceShipDeckId(destination.getId());
            return destination.addDroid(maintenanceDroid, connection.getDestinationCoordinate());
        } catch (Exception e) {
            return false;
        }
    }

    private SpaceShipDeck getSpaceShipByDroidUUID(UUID droidUuid) throws Exception {
        for (SpaceShipDeck spaceShipDeck : spaceShipDeckService.findAll()) {
            for (MaintenanceDroid maintenanceDroid : spaceShipDeck.getMaintenanceDroids()) {
                if (maintenanceDroid.getUuid().equals(droidUuid)) {
                    return spaceShipDeck;
                }
            }
        }

        throw new Exception("SpaceShipDeck not found");
    }

    private Boolean initialiseDroid(UUID maintenanceDroidId, Order command) {
        SpaceShipDeck spaceShipDeck = spaceShipDeckService.findById(command.getGridId());

        if (spaceShipDeck.canPlaceDroid()) {
            MaintenanceDroid maintenanceDroid = findById(maintenanceDroidId);
            spaceShipDeck.addMaintenanceDroid(maintenanceDroid);
            maintenanceDroid.setSpaceShipDeckId(spaceShipDeck.getId());
            maintenanceDroid.setCoordinates(new Vector2D(0, 0));
            save(maintenanceDroid);
            return true;
        }
        return false;
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        Optional<MaintenanceDroid> optionalMaintenanceDroid = this.maintenanceDroidRepository.findById(maintenanceDroidId);
        if (optionalMaintenanceDroid.isEmpty()) {
            throw new RuntimeException();
        }
        MaintenanceDroid maintenanceDroid = optionalMaintenanceDroid.get();
        return maintenanceDroid.getSpaceShipDeckId();
    }

    /**
     * This method returns the vector-2D a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector-2D of the maintenance droid
     */
    public Vector2D getMaintenanceDroidVector2D(UUID maintenanceDroidId) {
        Optional<MaintenanceDroid> optionalMaintenanceDroid = this.maintenanceDroidRepository.findById(maintenanceDroidId);
        if (optionalMaintenanceDroid.isEmpty()) {
            throw new RuntimeException();
        }
        MaintenanceDroid maintenanceDroid = optionalMaintenanceDroid.get();
        return maintenanceDroid.getVector2D();
    }

    public Iterable<MaintenanceDroid> findAll() {
        return maintenanceDroidRepository.findAll();
    }

    public MaintenanceDroid findById(UUID id) {
        return maintenanceDroidRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No maintenanceDroid with ID " + id + " can be found."));
    }

    public MaintenanceDroid createMaintenanceDroidFromDto(MaintenanceDroidDto maintenanceDroidDto) {
        MaintenanceDroid monster = maintenanceDroidDtoMapper.mapToEntity(maintenanceDroidDto);
        save(monster);
        return monster;
    }

    public void save(MaintenanceDroid maintenanceDroid) {
        maintenanceDroidRepository.save(maintenanceDroid);
    }

    public void deleteById(UUID id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No maintenanceDroid with ID " + id + " can be found."));
        maintenanceDroidRepository.delete(maintenanceDroid);
    }

    private void saveOrderInMaintenanceDroid(UUID maintenanceDroidId, Order order) {
        MaintenanceDroid maintenanceDroid = this.findById(maintenanceDroidId);
        maintenanceDroid.getOrders().add(order);
        maintenanceDroidRepository.save(maintenanceDroid);
    }
}
