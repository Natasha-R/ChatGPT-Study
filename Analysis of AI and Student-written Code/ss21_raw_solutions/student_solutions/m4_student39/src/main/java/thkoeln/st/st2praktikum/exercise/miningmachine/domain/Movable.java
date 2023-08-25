package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

public interface Movable {
    public void placeOnMap(Field map, Vector2D newPosition);
    public boolean useConnection(Field desiredMap);
    public boolean move(Order moveCommand);
}
