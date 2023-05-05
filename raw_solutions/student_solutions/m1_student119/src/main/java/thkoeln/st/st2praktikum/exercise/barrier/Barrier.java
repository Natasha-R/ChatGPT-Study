package thkoeln.st.st2praktikum.exercise.barrier;

import java.awt.*;

public class Barrier {
    private final Point start;
    private final Point end;
    private final BarrierType type;

    public Barrier(Point start, Point end, BarrierType type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public int getLimit() {
        switch (type) {
            case horizontal:
                return start.y;
            case vertical:
                return start.x;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public BarrierType getType() {
        return type;
    }
}

