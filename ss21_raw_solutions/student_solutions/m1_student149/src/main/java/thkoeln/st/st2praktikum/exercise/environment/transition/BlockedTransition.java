package thkoeln.st.st2praktikum.exercise.environment.transition;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;

public class BlockedTransition extends Transition<Position> {

    public BlockedTransition(Position fromPosition, Position toPosition) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

}
