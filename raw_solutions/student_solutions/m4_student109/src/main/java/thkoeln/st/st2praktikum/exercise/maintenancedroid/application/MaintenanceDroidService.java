package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidDto;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidDtoMapper;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckRepository;


import java.util.List;
import java.util.UUID;


@Service
public class MaintenanceDroidService {
    private MaintenanceDroidRepository maintenanceDroidRepository;
    private SpaceShipDeckRepository spaceShipDeckRepository;
    private MaintenanceDroidDtoMapper maintenanceDroidDtoMapper = new MaintenanceDroidDtoMapper();


    @Autowired
    public MaintenanceDroidService (MaintenanceDroidRepository maintenanceDroidRepository, SpaceShipDeckRepository spaceShipDeckRepository) {
        this.maintenanceDroidRepository = maintenanceDroidRepository;
        this.spaceShipDeckRepository = spaceShipDeckRepository;
    }


    public MaintenanceDroid MDfindById( UUID id ) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById( id ).orElseThrow( () ->
                new RuntimeException( "No dungeon with ID " + id + " can be found.") );
        return maintenanceDroid;
    }

    public SpaceShipDeck SSDfindById( UUID id ) {
        SpaceShipDeck spaceShipDeck = spaceShipDeckRepository.findById( id ).orElseThrow( () ->
                new RuntimeException( "No dungeon with ID " + id + " can be found.") );
        return spaceShipDeck;
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        UUID maintenanceDroidsId = UUID.randomUUID();
        maintenanceDroidRepository.save(new MaintenanceDroid(name, maintenanceDroidsId));
        return maintenanceDroidsId;
    }

    /**
     * This method lets the maintenance droid execute a task.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        MaintenanceDroid m = MDfindById(maintenanceDroidId);
        m.addTask(task);
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
            MaintenanceDroid maintenanceDroid = MDfindById(maintenanceDroidId);
            SpaceShipDeck spaceShipDeck = SSDfindById(maintenanceDroid.getSpaceShipDeckId());
            MaintenanceDroid newMD = spaceShipDeck.moveMaintenanceDroid(maintenanceDroid, command, step, (List<MaintenanceDroid>) maintenanceDroidRepository.findAll());
            if (newMD == null){
                return false;
            }
            maintenanceDroid = newMD;
            maintenanceDroidRepository.save(maintenanceDroid);
            return true;
    }

    private boolean entry(UUID maintenanceDroidId, String step) {
        MaintenanceDroid maintenanceDroid = MDfindById(maintenanceDroidId);
        SpaceShipDeck spaceShipDeck = SSDfindById(UUID.fromString(step));
        if (spaceShipDeck.checkPositionForDroid((List<MaintenanceDroid>) maintenanceDroidRepository.findAll(), 0, 0)) {
            maintenanceDroid.setSpaceShipDeckId(UUID.fromString(step));
            maintenanceDroid.setVector2D(new Vector2D(0, 0));
            maintenanceDroidRepository.save(maintenanceDroid);
            return true;
        }

        return false;
    }

    private boolean transport(UUID maintenanceDroidId, String step) {
        MaintenanceDroid maintenanceDroid = MDfindById(maintenanceDroidId);
        SpaceShipDeck spaceShipDeck = SSDfindById(maintenanceDroid.getSpaceShipDeckId());
                        MaintenanceDroid tmp = spaceShipDeck.transport(maintenanceDroid, step, (List<MaintenanceDroid>) maintenanceDroidRepository.findAll());
                        if (maintenanceDroid.getSpaceShipDeckId().equals(tmp.getMaintenanceDroidsId())) {
                            maintenanceDroidRepository.save(maintenanceDroid);
                            return true;
                        }
        return false;
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return MDfindById(maintenanceDroidId).getSpaceShipDeckId();
    }

    /**
     * This method returns the vector-2D a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector-2D of the maintenance droid
     */
    public Vector2D getMaintenanceDroidVector2D(UUID maintenanceDroidId){
        return MDfindById(maintenanceDroidId).getVector2D();
    }


    public Iterable<MaintenanceDroid> findAll() {
        return maintenanceDroidRepository.findAll();
    }

    public MaintenanceDroid createMaintenanceDroidFromDto(MaintenanceDroidDto maintenanceDroidDto) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidDtoMapper.mapToEntity( maintenanceDroidDto );
        maintenanceDroidRepository.save(maintenanceDroid);
        return maintenanceDroid;

    }

    public void deleteById(UUID id) {
        maintenanceDroidRepository.deleteById(id);
    }
}
