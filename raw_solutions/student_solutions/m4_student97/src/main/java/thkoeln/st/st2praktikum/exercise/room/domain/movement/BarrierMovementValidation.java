package thkoeln.st.st2praktikum.exercise.room.domain.movement;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.Moveable;
import thkoeln.st.st2praktikum.exercise.domaintypes.Passable;

public class BarrierMovementValidation implements Passable {

    private final Point start;
    private final Point end;


    public BarrierMovementValidation(Barrier barrier) {
        this.start = barrier.getStart();
        this.end = barrier.getEnd();
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
