package thkoeln.st.st2praktikum.droid;

import lombok.Getter;
import thkoeln.st.st2praktikum.linearAlgebra.BoundedStraight;
import thkoeln.st.st2praktikum.linearAlgebra.Cuttable;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.Position;

import java.util.Optional;

@Getter
public class MapMovement extends BoundedStraight implements Movement {
    private final Position sourcePosition;
    private final Position targetPosition;
    private Droid droid;

    MapMovement(Position sourcePosition, Position targetPosition) {
        super(sourcePosition.getCoordinates(), targetPosition
                .getCoordinates());

        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
    }

    MapMovement(Position sourcePosition, Position targetPosition, Droid droid) {
        super(sourcePosition.getCoordinates(), targetPosition
                .getCoordinates());

        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
        this.droid = droid;
    }

    @Override
    public Position getTargetPosition() {
        return this.targetPosition;
    }

    @Override
    public Movement changeTargetPosition(Position targetPosition) {
        return new MapMovement(this.sourcePosition, targetPosition);
    }

    @Override
    public Optional<Vector> cut(Cuttable cuttable) {
        if(cuttable == this.droid) {
            return Optional.empty();
        }
        return super.cut(cuttable);
    }
}
