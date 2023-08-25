package thkoeln.st.st2praktikum.exercise.environment;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.environment.transition.BlockedTransition;
import thkoeln.st.st2praktikum.exercise.uuid.UUIDElement;

import java.util.ArrayList;
import java.util.List;

public class Space extends UUIDElement {

    private final int width;
    private final int height;

    private final List<BlockedTransition> blockedTransitions = new ArrayList<>();

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addBlockedTransitions(Barrier barrier) {
        blockedTransitions.addAll(barrier.getBlockedTransitions());
    }

    public void addBlockedTransitions(List<Barrier> barriers) {
        barriers.forEach(barrier -> addBlockedTransitions(barrier));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<BlockedTransition> getBlockedTransitions() {
        return blockedTransitions;
    }

    public boolean isOnSpace(Position position) {
        return position.getX() >= 0 && position.getY() >= 0
                && position.getX() < width && position.getY() < height;
    }
}
