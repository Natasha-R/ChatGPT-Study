package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public abstract class AbstractRobot implements Identifiable {

    protected UUID id;
    protected String name;
    protected Vector2DRoom position;

    @Override
    public UUID getId() {
        return id;
    }

    public Vector2DRoom getPosition() {
        return position;
    }

    abstract public Boolean processTask(Task task, MapInstance map);
}
