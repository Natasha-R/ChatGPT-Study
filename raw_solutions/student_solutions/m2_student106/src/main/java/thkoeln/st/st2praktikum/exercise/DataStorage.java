package thkoeln.st.st2praktikum.exercise;


import thkoeln.st.st2praktikum.exercise.field.Connection;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.movable.Moveable;

import java.util.HashMap;
import java.util.UUID;

public class DataStorage {
    private static final HashMap<UUID, Field> fieldMap = new HashMap<>();
    private static final HashMap<UUID, Moveable> moveableMap = new HashMap<>();
    private static final HashMap<UUID, Connection> connectionMap = new HashMap<>();

    public static void addToFieldMap(UUID uuid, Field field) {
        fieldMap.put(uuid, field);
    }

    public static void addToMoveableMap(UUID uuid, Moveable moveable) {
        moveableMap.put(uuid, moveable);
    }

    public static void addToConnectionMap(UUID uuid, Connection connection) {
        connectionMap.put(uuid, connection);
    }


    public static HashMap<UUID, Field> getFieldMap() {
        return fieldMap;
    }

    public static HashMap<UUID, Moveable> getMoveableMap() {
        return moveableMap;
    }

    public static HashMap<UUID, Connection> getConnectionMap() {
        return connectionMap;
    }

}
