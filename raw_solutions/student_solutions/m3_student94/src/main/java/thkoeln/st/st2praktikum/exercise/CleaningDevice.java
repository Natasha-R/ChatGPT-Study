package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class CleaningDevice {
    @Id
    protected UUID cleaningDeviceId;

    protected   String name;

    private UUID spaceId;

    @ManyToOne
    public Space deviceSpace;

    @Embedded
    private Order order;

    @Embedded
    private Coordinate coordinate;

    public CleaningDevice(String name){
        this.name=name;
        this.cleaningDeviceId=UUID.randomUUID();
    }
    /*public Boolean analysedCommand(Order deviceMovement) {

        if (deviceMovement.getOrderType().isOrderType()) return moveCommand(deviceMovement);
        else if (deviceMovement.getOrderType()== OrderType.ENTER )return InitialCommand( deviceMovement);
        else if (deviceMovement.getOrderType()==OrderType.TRANSPORT)return TransportCommand(deviceMovement);
        else return false;

    }

    private Boolean InitialCommand(Order deviceMovement) {
        if (this.devicePosition != null)
            return false;
        deviceSpace= Assembler.getSpaceRepository().findById(deviceMovement.getSpaceId()).get();
        spaceId=deviceSpace.getId();
        if ((Assembler.getSpaceRepository().findById(deviceMovement.getSpaceId())).get().isInitialPositionFree()) {
            this.devicePosition = (Assembler.getSpaceRepository().findById(deviceMovement.getSpaceId()).get().getInitialPosition());
            deviceSpace.addOccupied(this);
            return true;
        }
        return false;
    }
    private Boolean TransportCommand(Order deviceCommand) {
        if (( Assembler.getSpaceRepository().findById(spaceId)).get().isTransportPositionValid(this.devicePosition,Assembler.getSpaceRepository().findById(deviceCommand.getSpaceId()).get()) ) {
            Coordinate newPosition = (Assembler.getSpaceRepository().findById(spaceId)).get().getTransportDestinationPosition(this.devicePosition,Assembler.getSpaceRepository().findById(deviceCommand.getSpaceId()).get());
            if (!( Assembler.getSpaceRepository().findById(deviceCommand.getSpaceId()).get().isSpaceOccupied(newPosition))) {
                Assembler.getSpaceRepository().findById(spaceId).get().removeOccupied(this);
                this.devicePosition = newPosition;
                Assembler.getSpaceRepository().findById(spaceId).get().addOccupied(this);
                spaceId=deviceCommand.getSpaceId();
                deviceSpace= Assembler.getSpaceRepository().findById(deviceCommand.getSpaceId()).get();
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

    public UUID getId() {
        return this.cleaningDeviceId;
    }

    public Coordinate getPosition() { return this.devicePosition; }

    public Space getSpace() {
        return deviceSpace;
    }

    public Boolean occupiedSpace(Coordinate coordinate) {
        return coordinate.equals(this.devicePosition);
    }

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
    }*/

}


