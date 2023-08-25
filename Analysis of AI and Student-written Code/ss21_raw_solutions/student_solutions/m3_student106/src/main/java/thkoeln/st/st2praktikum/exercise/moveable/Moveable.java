package thkoeln.st.st2praktikum.exercise.moveable;

import thkoeln.st.st2praktikum.exercise.Blockable;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Task;

import java.util.UUID;

public interface Moveable extends Blockable {
    boolean move(Task task);

    void setName(String name);

    java.util.UUID getUuid();

    String getName();

    UUID getFieldId();

    Point getPoint();
}
