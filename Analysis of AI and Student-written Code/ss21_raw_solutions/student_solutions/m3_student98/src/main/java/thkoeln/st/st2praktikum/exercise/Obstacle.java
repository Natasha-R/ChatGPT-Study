package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.InvalidInputException;
import thkoeln.st.st2praktikum.exercise.core.Moveable;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Obstacle {

    @Id
    private UUID id;
    @OneToOne (cascade = CascadeType.ALL)
    private Vector2D start;
    @OneToOne (cascade = CascadeType.ALL)
    private Vector2D end;


    public Obstacle(Vector2D pos1, Vector2D pos2) {
        if (!validateObstacleOrientation(pos1, pos2))
            throw new InvalidInputException("Invalid obstacle orientation. Only horizontal or vertical orientations are allowed.");

        Pair<Vector2D, Vector2D> vectorPairInOrder = orderObstaclePositionCoordinates(pos1, pos2);
        this.id = UUID.randomUUID();
        this.start = vectorPairInOrder.getLeft();
        this.end = vectorPairInOrder.getRight();
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) throws InvalidInputException {
        if (!validateObstacleStringFormatting(obstacleString))
            throw new InvalidInputException("String formatting is invalid");

        Pair<Vector2D, Vector2D> vectorPair = parseCoordinatesToPairFromString(obstacleString);
        if (!validateObstacleOrientation(vectorPair.getLeft(), vectorPair.getRight()))
            throw new InvalidInputException("Invalid obstacle orientation. Only horizontal or vertical orientations are allowed.");

        Pair<Vector2D, Vector2D> vectorPairInOrder = orderObstaclePositionCoordinates(vectorPair.getLeft(), vectorPair.getRight());
        this.id = UUID.randomUUID();
        this.start = vectorPairInOrder.getLeft();
        this.end = vectorPairInOrder.getRight();
    }

    public UUID getId() {
        return this.id;
    }

    public Boolean isPassable(Moveable movement) {
        switch (movement.getOrientation()) {
            case NO: return validateNorthMovement(movement);
            case EA: return validateEastMovement(movement);
            case SO: return validateSouthMovement(movement);
            case WE: return validateWestMovement(movement);
            default: return false;
        }
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    private Pair parseCoordinatesToPairFromString(String obstacleString) {
        String[] splitToVectors = obstacleString.split("-");
        Vector2D start = new Vector2D(splitToVectors[0]);
        Vector2D end = new Vector2D(splitToVectors[1]);
        return new Pair(start, end);
    }

    private Boolean validateObstacleStringFormatting(String obstacleString) {
        return obstacleString.matches("\\(\\d+,\\d+\\)-\\(\\d+,\\d+\\)");
    }

    private Boolean validateObstacleOrientation(Vector2D start, Vector2D end) {
        return validateHorizontalOrientation(start, end) || validateVerticalOrientation(start, end);
    }

    private Boolean validateHorizontalOrientation(Vector2D start, Vector2D end) {
        return start.getY().equals(end.getY());
    }

    private Boolean validateVerticalOrientation(Vector2D start, Vector2D end) {
        return start.getX().equals(end.getX());
    }

    private Pair<Vector2D, Vector2D> orderObstaclePositionCoordinates(Vector2D start, Vector2D end) {
        Double distanceStart = calculateCoordinateDistanceToAnchor(start);
        Double distanceEnd = calculateCoordinateDistanceToAnchor(end);
        if (distanceStart <= distanceEnd)
            return new Pair(start, end);
        else
            return new Pair(end, start);
    }

    private Double calculateCoordinateDistanceToAnchor(Vector2D vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
    }

    private Boolean validateNorthMovement(Moveable movement) {
        if (!isHorizontalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getY() < start.getY() &&
                movement.nextRequestedPositionFromCurrentPosition().getY() >= start.getY())
            return false;
        return true;
    }

    private Boolean validateEastMovement(Moveable movement) {
        if (!isVerticalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getX() < start.getX() &&
                movement.nextRequestedPositionFromCurrentPosition().getX() >= start.getX())
            return false;
        return true;
    }

    private Boolean validateSouthMovement(Moveable movement) {
        if (!isHorizontalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getY() >= start.getY() &&
                movement.nextRequestedPositionFromCurrentPosition().getY() < start.getY())
            return false;
        return true;
    }

    private Boolean validateWestMovement(Moveable movement) {
        if (!isVerticalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getX() >= start.getX() &&
                movement.nextRequestedPositionFromCurrentPosition().getX() < start.getX())
            return false;
        return true;
    }

    private Boolean isHorizontalWallInLine(Moveable movement) {
        int currentXPos, fromXPos, fromYPos, toXPos, toYPos;
        currentXPos = movement.getCurrentPosition().getX();
        fromXPos = start.getX();
        fromYPos = start.getY();
        toXPos = end.getX();
        toYPos = end.getY();

        if (fromYPos != toYPos)
            return false;
        if (fromXPos <= currentXPos && toXPos > currentXPos || toXPos <= currentXPos && fromXPos > currentXPos)
            return true;
        return false;
    }

    private Boolean isVerticalWallInLine(Moveable movement) {
        int currentYPos, fromXPos, fromYPos, toXPos, toYPos;
        currentYPos = movement.getCurrentPosition().getY();
        fromXPos = start.getX();
        fromYPos = start.getY();
        toXPos = end.getX();
        toYPos = end.getY();

        if (fromXPos != toXPos)
            return false;
        if (fromYPos <= currentYPos && toYPos > currentYPos || toYPos <= currentYPos && fromYPos > currentYPos)
            return true;
        return false;
    }
}