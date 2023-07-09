package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.Movement;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;


import java.util.UUID;


@Service
public class MaintenanceDroidService {

    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;
    
    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid();
        maintenanceDroid.setName(name);

        maintenanceDroid.setSpaceShipDeckId(UUID.randomUUID());
        maintenanceDroid.setVector2D(new Vector2D(99,99));

        maintenanceDroidRepository.save(maintenanceDroid);

        return maintenanceDroid.getId();
    }

    /**
     * This method lets the maintenance droid execute a task.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        Movement movement = new Movement();
        return movement.executeCommand(maintenanceDroidId, task, spaceShipDeckRepository, maintenanceDroidRepository, getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId));
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findById(maintenanceDroidId).get().getSpaceShipDeckId();
    }

    /**
     * This method returns the vector-2D a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector-2D of the maintenance droid
     */
    public Vector2D getMaintenanceDroidVector2D(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D();
    }
}
