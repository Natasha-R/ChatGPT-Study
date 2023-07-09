package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Robot extends AbstractRobot implements Occupied{

    public Robot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public Boolean processTask(Task task, MapInstance map) {
        switch (task.getTaskType()) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:      return moveRobot(task, map);
            case ENTER:     return initializeRobot(task, map);
            case TRANSPORT: return transportRobot(task, map);
        }
        return false;
    }

    @Override
    public Boolean isOccupied(Vector2DRoom position) {
        return (this.position.equals(position));
    }

    private Boolean moveRobot(Task task, MapInstance map) {
        Moveable movement = createMovementFromTask(task, map);
        movement = this.position.getRoom().validateMovement(movement);
        setNewPosition(movement.getCurrentPosition());
        return true;
    }

    private Boolean initializeRobot(Task task, MapInstance map) {
        AbstractRoom newRoom = map.findRoom(task.getGridId());
        Vector2DRoom newPosition = new Vector2DRoom(newRoom, 0, 0);
        if (newRoom.roomPositionIsEmpty(newPosition)) {
            newRoom.addOccupiedPosition(this);
            this.position = newPosition;
            return true;
        }
        return false;
    }

    private Boolean transportRobot(Task task, MapInstance map) {
        AbstractRoom currentRoom = this.position.getRoom();
        AbstractRoom targetRoom = map.findRoom(task.getGridId());
        Vector2DRoom currentPosition = this.position;
        Vector2DRoom newPosition;

        if (currentRoom.isTransportable(currentPosition, targetRoom)) {
            try {
                newPosition = currentRoom.getNewTransportedPosition(currentPosition, targetRoom);
                addRobotToNewRoom(targetRoom);
                removeRobotFromOldRoom(currentRoom);
                setNewPosition(newPosition);
                return true;
            } catch (PositionBlockedException e) {
                return false;
            }
        }
        return false;
    }

    private void setNewPosition(Vector2DRoom position) {
        this.position = position;
    }

    private void addRobotToNewRoom(AbstractRoom newRoom) {
        newRoom.addOccupiedPosition(this);
    }

    private void removeRobotFromOldRoom(AbstractRoom oldRoom) {
        oldRoom.removeOccupiedPosition(id);
    }

    private Moveable createMovementFromTask(Task task, MapInstance map) {
        return new Movement(this.position, parseTaskTypeToMovementOrientation(task.getTaskType()), task.getNumberOfSteps());
    }

    private Orientation parseTaskTypeToMovementOrientation(TaskType task) {
        switch (task) {
            case NORTH: return Orientation.NO;
            case EAST:  return Orientation.EA;
            case SOUTH: return Orientation.SO;
            case WEST:  return Orientation.WE;
            default: throw new RuntimeException();
        }
    }
}
