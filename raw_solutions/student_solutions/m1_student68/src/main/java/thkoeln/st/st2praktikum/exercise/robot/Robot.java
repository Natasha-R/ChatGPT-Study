package thkoeln.st.st2praktikum.exercise.robot;

import javax.persistence.Id;
import java.util.UUID;
import thkoeln.st.st2praktikum.exercise.core.Direction;
import thkoeln.st.st2praktikum.exercise.room.Walkable;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;
import thkoeln.st.st2praktikum.exercise.core.RobotCommand;


public class Robot implements Occupier, Instructable {
    @Id
    private UUID id;
    private String name;
    private Coordinate coordinate;
    private Walkable room;

    public Robot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public UUID getId() { return this.id; }

    @Override
    public Coordinate getPosition() { return this.coordinate; }

    @Override
    public Walkable getRoom() { return this.room; }

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
        Coordinate targetCoordinate = calcTargetPosition(robotCommand);
        Coordinate blockCoordinate1 = this.room.getBorderBlockPosition(targetCoordinate);
        Coordinate blockCoordinate2 = this.room.getBarrierBlockPosition(this.coordinate, targetCoordinate);
        Coordinate blockCoordinate3 = this.room.getOccupierBlockPosition(this.coordinate, targetCoordinate);
        if (robotCommand.getDirection() == Direction.NORTH || robotCommand.getDirection() == Direction.EAST)
            this.coordinate = blockCoordinate1.min(blockCoordinate2).min(blockCoordinate3);
        else if (robotCommand.getDirection() == Direction.SOUTH || robotCommand.getDirection() == Direction.WEST)
            this.coordinate = blockCoordinate1.max(blockCoordinate2).max(blockCoordinate3);
        return true;
    }

    private Coordinate calcTargetPosition(RobotCommand robotCommand) {
        Integer xCoordinate = this.coordinate.getX();
        Integer yCoordinate = this.coordinate.getY();
        switch (robotCommand.getDirection()) {
            case NORTH: yCoordinate+=robotCommand.getSteps(); break;
            case SOUTH: yCoordinate-=robotCommand.getSteps(); break;
            case WEST: xCoordinate-=robotCommand.getSteps(); break;
            case EAST: xCoordinate+=robotCommand.getSteps(); break;
         default: throw new IllegalArgumentException("Illegal TargetDirection: "+ robotCommand.getDirection());
        }
        return new Coordinate(xCoordinate, yCoordinate);
    }

    @Override
    public Coordinate getBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Direction blockDirection = calcDirection(currentCoordinate, targetCoordinate);
        switch (blockDirection) {
            case NORTH:  return calcBlockPositionNorth(currentCoordinate, targetCoordinate);
            case SOUTH:  return calcBlockPositionSouth(currentCoordinate, targetCoordinate);
            case WEST:   return calcBlockPositionWest(currentCoordinate, targetCoordinate);
            case EAST:   return calcBlockPositionEast(currentCoordinate, targetCoordinate);
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

    private Coordinate calcBlockPositionNorth(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getPosition().getX() == currentCoordinate.getX()
            && this.getPosition().getY() > currentCoordinate.getY()
            && this.getPosition().getY() <= targetCoordinate.getY() )
                 targetCoordinate = new Coordinate(this.getPosition().getX(), this.getPosition().getY()-1);
        return targetCoordinate;
    }

    private Coordinate calcBlockPositionSouth(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getPosition().getX() == currentCoordinate.getX()
            && this.getPosition().getY() < currentCoordinate.getY()
            && this.getPosition().getY() >= targetCoordinate.getY() )
                targetCoordinate = new Coordinate(this.getPosition().getX(), this.getPosition().getY()+1);
        return targetCoordinate;
    }

    private Coordinate calcBlockPositionEast(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getPosition().getY() == currentCoordinate.getY()
            && this.getPosition().getX() > currentCoordinate.getX()
            && this.getPosition().getX() <= targetCoordinate.getX() )
                targetCoordinate = new Coordinate(this.getPosition().getX()-1, this.getPosition().getY());
        return targetCoordinate;
    }

    private Coordinate calcBlockPositionWest(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        if ( this.getPosition().getY() == currentCoordinate.getY()
            && this.getPosition().getX() < currentCoordinate.getX()
            && this.getPosition().getX() >= targetCoordinate.getX() )
                targetCoordinate = new Coordinate(this.getPosition().getX()+1, this.getPosition().getY());
        return targetCoordinate;
    }

    public Boolean occupiedField(Coordinate coordinate) {
        if (coordinate.equals(this.coordinate))
            return true;
        return false;
    }


}
