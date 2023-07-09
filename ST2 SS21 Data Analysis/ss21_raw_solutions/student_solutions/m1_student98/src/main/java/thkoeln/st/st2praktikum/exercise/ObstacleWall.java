package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class ObstacleWall implements Passable {

    private final UUID id;
    private final XYPositionable fromPosition;
    private final XYPositionable toPosition;


    public ObstacleWall(XYPositionable fromPosition, XYPositionable toPosition) {
        this.id = UUID.randomUUID();
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Boolean isPassable(XYMovable movement) {
        switch (movement.getOrientation()) {
            case NO: return validateNorthMovement(movement);
            case EA: return validateEastMovement(movement);
            case SO: return validateSouthMovement(movement);
            case WE: return validateWestMovement(movement);
            default: return false;
        }
    }

    private Boolean validateNorthMovement(XYMovable movement) {
        if (!isHorizontalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getYPos() < fromPosition.getYPos() &&
                movement.nextRequestedPositionFromCurrentPosition().getYPos() >= fromPosition.getYPos())
            return false;
        return true;
    }

    private Boolean validateEastMovement(XYMovable movement) {
        if (!isVerticalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getXPos() < fromPosition.getXPos() &&
                movement.nextRequestedPositionFromCurrentPosition().getXPos() >= fromPosition.getXPos())
            return false;
        return true;
    }

    private Boolean validateSouthMovement(XYMovable movement) {
        if (!isHorizontalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getYPos() >= fromPosition.getYPos() &&
                movement.nextRequestedPositionFromCurrentPosition().getYPos() < fromPosition.getYPos())
            return false;
        return true;
    }

    private Boolean validateWestMovement(XYMovable movement) {
        if (!isVerticalWallInLine(movement))
            return true;
        if (movement.getCurrentPosition().getXPos() >= fromPosition.getXPos() &&
                movement.nextRequestedPositionFromCurrentPosition().getXPos() < fromPosition.getXPos())
            return false;
        return true;
    }

    private Boolean isHorizontalWallInLine(XYMovable movement) {
        int currentXPos, fromXPos, fromYPos, toXPos, toYPos;
        currentXPos = movement.getCurrentPosition().getXPos();
        fromXPos = fromPosition.getXPos();
        fromYPos = fromPosition.getYPos();
        toXPos = toPosition.getXPos();
        toYPos = toPosition.getYPos();

        if (fromYPos != toYPos)
            return false;
        if (fromXPos <= currentXPos && toXPos > currentXPos || toXPos <= currentXPos && fromXPos > currentXPos)
            return true;
        return false;
    }

    private Boolean isVerticalWallInLine(XYMovable movement) {
        int currentYPos, fromXPos, fromYPos, toXPos, toYPos;
        currentYPos = movement.getCurrentPosition().getYPos();
        fromXPos = fromPosition.getXPos();
        fromYPos = fromPosition.getYPos();
        toXPos = toPosition.getXPos();
        toYPos = toPosition.getYPos();

        if (fromXPos != toXPos)
            return false;
        if (fromYPos <= currentYPos && toYPos > currentYPos || toYPos <= currentYPos && fromYPos > currentYPos)
            return true;
        return false;
    }

    @Override
    public String debugPassableObjectToString() {
        return "\tObstacleWallID: "+id+"\n"+
                "\tfromPosition:\n"+
                ((fromPosition == null) ? "\t\tNULL" : "\t\t"+fromPosition.debugPositionToString()+"\n")+
                "\ttoPosition:\n"+
                ((toPosition == null) ? "\t\tNULL" : "\t\t"+toPosition.debugPositionToString()+"\n");
    }
}
