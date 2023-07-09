package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ConcreteRectangle extends Rectangle {
    private final Vector upperLeftCorner;
    private final Vector lowerLeftCorner;
    private final Vector upperRightCorner;
    private final Vector lowerRightCorner;

    @Override
    public BoundedStraight[] getSites() {
        return new BoundedStraight[]{
                new ConcreteBoundedStraight(upperLeftCorner, upperRightCorner),
                new ConcreteBoundedStraight(lowerLeftCorner, lowerRightCorner),
                new ConcreteBoundedStraight(lowerLeftCorner, upperLeftCorner),
                new ConcreteBoundedStraight(lowerRightCorner, upperRightCorner)
        };
    }
}
