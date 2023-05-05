package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Blocker;

public class Barrier implements Blocker {

    private Coordinate start;
    private Coordinate end;


    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        String[] splitString = barrierString.split("-");
        Coordinate pos1 = new Coordinate(splitString[0]);
        Coordinate pos2 = new Coordinate(splitString[1]);
        if (isHorizontal(pos1, pos2) || isVertical(pos1, pos2)) {
            if (pos1.getX() + pos1.getY() < pos2.getX() + pos2.getY()) {
                this.start = pos1;
                this.end = pos2;
            }else {
                this.start = pos2;
                this.end = pos1;
            }
        } else throw new IllegalArgumentException();
    }

    public Barrier(Coordinate pos1, Coordinate pos2) {
        if (isHorizontal(pos1, pos2) || isVertical(pos1, pos2)) {
            if (pos1.getX() + pos1.getY() < pos2.getX() + pos2.getY()) {
                this.start = pos1;
                this.end = pos2;
            }else {
                this.start = pos2;
                this.end = pos1;
            }
        } else throw new IllegalArgumentException();
    }

    private boolean isHorizontal(Coordinate pos1, Coordinate pos2) {
        return !pos1.getX().equals(pos2.getX()) && pos1.getY().equals(pos2.getY());
    }

    private boolean isVertical(Coordinate pos1, Coordinate pos2) {
        return pos1.getX().equals(pos2.getX()) && !pos1.getY().equals(pos2.getY());
    }

    @Override
    public boolean isBlocked(Coordinate current, Coordinate future) {
        if (movesHorizontally(current, future)) {
            return willCrossHorizontalBarrier(current, future);
        } else {
            return willCrossVerticalBarrier(current, future);
        }
    }

    private boolean movesHorizontally(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        return currentCoordinate.getY() - futureCoordinate.getY() == 0;
    }

    private boolean willCrossHorizontalBarrier(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        if (start.getY() <= currentCoordinate.getY()
                && end.getY() > currentCoordinate.getY()) {
            if (start.getX() > currentCoordinate.getX()
                    && start.getX() <= futureCoordinate.getX())
                return true;
            if (start.getX() <= currentCoordinate.getX()
                    && start.getX() > futureCoordinate.getX())
                return true;
        }
        return false;
    }

    private boolean willCrossVerticalBarrier(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        if (start.getX() <= currentCoordinate.getX()
                && end.getX() > currentCoordinate.getX()) {
            if (start.getY() > currentCoordinate.getY()
                    && start.getY() <= futureCoordinate.getY())
                return true;
            if (start.getY() <= currentCoordinate.getY()
                    && start.getY() > futureCoordinate.getY())
                return true;
        }
        return false;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
