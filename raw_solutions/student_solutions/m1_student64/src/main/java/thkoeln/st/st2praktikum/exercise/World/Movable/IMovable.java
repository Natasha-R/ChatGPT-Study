package thkoeln.st.st2praktikum.exercise.World.Movable;

import thkoeln.st.st2praktikum.exercise.World.IHasUUID;
import thkoeln.st.st2praktikum.exercise.World.Point;
import thkoeln.st.st2praktikum.exercise.World.Space.Space;

public interface IMovable extends IHasUUID {
    Point getPosition();
    boolean isPlaced();
    Space getSpace();
    void setSpace(Space space);
    boolean move(MovementDirectionEnum direction, int steps);
}
