package thkoeln.st.st2praktikum.exercise.Move;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Room.MoveAllowed;

public abstract class Movement implements Moveable{

    // check if the move is allowed and create a new Hitbox based on the updated position
    public abstract Coordinate move(OrderType direction, Integer steps, Coordinate coordinate, MoveAllowed room);

    public abstract Coordinate moveDirection(OrderType direction, Hitbox hitbox, MoveAllowed room);

    public abstract boolean canGoNorth(Hitbox hitbox, MoveAllowed room);

    public abstract boolean canGoEast(Hitbox hitbox, MoveAllowed room);

    public abstract boolean canGoSouth(Hitbox hitbox, MoveAllowed room);

    public abstract boolean canGoWest(Hitbox hitbox, MoveAllowed room);
}
