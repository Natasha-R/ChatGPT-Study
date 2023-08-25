package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Pair;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Access(AccessType.FIELD)
public class Obstacle {

    @Embedded
    private Vector2D start;
    @Embedded
    private Vector2D end;


    public Obstacle(Vector2D start, Vector2D end) throws InvalidInputException {
        if (!validateObstacleOrientation(start, end)) {
            throw new InvalidInputException("Invalid obstacle orientation. Only horizontal or vertical orientations are allowed.");
        }
        Pair<Vector2D, Vector2D> vectorPairInOrder = orderObstaclePositionCoordinates(start, end);
        this.start = vectorPairInOrder.getLeft();
        this.end = vectorPairInOrder.getRight();
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) throws InvalidInputException {
        if (!validateObstacleStringFormatting(obstacleString)) {
            throw new InvalidInputException("String formatting is invalid");
        }
        Pair<Vector2D, Vector2D> vectorPair = parseCoordinatesToPairFromString(obstacleString);
        if (!validateObstacleOrientation(vectorPair.getLeft(), vectorPair.getRight())) {
            throw new InvalidInputException("Invalid obstacle orientation. Only horizontal or vertical orientations are allowed.");
        }
        return new Obstacle(vectorPair.getLeft(), vectorPair.getRight());
    }
    
    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    private static Pair parseCoordinatesToPairFromString(String obstacleString) {
        String[] splitToVectors = obstacleString.split("-");
        Vector2D start = Vector2D.fromString(splitToVectors[0]);
        Vector2D end = Vector2D.fromString(splitToVectors[1]);
        return new Pair(start, end);
    }

    private static Boolean validateObstacleStringFormatting(String obstacleString) {
        return obstacleString.matches("\\(\\d+,\\d+\\)-\\(\\d+,\\d+\\)");
    }

    private static Boolean validateObstacleOrientation(Vector2D start, Vector2D end) {
        return validateHorizontalOrientation(start, end) || validateVerticalOrientation(start, end);
    }

    private static Boolean validateHorizontalOrientation(Vector2D start, Vector2D end) {
        return start.getY().equals(end.getY());
    }

    private static Boolean validateVerticalOrientation(Vector2D start, Vector2D end) {
        return start.getX().equals(end.getX());
    }

    private static Pair<Vector2D, Vector2D> orderObstaclePositionCoordinates(Vector2D start, Vector2D end) {
        Double distanceStart = calculateCoordinateDistanceToAnchor(start);
        Double distanceEnd = calculateCoordinateDistanceToAnchor(end);
        if (distanceStart <= distanceEnd)
            return new Pair(start, end);
        else
            return new Pair(end, start);
    }

    private static Double calculateCoordinateDistanceToAnchor(Vector2D vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
    }
}
