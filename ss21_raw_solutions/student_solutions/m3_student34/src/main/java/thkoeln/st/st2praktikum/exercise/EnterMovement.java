package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
