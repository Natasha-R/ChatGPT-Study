package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Vector2DRoom extends Vector2D {

    private final AbstractRoom room;


    public Vector2DRoom(AbstractRoom room, Integer x, Integer y) throws InvalidInputException {
        super(x, y);
        this.room = room;
    }

    public Vector2DRoom(AbstractRoom room, String vector2DString) throws InvalidInputException {
        super(vector2DString);
        this.room = room;
    }

    public Vector2DRoom(AbstractRoom room, Vector2D vector) {
        super(vector.getX(), vector.getY());
        this.room = room;
    }

    public AbstractRoom getRoom() {
        return this.room;
    }

    public UUID getRoomID()  {
        return this.room.getId();
    }

    public Vector2D getNewVector2DInstance() {
        return new Vector2D(super.x, super.y);
    }

    public Boolean equals(Vector2DRoom newPosition) {
        if (x != newPosition.getX())
            return false;
        if (y != newPosition.getY())
            return false;
        if (room.getId() != newPosition.getRoomID())
            return false;
        return true;
    }
}
