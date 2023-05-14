package thkoeln.st.st2praktikum.exercise;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class MapsStorage {
    private static final HashMap<UUID, SpaceShipDeck> spaceShipDeckMap = new HashMap<>();
    public static void addSpaceShipDeckMap(UUID uuid, SpaceShipDeck spaceShipDeck) {spaceShipDeckMap.put(uuid,spaceShipDeck);}

    public static HashMap<UUID, SpaceShipDeck> getSpaceShipDeckMap() {
        return spaceShipDeckMap;
    }

    private static final HashMap<UUID, MaintenanceDroid>  maintenanceDroidMap = new HashMap<>();
    public static void addMaintenanceDroidMap(UUID uuid, MaintenanceDroid maintenanceDroid){maintenanceDroidMap.put(uuid, maintenanceDroid);}

    public static HashMap<UUID, MaintenanceDroid> getMaintenanceDroidMap() {
        return maintenanceDroidMap;
    }

    private static final HashMap<UUID, Connection> connectionMap = new HashMap<>();
    public static void addConnectionMap(UUID uuid, Connection connection) {connectionMap.put(uuid, connection);}

    public static HashMap<UUID, Connection> getConnectionMap() {
        return connectionMap;
    }

    private static final HashMap<UUID, Barrier> barrierMap = new HashMap<>();
    public static void addBarrierMap(UUID uuid, Barrier barrier) {barrierMap.put(uuid, barrier);}

    public static HashMap<UUID, Barrier> getBarrierMap() {
        return barrierMap;
    }
}
