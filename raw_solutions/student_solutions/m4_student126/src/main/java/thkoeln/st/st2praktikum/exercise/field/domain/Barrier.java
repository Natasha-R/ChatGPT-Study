package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Blockable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@NoArgsConstructor
@Getter
public class Barrier implements Blockable {

    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;


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
    
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        String[] splitString = barrierString.split("-");
        Coordinate pos1 = Coordinate.fromString(splitString[0]);
        Coordinate pos2 = Coordinate.fromString(splitString[1]);
        if (isHorizontal(pos1, pos2) || isVertical(pos1, pos2)) {
            if (pos1.getX() + pos1.getY() < pos2.getX() + pos2.getY()) {
                return new Barrier(pos1, pos2);
            }else {
                return new Barrier(pos2, pos1);
            }
        } else throw new IllegalArgumentException();
    }

    private static boolean isHorizontal(Coordinate pos1, Coordinate pos2) {
        return !pos1.getX().equals(pos2.getX()) && pos1.getY().equals(pos2.getY());
    }

    private static boolean isVertical(Coordinate pos1, Coordinate pos2) {
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
}
