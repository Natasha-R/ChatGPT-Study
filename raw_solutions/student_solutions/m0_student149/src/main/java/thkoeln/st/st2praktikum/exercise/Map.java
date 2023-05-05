package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private int width;
    private int height;

    private List<Transition> blockedTransitions = new ArrayList<>();

    public Map(int width, int height, List<Barrier> barriers) {
        this.width = width;
        this.height = height;

        initBlockedTransition(barriers);
    }

    public boolean isMoveValid(Position current, Position next) {
        return isPositionOnMap(next) && !blockedTransitions.contains(new Transition(current, next));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Transition> getBlockedTransitions() {
        return blockedTransitions;
    }

    private boolean isPositionOnMap(Position position) {
        return position.getX() >= 0 && position.getY() >= 0
                && position.getX() < width && position.getY() < height;
    }

    private void initBlockedTransition(List<Barrier> barriers) {
        barriers.forEach(barrier -> blockedTransitions.addAll(barrier.getBlockedTransitions()));
    }
}
