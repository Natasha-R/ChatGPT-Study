package thkoeln.st.st2praktikum.droid;

import lombok.AllArgsConstructor;
import thkoeln.st.st2praktikum.linearAlgebra.Point;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.Connection;
import thkoeln.st.st2praktikum.map.Position;

@AllArgsConstructor
public class ConnectionMovement extends Point implements Movement {

    private final Connection connection;

    @Override
    public Position getSourcePosition() {
        return connection.getSource();
    }

    @Override
    public Position getTargetPosition() {
        return connection.getTarget();
    }

    @Override
    public Movement changeTargetPosition(Position targetPosition) throws IllegalStateException {
        throw new IllegalStateException();
    }

    @Override
    public Vector getCoordinates() {
        return connection.getTarget().getCoordinates();
    }
}
