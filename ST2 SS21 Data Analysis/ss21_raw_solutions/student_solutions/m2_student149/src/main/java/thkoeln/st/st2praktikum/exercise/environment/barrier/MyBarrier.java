package thkoeln.st.st2praktikum.exercise.environment.barrier;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.environment.transition.BlockedTransition;
import thkoeln.st.st2praktikum.exercise.exception.InvalidBarrierException;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;
import thkoeln.st.st2praktikum.exercise.parser.BarrierParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyBarrier {

    private static final BarrierParser parser = new BarrierParser();

    private Position start;
    private Position end;

    private BarrierOrientation orientation;

    private final List<BlockedTransition> blockedTransitions = new ArrayList<>();

    private MyBarrier(Position barrierEdge1, Position barrierEdge2) throws InvalidBarrierException {
        orderBarrier(barrierEdge1, barrierEdge2);
        initOrientation();
        initBlockedTransitions();
    }

    public static MyBarrier of(String string) throws ParseException {
        return parser.parse(string);
    }

    public static MyBarrier of(Position start, Position end) {
        return new MyBarrier(start, end);
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

    private void orderBarrier(Position barrierEdge1, Position barrierEdge2) throws InvalidBarrierException {
        if (Objects.equals(barrierEdge1, barrierEdge2))
            throw new InvalidBarrierException("Start position should differ from end position", this);

        int deltaX = barrierEdge1.getX() - barrierEdge2.getX();
        int deltaY = barrierEdge1.getY() - barrierEdge2.getY();

        if (deltaX < 0 || deltaY < 0) {
            this.start = barrierEdge1;
            this.end = barrierEdge2;
        } else {
            this.start = barrierEdge2;
            this.end = barrierEdge1;
        }
    }

    private void initOrientation() throws InvalidBarrierException {
        int deltaX = end.getX() - start.getX();
        int deltaY = end.getY() - start.getY();

        if (deltaX != 0 && deltaY != 0) throw new InvalidBarrierException("Diagonal barrier is not allowed", this);

        if (deltaX != 0) this.orientation = BarrierOrientation.HORIZONTAL;
        else if (deltaY != 0) this.orientation = BarrierOrientation.VERTICAL;
    }

    private void initBlockedTransitions() {
        switch (orientation) {
            case VERTICAL:
                int x = start.getX();
                for (int i = start.getY(); i <= end.getY(); i++) {
                    blockedTransitions.add(new BlockedTransition(Position.of(x, i), Position.of(x - 1, i)));
                }
                break;
            case HORIZONTAL:
                int y = start.getY();
                for (int i = start.getX(); i < end.getX(); i++) {
                    blockedTransitions.add(new BlockedTransition(Position.of(i, y), Position.of(i, y - 1)));
                }
                break;
        }
    }
}
