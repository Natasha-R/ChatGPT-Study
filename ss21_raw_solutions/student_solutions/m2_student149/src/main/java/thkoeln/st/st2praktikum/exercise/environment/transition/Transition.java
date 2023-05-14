package thkoeln.st.st2praktikum.exercise.environment.transition;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.uuid.UUIDElement;

public abstract class Transition<P extends Position> extends UUIDElement {

    protected P fromPosition;
    protected P toPosition;

    public P getFromPosition() {
        return fromPosition;
    }

    public P getToPosition() {
        return toPosition;
    }

    public P getOtherPosition(P position) {
        if (!isInvolved(position)) {
            return null;
        }
        return fromPosition.equals(position) ? toPosition : fromPosition;
    }

    public boolean isInvolved(P position) {
        return fromPosition.equals(position) || toPosition.equals(position);
    }

    @Override
    public String toString() {
        return "Transition{" + fromPosition + " || " + toPosition + '}';
    }
}
