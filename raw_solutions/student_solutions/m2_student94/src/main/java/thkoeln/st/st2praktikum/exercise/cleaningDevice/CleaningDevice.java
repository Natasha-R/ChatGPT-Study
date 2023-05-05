package thkoeln.st.st2praktikum.exercise.cleaningDevice;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.assembler.Assembler;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

import java.util.UUID;

public class CleaningDevice implements Occupied,Instructable{

    private final UUID cleaningDeviceId;
    private final  String name;
    private Coordinate devicePosition;
    private UUID spaceId;
    public  Walkable deviceSpace;

    public CleaningDevice(String name){
        this.name=name;
        this.cleaningDeviceId=UUID.randomUUID();
    }

    @Override
    public Boolean analysedCommand(Order deviceMovement) {

        if (deviceMovement.getOrderType().isOrderType()) return moveCommand(deviceMovement);
        else if (deviceMovement.getOrderType()==OrderType.ENTER )return InitialCommand( deviceMovement);
        else if (deviceMovement.getOrderType()==OrderType.TRANSPORT)return TransportCommand(deviceMovement);
        else return false;

    }
    private Boolean InitialCommand(Order deviceMovement) {
        if (this.devicePosition != null)
            return false;
        deviceSpace=(Walkable) Assembler.getSpaceList().get(deviceMovement.getGridId());
        spaceId=deviceSpace.getId();
        if (((Walkable) Assembler.getSpaceList().get(deviceMovement.getGridId())).isInitialPositionFree()) {
            this.devicePosition = ((Walkable) Assembler.getSpaceList().get(deviceMovement.getGridId())).getInitialPosition();
            deviceSpace.addOccupied(this);
            return true;
        }
        return false;
    }

    private Boolean TransportCommand(Order deviceCommand) {

        if ( ((Walkable)Assembler.getSpaceList().get(spaceId)).isTransportPositionValid(this.devicePosition, (Walkable) Assembler.getSpaceList().get(deviceCommand.getGridId())) ) {
            Coordinate newPosition = ((Walkable)Assembler.getSpaceList().get(spaceId)).getTransportDestinationPosition(this.devicePosition, (Walkable) Assembler.getSpaceList().get(deviceCommand.getGridId()));
            if (!((Walkable) Assembler.getSpaceList().get(deviceCommand.getGridId())).isSpaceOccupied(newPosition) ) {
                ((Walkable)Assembler.getSpaceList().get(spaceId)).removeOccupied(this);
                this.devicePosition = newPosition;
                ((Walkable)Assembler.getSpaceList().get(spaceId)).addOccupied(this);
                spaceId=deviceCommand.getGridId();
                deviceSpace=(Walkable) Assembler.getSpaceList().get(deviceCommand.getGridId());
                return true;
            }
        }
        return false;
    }

    private Boolean moveCommand(Order deviceMovement) {
        if (this.devicePosition == null)
            return false;

        Coordinate targetPosition = calculateTargetPosition(deviceMovement);
        Coordinate lastPosition =deviceSpace.getLastPosition(targetPosition);
        Coordinate obstaclePosition= deviceSpace.getObstaclePosition(this.devicePosition, targetPosition);
        Coordinate occupiedPosition = deviceSpace.getOccupiedPosition(this.devicePosition, targetPosition);

        if (deviceMovement.getOrderType() == OrderType.NORTH || deviceMovement.getOrderType() == OrderType.EAST)
            this.devicePosition = lastPosition.min(obstaclePosition).min(occupiedPosition);
        else if (deviceMovement.getOrderType() == OrderType.SOUTH || deviceMovement.getOrderType() == OrderType.WEST)
            this.devicePosition = lastPosition.max(obstaclePosition).max(occupiedPosition);
        return true;
    }

    private Coordinate calculateTargetPosition(Order deviceMovement) {
        Coordinate coordinate=new Coordinate(devicePosition.getX(),devicePosition.getY());
        switch (deviceMovement.getOrderType()) {
            case NORTH: coordinate.setY(coordinate.getY()+deviceMovement.getNumberOfSteps()); break;
            case SOUTH: coordinate.setY(coordinate.getY()-deviceMovement.getNumberOfSteps()); break;
            case WEST: coordinate.setX(coordinate.getX()-deviceMovement.getNumberOfSteps()); break;
            case EAST: coordinate.setX(coordinate.getX()+deviceMovement.getNumberOfSteps()); break;
            default: throw new IllegalArgumentException("Illegal NextDirection: "+ deviceMovement.getOrderType());
        }
        return coordinate;
    }

    @Override
    public Coordinate getPosition() {
        return this.devicePosition;
    }

    @Override
    public Walkable getSpace() {
        return deviceSpace;
    }

    @Override
    public Boolean occupiedSpace(Coordinate coordinate) {
        return coordinate.equals(this.devicePosition);
    }

    @Override
    public UUID getId() {
        return this.cleaningDeviceId;
    }

    @Override
    public Coordinate getUnaccessedCoordinate(Coordinate actualCoordinate, Coordinate targetCoordinate) {
        OrderType blockedDirection = calculateDirection(actualCoordinate, targetCoordinate);
        switch (blockedDirection) {
            case NORTH:  return calculateBlockPositionNorth(actualCoordinate, targetCoordinate);
            case SOUTH:  return calculateBlockPositionSouth(actualCoordinate, targetCoordinate);
            case WEST:   return calcBlockPositionWest(actualCoordinate, targetCoordinate);
            case EAST:   return calculateBlockPositionEast(actualCoordinate, targetCoordinate);
            default: throw new IllegalArgumentException("Illegal BlockDirection: "+ blockedDirection);
        }
    }

    private static OrderType calculateDirection(Coordinate currentPosition, Coordinate targetPosition) {
        int horizontalSteps = targetPosition.getX() - currentPosition.getX();
        int verticalSteps = targetPosition.getY() - currentPosition.getY();
        if (verticalSteps > 0)          return OrderType.NORTH;
        else if (verticalSteps < 0)     return OrderType.SOUTH;
        else if (horizontalSteps > 0)   return OrderType.EAST;
        else if (horizontalSteps < 0)   return OrderType.WEST;
        else                            return OrderType.illegal;
    }
    private Coordinate calculateBlockPositionNorth(Coordinate currentPosition, Coordinate targetPosition) {
        if (this.getPosition().getX().equals(currentPosition.getX())
                && this.getPosition().getY() > currentPosition.getY()
                && this.getPosition().getY() <= targetPosition.getY() )
            targetPosition = new Coordinate(this.getPosition().getX(), this.getPosition().getY()-1);
        return targetPosition;
    }


    private Coordinate calculateBlockPositionSouth(Coordinate currentPosition, Coordinate targetPosition) {
        if (this.getPosition().getX().equals(currentPosition.getX())
                && this.getPosition().getY() < currentPosition.getY()
                && this.getPosition().getY() >= targetPosition.getY() )
            targetPosition = new Coordinate( this.getPosition().getX(), this.getPosition().getY()+1);
        return targetPosition;
    }

    private Coordinate calculateBlockPositionEast(Coordinate currentPosition, Coordinate targetPosition) {
        if (this.getPosition().getY().equals(currentPosition.getY())
                && this.getPosition().getX() > currentPosition.getX()
                && this.getPosition().getX() <= targetPosition.getX() )
            targetPosition = new Coordinate(this.getPosition().getX()-1, this.getPosition().getY());
        return targetPosition;
    }

    private Coordinate calcBlockPositionWest(Coordinate currentPosition, Coordinate targetPosition) {
        if (this.getPosition().getY().equals(currentPosition.getY())
                && this.getPosition().getX() < currentPosition.getX()
                && this.getPosition().getX() >= targetPosition.getX() )
            targetPosition = new Coordinate(this.getPosition().getX()+1, this.getPosition().getY());
        return targetPosition;
    }




}

