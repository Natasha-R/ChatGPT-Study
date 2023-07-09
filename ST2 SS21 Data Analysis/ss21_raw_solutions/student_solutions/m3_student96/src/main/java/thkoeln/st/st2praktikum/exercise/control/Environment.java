package thkoeln.st.st2praktikum.exercise.control;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.map.connection.Connection;
import thkoeln.st.st2praktikum.exercise.map.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.map.Locatable;
import thkoeln.st.st2praktikum.exercise.map.connection.TransportCategory;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class Environment {
    private HashMap<UUID, SpaceShipDeck> grids = new HashMap<>();
    private HashMap<UUID, MaintenanceDroid> droids = new HashMap<>();
    private HashMap<UUID, Connection> connections = new HashMap<>();
    private HashMap<UUID, TransportCategory> transportCategories = new HashMap<>();

    //Singleton Pattern Quelle: https://www.youtube.com/watch?v=3cJbDs-zzpw
    //private static Environment environment = new Environment();

    public UUID addDroid(String name){
        MaintenanceDroid newMaintenanceDroid = new MaintenanceDroid(name);
        droids.put(newMaintenanceDroid.getDroidID(), newMaintenanceDroid);
        return newMaintenanceDroid.getDroidID();
    }

    public UUID addGrid(int height, int width) {
        SpaceShipDeck newDeck = new SpaceShipDeck(height, width);
        addGrid(newDeck);
        return newDeck.getGridID();
    }

    public UUID addGrid(SpaceShipDeck spaceShipDeck){
        grids.put(spaceShipDeck.getGridID(), spaceShipDeck);
        return spaceShipDeck.getGridID();
    }

    public UUID addConnection( UUID transportCategoryId, UUID sourceSpaceShipDeckId,
                               Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinatPoint) {
        Locatable sourcePointOnGrid = getPointFromGrid(sourceSpaceShipDeckId, sourcePoint.toString() );
        Locatable destinationPointOnGrid = getPointFromGrid(destinationSpaceShipDeckId,destinatPoint.toString());
        TransportCategory transportCategory = transportCategories.get(transportCategoryId);
        Connection newConnection = new Connection(transportCategory , sourcePointOnGrid , destinationPointOnGrid );
        connections.put(newConnection.getConnectionID(), newConnection);
        return newConnection.getConnectionID();
    }

    public UUID addTransportCategory(String category){
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategories.put(transportCategory.getTransportCategoryId(), transportCategory);
        return transportCategory.getTransportCategoryId();
    }

    public Locatable getPointFromGrid(UUID gridID, String coordinateString){
        return getGrids().get(gridID).getPointFromSpaceShipDeck(coordinateString);
    }

    public HashMap<String, Locatable> getPoints(UUID deckID){
        return getGrids().get(deckID).getPoints();
    }
}