package thkoeln.st.st2praktikum.exercise;


import java.util.HashMap;
import java.util.UUID;

interface Movable {
    boolean executeCommand(Task task, HashMap<UUID,Field> fields, HashMap<UUID, Movable> movables);
    boolean move(TaskType taskType, Integer steps, HashMap<UUID,Field> fields, HashMap<UUID, Movable> movables);
    boolean transport(HashMap<UUID, Movable> movables, HashMap<UUID,Field> fields);
    boolean enter(HashMap<UUID, Movable> movables, UUID targetId);
    boolean isPlaced();
    boolean isBlockingCoordinate(UUID fieldId, Coordinate coordinate);
    Coordinate getCoordinate();
    UUID getId();
    UUID getFieldId();

}
