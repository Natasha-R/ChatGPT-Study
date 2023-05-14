package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public interface Movable {
    boolean executeCommand(Task task, List<Field> fields, List<Movable> movables);
    boolean move(TaskType taskType, Integer steps, List<Field> fields, List<Movable> movables);
    boolean transport( List<Movable> movables, List<Field> fields);
    boolean enter( List<Movable> movables, UUID targetId);
    boolean isPlaced();
    boolean isBlockingCoordinate(UUID fieldId, Coordinate coordinate);
    Coordinate getCoordinate();
    UUID getId();
    UUID getFieldId();
}
