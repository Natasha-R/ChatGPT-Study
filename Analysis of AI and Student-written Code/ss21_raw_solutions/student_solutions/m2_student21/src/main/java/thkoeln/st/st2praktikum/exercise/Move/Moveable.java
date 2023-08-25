package thkoeln.st.st2praktikum.exercise.Move;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Room.MoveAllowed;

public interface Moveable {
    /**
     *  Moves an object in a given Room, X-Amount of steps in the intended direction and returns the new position
     * @param direction is of type OrderType
     * @param steps is the amount of steps the object intends to move
     * @param coordinate this is the objects current position
     * @param room this is the room in which the objects wants to move which has to implement the MoveAllowed Interface
     */
    public Coordinate move(OrderType direction, Integer steps, Coordinate coordinate, MoveAllowed room);
}
