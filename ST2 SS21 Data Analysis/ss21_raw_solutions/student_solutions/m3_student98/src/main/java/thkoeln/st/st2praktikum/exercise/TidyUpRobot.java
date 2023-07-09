package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.Moveable;
import thkoeln.st.st2praktikum.exercise.core.PositionBlockedException;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TidyUpRobot extends AbstractRobot {

    public TidyUpRobot(String name) {
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

    public Boolean isOccupied(Vector2D position) {
        return (this.position.equals(position));
    }

    private Boolean moveRobot(Task task, MapInstance map) {
        Moveable movement = createMovementFromTask(task, map);
        movement = this.position.getRoom().validateMovement(movement);
        setNewPosition(movement.getCurrentPosition());
        return true;
    }

    private Boolean initializeRobot(Task task, MapInstance map) {
        Room newRoom = map.findRoom(task.getGridId());
        Vector2D newPosition = new Vector2D(0, 0, newRoom);
        if (newRoom.roomPositionIsEmpty(newPosition)) {
            newRoom.addOccupiedPosition(this);
            this.position = newPosition;
            return true;
        }
        return false;
    }

    private Boolean transportRobot(Task task, MapInstance map) {
        Room currentRoom = this.position.getRoom();
        Room targetRoom = map.findRoom(task.getGridId());
        Vector2D currentPosition = this.position;
        Vector2D newPosition;

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

    private void setNewPosition(Vector2D position) {
        this.position = position;
    }

    private void addRobotToNewRoom(Room newRoom) {
        newRoom.addOccupiedPosition(this);
    }

    private void removeRobotFromOldRoom(Room oldRoom) {
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
