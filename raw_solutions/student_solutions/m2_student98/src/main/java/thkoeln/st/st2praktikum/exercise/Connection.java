package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements Connectable {

    private final UUID id;
    private final Vector2DRoom fromPosition;
    private final Vector2DRoom toPosition;


    public Connection(Vector2DRoom fromPosition, Vector2DRoom toPosition) {
        this.id = UUID.randomUUID();
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Vector2DRoom getFromPosition() {
        return fromPosition;
    }

    @Override
    public Vector2DRoom getToPosition() {
        return toPosition;
    }
}
