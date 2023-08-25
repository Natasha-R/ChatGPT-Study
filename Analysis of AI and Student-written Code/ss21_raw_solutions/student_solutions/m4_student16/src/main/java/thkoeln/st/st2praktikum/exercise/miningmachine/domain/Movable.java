package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;

import java.util.List;
import java.util.UUID;

public interface Movable {
    boolean executeCommand(Task task, List<Field> fields, List<Movable> movables, List<TransportSystem> transportSystems);
    boolean move(TaskType taskType, Integer steps, List<Field> fields, List<Movable> movables);
    boolean transport( List<Movable> movables, List<Field> fields, List<TransportSystem> transportSystems);
    boolean enter( List<Movable> movables, UUID targetId);
    boolean isPlaced();
    boolean isBlockingCoordinate(UUID fieldId, Coordinate coordinate);
    Coordinate getCoordinate();
    UUID getId();
    UUID getFieldId();
}
