package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Barrier {

    private final BarrierType barrierType;
    private Point start;
    private Point end;

    public Barrier(Point pos1, Point pos2) {
        sortPoints(pos1, pos2);
        this.barrierType = getBarrierTypeFromPoints();
        ensureNotDiagonal();
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        final Pattern pattern = Pattern.compile("^(\\((\\d+),(\\d+)\\))-(\\((\\d+),(\\d+)\\))$");
        final Matcher matcher = pattern.matcher(barrierString);

        if(matcher.find()) {
            final String start = matcher.group(1);
            final String end = matcher.group(4);

            final Point pointA = new Point(start);
            final Point pointB = new Point(end);

            sortPoints(pointA, pointB);
            this.barrierType = getBarrierTypeFromPoints();
            ensureNotDiagonal();
        } else {
            throw new IllegalArgumentException();
        }

    }

    private void ensureNotDiagonal() {
        if(!(this.start.getX().equals(this.end.getX()) || this.start.getY().equals(this.end.getY())))
            throw new IllegalArgumentException("diagonal barriers are not supported!");
    }

    private void sortPoints(Point pointA, Point pointB) {
        if(pointA.getX() <= pointB.getX() && pointA.getY() <= pointB.getY()) {
            this.start = pointA;
            this.end = pointB;
        } else {
            this.start = pointB;
            this.end = pointA;
        }
    }

    private BarrierType getBarrierTypeFromPoints() {
        return this.start.getX().equals(this.end.getX()) ? BarrierType.vertical : BarrierType.horizontal;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public BarrierType getBarrierType() {
        return barrierType;
    }
}
