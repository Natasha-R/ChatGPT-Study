package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.Entities.*;
import thkoeln.st.st2praktikum.exercise.Interfaces.*;


import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
//Wird hier ein Applicationlistener benötigt??

public class MaintenanceDroidService {



    //SpaceShip spaceshipEnterprise = new SpaceShip();
    Map map = new Map();


    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */


    public UUID addSpaceShipDeck(Integer height, Integer width) { return map.createSpaceShipDeck(height,width); }

    /**
     * This method adds a obstacle to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceShipDeckId, String obstacleString) {
      CreateObstacleable obstacleFactory = new ObstacleFactory();
      map.addObstacleIntoMap(obstacleFactory.createObstacle(map.getSpaceShipDeckByID(spaceShipDeckId),obstacleString));
      obstacleFactory.addObstacleOnSpaceShipDeck(map.getSpaceShipDeckByID(spaceShipDeckId));
    }


    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        ImplementConnectionable connectionfactory = new ConnectionFactory();
        Connection connection = connectionfactory.createConnection(map.getSpaceShipDeckByID(sourceSpaceShipDeckId), sourceCoordinate, map.getSpaceShipDeckByID(destinationSpaceShipDeckId),destinationCoordinate);
        UUID connectionIDOfCreatedConnection = map.addConnectionIntoMap(connection);
        connectionfactory.addConnectionOnSpaceShipDeck(map.getSpaceShipDeckByID(sourceSpaceShipDeckId),connection);
       return connectionIDOfCreatedConnection;

    }


    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        CreateMaintenanceDroidable headQuarter = new MaintenanceDroidHeadQuarters();
       return map.addMaintenanceDroidIntoMap(headQuarter.createMaintenanceDroid(name));
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {

     Commandable command = new MaintenanceDroidHeadQuarters();
        ExecuteCommandSplitter splittcommand = new ExecuteCommandSplitter();
        String [] splittedCommandString = splittcommand.splittCommandString(commandString);
        String directionOrCommand = splittedCommandString[0];
        switch (directionOrCommand){
            case "tr":{
              return command.droidGoOverAnConnection(map.getMaintenanceDroidbyID(maintenanceDroidId), map.getSpaceShipDeckByID(UUID.fromString(splittedCommandString[1])));
            }
            case "en":{
                return command.droidGoOnSpaceShipDeck(map.getMaintenanceDroidbyID(maintenanceDroidId),map.getSpaceShipDeckByID(UUID.fromString(splittedCommandString[1])));
            }
            default:{
                return command.droidGoSteps(map.getMaintenanceDroidbyID(maintenanceDroidId),directionOrCommand,Integer.parseInt(splittedCommandString[1]));
            }

        }




    }


    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
    UUID spaceShipDeckId =  map.getSpaceShipDeckIDOfSpaceShipDeck(map.getSpaceShipDeckOfMaintenanceDroid(map.getMaintenanceDroidbyID(maintenanceDroidId)));
    return spaceShipDeckId;
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        MaintenanceDroid maintenanceDroid = map.getMaintenanceDroidbyID(maintenanceDroidId);
        Coordinates coordinates = maintenanceDroid.getXYCoordinatesOfMaintenanceDroid();
        return "("+ coordinates.getX_Coordinate() + "," + coordinates.getY_Coordinate() + ")";
    }
}
