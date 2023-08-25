package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.InvalidInputException;
import thkoeln.st.st2praktikum.exercise.core.Moveable;
import thkoeln.st.st2praktikum.exercise.core.PositionBlockedException;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Room extends AbstractRoom {

    public Room(Integer height, Integer width) {
        this.id = UUID.randomUUID();
        this.roomWidth = width;
        this.roomHeight = height;
    }

    @Override
    public Boolean roomPositionIsEmpty(Vector2D position) {
        for (TidyUpRobot robot: occupiedFields) {
            if (robot.isOccupied(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Moveable validateMovement(Moveable movement) {
        while (movement.getMovementStatus() == MovementStatus.READY || movement.getMovementStatus() == MovementStatus.RUNNING) {
            if (validateNextStep(movement)) {
                movement.takeSingleStep();
            } else {
                movement.setStatusBlocked();
            }
        }
        return movement;
    }

    @Override
    public Boolean isTransportable(Vector2D currentPosition, Room targetRoom) {
        return findFirstMatchingConnection(currentPosition, targetRoom) != null;
    }

    @Override
    public Vector2D getNewTransportedPosition(Vector2D currentPosition, Room targetRoom) throws PositionBlockedException {
        Connection transportConnection = findFirstMatchingConnection(currentPosition, targetRoom);
        Vector2D newPosition = transportConnection.getToPosition();
        if (newPosition.getRoom().roomPositionIsEmpty(newPosition)) {
            return newPosition;
        }
        throw new PositionBlockedException("Transport location is blocked or occupied at the moment.");
    }

    private Boolean validateNextStep(Moveable movement) {
        try {
            if (!validateRoomBorder(movement))
                return false;
            if (!validateObstacles(movement))
                return false;
            if (!validateOccupiedFields(movement))
                return false;
        } catch (InvalidInputException e) {
            return false;
        }
        return true;
    }

    private Boolean validateRoomBorder(Moveable movement) {
        int xPos = movement.nextRequestedPositionFromCurrentPosition().getX();
        int yPos = movement.nextRequestedPositionFromCurrentPosition().getY();
        if (xPos < 0 || xPos >= roomWidth)
            return false;
        if (yPos < 0 || yPos >= roomHeight)
            return false;
        return true;
    }

    private Boolean validateObstacles(Moveable movement) {
        for (Obstacle element : obstacles) {
            if (!element.isPassable(movement))
                return false;
        }
        return true;
    }

    private Boolean validateOccupiedFields(Moveable movement) {
        for (TidyUpRobot element : occupiedFields) {
            if (element.isOccupied(movement.nextRequestedPositionFromCurrentPosition()))
                return false;
        }
        return true;
    }

    private Connection findFirstMatchingConnection(Vector2D position, Room targetRoom) {
        for (Connection connection : this.connections) {
            if (connection.getFromPosition().equals(position)) {
                if (connection.getToPosition().getRoomID().equals(targetRoom.getId())) {
                    return connection;
                }
            }
        }
        return null;
    }
}
