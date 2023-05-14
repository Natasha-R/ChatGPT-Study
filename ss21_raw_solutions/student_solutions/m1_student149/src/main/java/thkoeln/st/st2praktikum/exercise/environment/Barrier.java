package thkoeln.st.st2praktikum.exercise.environment;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.environment.transition.BlockedTransition;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;
import thkoeln.st.st2praktikum.exercise.parser.BarrierParser;

import java.util.ArrayList;
import java.util.List;

public class Barrier {

    private static final BarrierParser parser = new BarrierParser();

    private final Position start;
    private final Position end;

    private final List<BlockedTransition> blockedTransitions = new ArrayList<>();

    private Barrier(Position start, Position end) {
        this.start = start;
        this.end = end;

        initBlockedTransitions();
    }

    public static Barrier of(String string) throws ParseException {
        return parser.parse(string);
    }

    public static Barrier of(Position start, Position end) {
        return new Barrier(start, end);
    }

    public List<BlockedTransition> getBlockedTransitions() {
        return blockedTransitions;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start + "-" + end;
    }

    private void initBlockedTransitions() {
        int deltaX = end.getX() - start.getX();
        int deltaY = end.getY() - start.getY();

        int x = end.getX() - deltaX;
        int y = end.getY() - deltaY;

        if (deltaX != 0) {
            int minX = Integer.min(x, end.getX());
            int maxX = Integer.max(x, end.getX());

            for (int i = minX; i < maxX; i++) {
                blockedTransitions.add(new BlockedTransition(Position.of(i, y), Position.of(i, y - 1)));
            }
        }
        if (deltaY != 0) {
            int minY = Integer.min(y, end.getY());
            int maxY = Integer.max(y, end.getY());

            for (int i = minY; i <= maxY; i++) {
                blockedTransitions.add(new BlockedTransition(Position.of(x, i), Position.of(x - 1, i)));
            }
        }
    }
}
