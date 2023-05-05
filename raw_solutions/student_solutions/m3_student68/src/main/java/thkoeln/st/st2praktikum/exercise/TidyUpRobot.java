package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.Direction;
import thkoeln.st.st2praktikum.exercise.core.RobotCommand;
import thkoeln.st.st2praktikum.exercise.interfaces.Instructable;
import thkoeln.st.st2praktikum.exercise.interfaces.Occupier;
import thkoeln.st.st2praktikum.exercise.interfaces.Walkable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TidyUpRobot implements Occupier, Instructable {
    @Id
    private UUID id;
    private String name;
    @Embedded
    private Coordinate coordinate;
    @ManyToOne(targetEntity = Room.class)
    private Walkable room;

    public TidyUpRobot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public UUID getId() { return this.id; }

    @Override
    public Coordinate getCoordinate() { return this.coordinate; }

    @Override
    public Walkable getRoom() { return this.room; }

    @Override
    public UUID getRoomId() { return this.room.getId(); }

    @Override
    public Boolean readCommand (RobotCommand robotCommand) {
        switch (robotCommand.getCommand()) {
            case SPAWN: return executeSpawnCommand(robotCommand);
            case MOVE:  return executeMoveCommand(robotCommand);
            case TRANSPORT: return executeTransportCommand(robotCommand);
            default: throw new IllegalArgumentException("Command not able to read: "+ robotCommand);
        }
    }

    private Boolean executeSpawnCommand(RobotCommand robotCommand) {
        if (this.coordinate != null)
            return false;
        if (robotCommand.getTargetRoom().isSpawnCoordinateAvailable()) {
            this.coordinate = robotCommand.getTargetRoom().getSpawnCoordinate();
            this.room = robotCommand.getTargetRoom();
            this.room.addOccupier(this);
            return true;
        }
        return false;
    }

    private Boolean executeTransportCommand(RobotCommand robotCommand) {
       if ( this.room.isValidTransportPosition(this.coordinate, robotCommand.getTargetRoom()) ) {
           Coordinate newCoordinate = this.room.getTransportDestinationPosition(this.coordinate, robotCommand.getTargetRoom());
           if ( !robotCommand.getTargetRoom().isFieldOccupied(newCoordinate) ) {
               this.room.removeOccupier(this);
               this.coordinate = newCoordinate;
               this.room = robotCommand.getTargetRoom();
               this.room.addOccupier(this);
               return true;
           }
        }
        return false;
    }

    private Boolean executeMoveCommand(RobotCommand robotCommand) {
        if (this.coordinate == null)
            return false;
        Coordinate targetCoordinate = calcTargetCoordinate(robotCommand);
        Coordinate blockCoordinate1 = this.room.getBorderBlockPosition(targetCoordinate);
        Coordinate blockCoordinate2 = this.room.getBarrierBlockPosition(this.coordinate, targetCoordinate);
        Coordinate blockCoordinate3 = this.room.getOccupierBlockPosition(this.coordinate, targetCoordinate);
        if (robotCommand.getDirection() == Direction.NORTH || robotCommand.getDirection() == Direction.EAST)
            this.coordinate = blockCoordinate1.min(blockCoordinate2).min(blockCoordinate3);
        else if (robotCommand.getDirection() == Direction.SOUTH || robotCommand.getDirection() == Direction.WEST)
            this.coordinate = blockCoordinate1.max(blockCoordinate2).max(blockCoordinate3);
        return true;
    }

    private Coordinate calcTargetCoordinate(RobotCommand robotCommand) {
        Integer xCoordinate = this.coordinate.getX();
        Integer yCoordinate = this.coordinate.getY();
        switch (robotCommand.getDirection()) {
            case NORTH: yCoordinate+=robotCommand.getSteps(); break;
            case SOUTH: yCoordinate-=robotCommand.getSteps(); break;
            case WEST: xCoordinate-=robotCommand.getSteps(); break;
            case EAST: xCoordinate+=robotCommand.getSteps(); break;
         default: throw new IllegalArgumentException("Illegal TargetDirection: "+ robotCommand.getDirection());
        }
        if (xCoordinate<0)  xCoordinate = 0;
        if (yCoordinate<0)  yCoordinate = 0;
        return new Coordinate(xCoordinate, yCoordinate);
    }

    @Override
    public Coordinate getBlockCoordinate(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Direction blockDirection = calcDirection(currentCoordinate, targetCoordinate);
        switch (blockDirection) {
            case NORTH:  return calcBlockCoordinateNorth(currentCoordinate, targetCoordinate);
            case SOUTH:  return calcBlockCoordinateSouth(currentCoordinate, targetCoordinate);
            case WEST:   return calcBlockCoordinateWest(currentCoordinate, targetCoordinate);
            case EAST:   return calcBlockCoordinateEast(currentCoordinate, targetCoordinate);
         default: throw new IllegalArgumentException("Illegal BlockDirection: "+ blockDirection);
         }
    }

    private static Direction calcDirection(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Integer horizontalSteps = targetCoordinate.getX() - currentCoordinate.getX();
        Integer verticalSteps = targetCoordinate.getY() - currentCoordinate.getY();
        if (verticalSteps > 0)          return Direction.NORTH;
        else if (verticalSteps < 0)     return Direction.SOUTH;
        else if (horizontalSteps > 0)   return Direction.EAST;
        else if (horizontalSteps < 0)   return Direction.WEST;
        else                            return Direction.NONE;
    }

    private Coordinate calcBlockCoordinateNorth(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getCoordinate().getX() == currentCoordinate.getX()
            && this.getCoordinate().getY() > currentCoordinate.getY()
            && this.getCoordinate().getY() <= targetCoordinate.getY() )
                 targetCoordinate = new Coordinate(this.getCoordinate().getX(), this.getCoordinate().getY()-1);
        return targetCoordinate;
    }

    private Coordinate calcBlockCoordinateSouth(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getCoordinate().getX() == currentCoordinate.getX()
            && this.getCoordinate().getY() < currentCoordinate.getY()
            && this.getCoordinate().getY() >= targetCoordinate.getY() )
                targetCoordinate = new Coordinate(this.getCoordinate().getX(), this.getCoordinate().getY()+1);
        return targetCoordinate;
    }

    private Coordinate calcBlockCoordinateEast(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getCoordinate().getY() == currentCoordinate.getY()
            && this.getCoordinate().getX() > currentCoordinate.getX()
            && this.getCoordinate().getX() <= targetCoordinate.getX() )
                targetCoordinate = new Coordinate(this.getCoordinate().getX()-1, this.getCoordinate().getY());
        return targetCoordinate;
    }

    private Coordinate calcBlockCoordinateWest(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getCoordinate().getY() == currentCoordinate.getY()
            && this.getCoordinate().getX() < currentCoordinate.getX()
            && this.getCoordinate().getX() >= targetCoordinate.getX() )
                targetCoordinate = new Coordinate(this.getCoordinate().getX()+1, this.getCoordinate().getY());
        return targetCoordinate;
    }

    public Boolean occupiedField(Coordinate coordinate) {
        if (coordinate.equals(this.coordinate))
            return true;
        return false;
    }


}
