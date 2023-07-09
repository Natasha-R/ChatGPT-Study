package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.util.UUID;

public interface Moveable extends Blockable {
    boolean move(Task task);

    void setName(String name);

    UUID getUuid();

    String getName();

    UUID getFieldId();

    Point getPoint();
}
