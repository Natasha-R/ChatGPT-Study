package thkoeln.st.st2praktikum.exercise.obstacle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.inner.OrientationStand;
import thkoeln.st.st2praktikum.exercise.inner.Position;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

@Getter
@Setter
@AllArgsConstructor
public class Obstacle implements ObstaclePassable{
    private Position startPosition;
    private Position endPosition;

    public Obstacle(String location, Walkable space){
        String[] positionDivider=location.split("-");
        this.startPosition= new Position(space, positionDivider[0]);
        this.endPosition=new Position(space,positionDivider[1]);
    }
    @Override
    public Position getUnaccessPosition(Position actualPosition, Position targetPosition) {
        OrientationStand orientationDirection = calculateDirection(actualPosition, targetPosition);
        switch (orientationDirection) {
            case no:  return calculatePositionNorth(actualPosition, targetPosition);
            case so:  return calculatePositionSouth(actualPosition, targetPosition);
            case we:   return calculatePositionWest(actualPosition, targetPosition);
            case ea:   return calculatePositionEast(actualPosition, targetPosition);
            default: throw new IllegalArgumentException("Illegal orientationStand: "+ orientationDirection);
        }
    }

    private static OrientationStand calculateDirection(Position currentPosition, Position targetPosition) {
        int horizontalSteps = targetPosition.getX() - currentPosition.getX();
        int verticalSteps = targetPosition.getY() - currentPosition.getY();
        if (verticalSteps > 0)          return OrientationStand.no;
        else if (verticalSteps < 0)     return OrientationStand.so;
        else if (horizontalSteps > 0)   return OrientationStand.ea;
        else if (horizontalSteps < 0)   return OrientationStand.we;
        else                            return OrientationStand.illegal;
    }

    private Position calculatePositionNorth(Position actualPosition, Position targetPosition) {
        if (getYStart().equals(getYEnd())
                && getYStart() > actualPosition.getY()
                && getXStart() <= actualPosition.getX()
                && getXEnd() > actualPosition.getX()
                && getYStart() <= targetPosition.getY())
            return new Position(targetPosition.getSpace(), targetPosition.getX(), getYStart()-1);
        return targetPosition;
    }

    private Position calculatePositionSouth(Position actualPosition, Position targetPosition) {
        if (getYStart().equals(getYEnd())
                && getYStart() <= actualPosition.getY()
                && getXStart() <= actualPosition.getX()
                && getXEnd() > actualPosition.getX()
                && getYStart() > targetPosition.getY())
            return new Position(targetPosition.getSpace(), targetPosition.getX(), getYStart());
        return targetPosition;
    }

    private Position calculatePositionEast(Position actualPosition, Position targetPosition) {
        if (getXStart().equals(getXEnd())
                && getXStart() > actualPosition.getX()
                && getYStart() <= actualPosition.getY()
                && getYEnd() > actualPosition.getY()
                && getXStart() <= targetPosition.getX())
            return new Position(targetPosition.getSpace(), getXStart()-1, targetPosition.getY());
        return targetPosition;
    }

    private Position calculatePositionWest(Position actualPosition, Position targetPosition) {
        if (getXStart().equals(getXEnd())
                && getXStart() <= actualPosition.getX()
                && getYStart() <= actualPosition.getY()
                && getYEnd() > actualPosition.getY()
                && getXStart() > targetPosition.getX())
            return new Position(targetPosition.getSpace(), getXStart(), targetPosition.getY());
        return targetPosition;
    }

    private Integer getXStart() { return this.startPosition.getX(); }

    private Integer getYStart() {
        return this.startPosition.getY();
    }

    private Integer getXEnd() { return this.endPosition.getX(); }

    private Integer getYEnd() {
        return this.endPosition.getY();
    }

}