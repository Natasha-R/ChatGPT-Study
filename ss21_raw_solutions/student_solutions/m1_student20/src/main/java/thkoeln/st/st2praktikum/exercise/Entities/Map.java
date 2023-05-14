package thkoeln.st.st2praktikum.exercise.Entities;

import thkoeln.st.st2praktikum.exercise.Interfaces.*;

import java.util.HashMap;
import java.util.UUID;

public class Map{
    private HashMap<UUID, MaintenanceDroid> groupOfmaintenanceDroids = new HashMap<UUID, MaintenanceDroid>() ;
    private HashMap<UUID, SpaceShipDeck> collectionOfSpaceShipDecks = new HashMap<UUID, SpaceShipDeck>() ;
    private HashMap<UUID, Connection> groupOfConnections= new HashMap<UUID, Connection>() ;
    private HashMap<UUID, Obstacle> groupOfObstacles= new HashMap<UUID, Obstacle>() ;

    public UUID createSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck();
        spaceShipDeck.setHeight(height);
        spaceShipDeck.setWidth(width);
        addSpaceShipDeckIntoMap(spaceShipDeck);
        return (UUID) spaceShipDeck.getSpaceShipDeck_Id();
    }
    private void addSpaceShipDeckIntoMap(SpaceShipDeck spaceShipDeck){
        collectionOfSpaceShipDecks.put((UUID) spaceShipDeck.getSpaceShipDeck_Id(), spaceShipDeck);
    }
    public SpaceShipDeck getSpaceShipDeckByID(UUID spaceShipDeckId){
        return collectionOfSpaceShipDecks.get(spaceShipDeckId);
    }
    public void addObstacleIntoMap(Obstacle obstacle){
        groupOfObstacles.put(obstacle.getObstacle_Id(),obstacle);
    }
    public UUID addConnectionIntoMap(Connection connection){
        groupOfConnections.put(connection.getConnection_Id(), connection);
        return connection.getConnection_Id();
    }
    public UUID addMaintenanceDroidIntoMap(MaintenanceDroid maintenanceDroid){
        groupOfmaintenanceDroids.put(maintenanceDroid.getMaintenanceDroid_Id(), maintenanceDroid);
        return maintenanceDroid.getMaintenanceDroid_Id();
    }
    public MaintenanceDroid getMaintenanceDroidbyID(UUID maintenanceDroidId){
        return groupOfmaintenanceDroids.get(maintenanceDroidId);
    }

    public SpaceShipDeck getSpaceShipDeckOfMaintenanceDroid(MaintenanceDroid maintenanceDroid){
        SpaceShipDeck spaceShipDeck = maintenanceDroid.getSpaceShipDeck();
        return spaceShipDeck;
    }
   public UUID getSpaceShipDeckIDOfSpaceShipDeck(SpaceShipDeck spaceShipDeck){
        if( collectionOfSpaceShipDecks.containsValue(spaceShipDeck))return (UUID) spaceShipDeck.getSpaceShipDeck_Id();
        else return null;
   }

}
