package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.World;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;


import java.util.UUID;


@Service
public class MaintenanceDroidService {

    @Autowired
    private World world;
    
    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return world.createMaintenanceDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Command command) {
        return world.canTheCommandExecute(maintenanceDroidId, command);
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return world.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId);
    }

    /**
     * This method returns the point a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the point of the maintenance droid
     */
    public Point getMaintenanceDroidPoint(UUID maintenanceDroidId){
       return world.getPoints(maintenanceDroidId);
    }

    public MaintenanceDroid getMaintenanceDroidById(UUID maintenanceDroid){
        return world.getMaintenanceDroidById(maintenanceDroid);
    }
}
