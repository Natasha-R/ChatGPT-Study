package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Pair;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Access(AccessType.FIELD)
public class Barrier {

    @Embedded
    private Point start;
    @Embedded
    private Point end;


    public Barrier(Point start, Point end) throws InvalidInputException {
        if (!validateBarrierOrientation(start, end)) {
            throw new InvalidInputException("Invalid barrier orientation. Only horizontal or vertical orientations are allowed.");
        }
        Pair<Point, Point> vectorPairInOrder = orderBarrierPositionCoordinates(start, end);
        this.start = vectorPairInOrder.getLeft();
        this.end = vectorPairInOrder.getRight();
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) throws InvalidInputException {
        if (!validateBarrierStringFormatting(barrierString)) {
            throw new InvalidInputException("String formatting is invalid");
        }
        Pair<Point, Point> vectorPair = parseCoordinatesToPairFromString(barrierString);
        if (!validateBarrierOrientation(vectorPair.getLeft(), vectorPair.getRight())) {
            throw new InvalidInputException("Invalid barrier orientation. Only horizontal or vertical orientations are allowed.");
        }
        return new Barrier(vectorPair.getLeft(), vectorPair.getRight());
    }
    
    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    private static Pair parseCoordinatesToPairFromString(String barrierString) {
        String[] splitToVectors = barrierString.split("-");
        Point start = Point.fromString(splitToVectors[0]);
        Point end = Point.fromString(splitToVectors[1]);
        return new Pair(start, end);
    }

    private static Boolean validateBarrierStringFormatting(String barrierString) {
        return barrierString.matches("\\(\\d+,\\d+\\)-\\(\\d+,\\d+\\)");
    }

    private static Boolean validateBarrierOrientation(Point start, Point end) {
        return validateHorizontalOrientation(start, end) || validateVerticalOrientation(start, end);
    }

    private static Boolean validateHorizontalOrientation(Point start, Point end) {
        return start.getY().equals(end.getY());
    }

    private static Boolean validateVerticalOrientation(Point start, Point end) {
        return start.getX().equals(end.getX());
    }

    private static Pair<Point, Point> orderBarrierPositionCoordinates(Point start, Point end) {
        Double distanceStart = calculateCoordinateDistanceToAnchor(start);
        Double distanceEnd = calculateCoordinateDistanceToAnchor(end);
        if (distanceStart <= distanceEnd)
            return new Pair(start, end);
        else
            return new Pair(end, start);
    }

    private static Double calculateCoordinateDistanceToAnchor(Point vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
    }
}
