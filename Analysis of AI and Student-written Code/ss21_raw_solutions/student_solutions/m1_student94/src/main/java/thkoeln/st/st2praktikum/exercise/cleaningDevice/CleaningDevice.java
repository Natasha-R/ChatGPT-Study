package thkoeln.st.st2praktikum.exercise.cleaningDevice;

import thkoeln.st.st2praktikum.exercise.inner.CleaningDeviceMovement;
import thkoeln.st.st2praktikum.exercise.inner.OrientationStand;
import thkoeln.st.st2praktikum.exercise.inner.Position;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

import java.util.UUID;

public class CleaningDevice implements Occupied,Instructable{
    private final UUID cleaningDeviceId;
    private final  String name;
    private Position devicePosition;

    public CleaningDevice( String name){
        this.name=name;
        this.cleaningDeviceId=UUID.randomUUID();
    }

    @Override
    public Boolean analysedCommand(CleaningDeviceMovement  deviceMovement) {
        switch ( deviceMovement.getMovetype()) {
            case INITIALISED: return InitialCommand( deviceMovement);
            case MOVEMENT:  return moveCommand( deviceMovement);
            case TRANSPORT: return TransportCommand(deviceMovement);
            default: throw new IllegalArgumentException("Command not available: "+ deviceMovement);
        }
    }

    private Boolean InitialCommand(CleaningDeviceMovement deviceMovement) {
        if (this.devicePosition != null)
            return false;
        if ( deviceMovement.getSpace().isInitialPositionFree()) {
            this.devicePosition = deviceMovement.getSpace().getInitialPosition();
            actualSpace().addOccupied(this);
            return true;
        }
        return false;
    }
    private Boolean TransportCommand(CleaningDeviceMovement  deviceCommand) {
        if ( actualSpace().isTransportPositionValid(this.devicePosition, deviceCommand.getSpace()) ) {
            Position newPosition = this.actualSpace().getTransportDestinationPosition(this.devicePosition, deviceCommand.getSpace());
            if ( !newPosition.getSpace().isSpaceOccupied(newPosition) ) {
                actualSpace().removeOccupied(this);
                this.devicePosition = newPosition;
                actualSpace().addOccupied(this);
                return true;
            }
        }
        return false;
    }

    private Boolean moveCommand(CleaningDeviceMovement deviceMovement) {

        if (this.devicePosition == null)
            return false;
        Position targetPosition = calculateTargetPosition(deviceMovement);
        Position lastPosition = actualSpace().getLastPosition(targetPosition);
        Position obstaclePosition= actualSpace().getObstaclePosition(this.devicePosition, targetPosition);
        Position occupiedPosition = actualSpace().getOccupiedPosition(this.devicePosition, targetPosition);
        if (deviceMovement.getOrientationStand() == OrientationStand.no || deviceMovement.getOrientationStand() == OrientationStand.ea)
            this.devicePosition = lastPosition.min(obstaclePosition).min(occupiedPosition);
        else if (deviceMovement.getOrientationStand() == OrientationStand.so || deviceMovement.getOrientationStand() == OrientationStand.we)
            this.devicePosition = lastPosition.max(occupiedPosition).max(occupiedPosition);
        return true;
    }

    private Position calculateTargetPosition(CleaningDeviceMovement deviceMovement) {
        int xCoordinate = this.devicePosition.getX();
        int yCoordinate = this.devicePosition.getY();
        switch (deviceMovement.getOrientationStand()) {
            case no: yCoordinate+=deviceMovement.getStepToMove(); break;
            case so: yCoordinate-=deviceMovement.getStepToMove(); break;
            case we: xCoordinate-=deviceMovement.getStepToMove(); break;
            case ea: xCoordinate+=deviceMovement.getStepToMove(); break;
            default: throw new IllegalArgumentException("Illegal NextDirection: "+ deviceMovement.getOrientationStand());
        }
        return new Position(this.devicePosition.getSpace(),xCoordinate, yCoordinate);
    }

    @Override
    public Position getPosition() {
        return this.devicePosition;
    }

    @Override
    public Walkable getSpace() {
        return this.devicePosition.getSpace();
    }

    @Override
    public Boolean occupiedSpace(Position position) {
        return position.equals(this.devicePosition);
    }

    @Override
    public UUID getId() {
        return this.cleaningDeviceId;
    }

    @Override
    public Position getUnaccessPosition(Position actualPosition, Position objectivePosition) {
        OrientationStand blockedDirection = calculateDirection(actualPosition, objectivePosition);
        switch (blockedDirection) {
            case no:  return calculateBlockPositionNorth(actualPosition, objectivePosition);
            case so:  return calculateBlockPositionSouth(actualPosition, objectivePosition);
            case we:   return calcBlockPositionWest(actualPosition, objectivePosition);
            case ea:   return calculateBlockPositionEast(actualPosition, objectivePosition);
            default: throw new IllegalArgumentException("Illegal BlockDirection: "+ blockedDirection);
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
    private Position calculateBlockPositionNorth(Position currentPosition, Position targetPosition) {
        if (this.getPosition().getX()==(currentPosition.getX())
                && this.getPosition().getY() > currentPosition.getY()
                && this.getPosition().getY() <= targetPosition.getY() )
            targetPosition = new Position(this.getSpace(), this.getPosition().getX(), this.getPosition().getY()-1);
        return targetPosition;
    }


    private Position calculateBlockPositionSouth(Position currentPosition, Position targetPosition) {
        if (this.getPosition().getX()==(currentPosition.getX())
                && this.getPosition().getY() < currentPosition.getY()
                && this.getPosition().getY() >= targetPosition.getY() )
            targetPosition = new Position(this.getSpace(), this.getPosition().getX(), this.getPosition().getY()+1);
        return targetPosition;
    }

    private Position calculateBlockPositionEast(Position currentPosition, Position targetPosition) {
        if (this.getPosition().getY()==(currentPosition.getY())
                && this.getPosition().getX() > currentPosition.getX()
                && this.getPosition().getX() <= targetPosition.getX() )
            targetPosition = new Position(this.getSpace(), this.getPosition().getX()-1, this.getPosition().getY());
        return targetPosition;
    }

    private Position calcBlockPositionWest(Position currentPosition, Position targetPosition) {
        if (this.getPosition().getY()==(currentPosition.getY())
                && this.getPosition().getX() < currentPosition.getX()
                && this.getPosition().getX() >= targetPosition.getX() )
            targetPosition = new Position(this.getSpace(), this.getPosition().getX()+1, this.getPosition().getY());
        return targetPosition;
    }


    private Walkable actualSpace(){
        return this.devicePosition.getSpace();
    }

}

