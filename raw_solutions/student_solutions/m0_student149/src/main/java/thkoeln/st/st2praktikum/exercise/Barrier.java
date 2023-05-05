package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Barrier {

    private static final String PATTERN = "(\\((?:\\d|\\,)+\\))\\-(\\((?:\\d|\\,)+\\))";

    private Position start;
    private Position end;

    private List<Transition> blockedTransitions = new ArrayList<>();

    public Barrier(Position start, Position end) {
        this.start = start;
        this.end = end;

        initBlockedTransitions();
    }

    public Barrier(String barrier) {
        parseBarrier(barrier);
        initBlockedTransitions();
    }

    public List<Transition> getBlockedTransitions() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barrier barrier = (Barrier) o;
        return Objects.equals(start, barrier.start) && Objects.equals(end, barrier.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    private void initBlockedTransitions() {
        int deltaX = end.getX() - start.getX();
        int deltaY = end.getY() - start.getY();

        int x = end.getX() - deltaX;
        int y = end.getY() - deltaY;

        if (deltaX != 0) {
            int minX = Integer.min(x, end.getX());
            int maxX = Integer.max(x, end.getX());

            for (int i = minX; i <= maxX; i++) {
                blockedTransitions.add(new Transition(new Position(i, y), new Position(i, y - 1)));
            }
        }
        if (deltaY != 0) {
            int minY = Integer.min(y, end.getY());
            int maxY = Integer.max(y, end.getY());

            for (int i = minY; i <= maxY; i++) {
                blockedTransitions.add(new Transition(new Position(x, i), new Position(x - 1, i)));
            }
        }
    }

    private void parseBarrier(String barrier) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(barrier);

        if (matcher.find()) {
            start = new Position(matcher.group(1));
            end = new Position(matcher.group(2));
        }
    }
}
