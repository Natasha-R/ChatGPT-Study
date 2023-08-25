package thkoeln.st.st2praktikum.exercise.movable;

public interface Moveable {
    boolean chooseAction(String commandString);

    void setName(String name);

    java.util.UUID getUuid();

    String getName();

    java.util.UUID getFieldId();

    thkoeln.st.st2praktikum.exercise.field.Coordinate getPosition();
}
