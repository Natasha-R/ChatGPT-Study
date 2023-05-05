package thkoeln.st.st2praktikum.droid;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thkoeln.st.st2praktikum.linearAlgebra.Point;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.Position;
import thkoeln.st.st2praktikum.map.StartPosition;

@RequiredArgsConstructor
@Getter
public class EnterMovement extends Point implements Movement {

    private final StartPosition sourcePosition;
    private final Position targetPosition;

    @Override
    public Movement changeTargetPosition(Position targetPosition) {
        throw new IllegalStateException("EnterMovement cannot be altered");
    }

    @Override
    public Vector getCoordinates() {
        return targetPosition.getCoordinates();
    }
}
