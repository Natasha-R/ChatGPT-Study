package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Room extends AbstractRoom {

    public Room(Integer height, Integer width) {
        this.id = UUID.randomUUID();
        this.roomWidth = width;
        this.roomHeight = height;
    }

    @Override
    public Boolean roomPositionIsEmpty(Vector2DRoom position) {
        for (Occupied element: occupiedFields) {
            if (element.isOccupied(position)) {
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
    public Boolean isTransportable(Vector2DRoom currentPosition, Identifiable targetRoom) {
        return findFirstMatchingConnection(currentPosition, targetRoom) != null;
    }

    @Override
    public Vector2DRoom getNewTransportedPosition(Vector2DRoom currentPosition, Identifiable targetRoom) throws PositionBlockedException {
        Connectable transportConnection = findFirstMatchingConnection(currentPosition, targetRoom);
        Vector2DRoom newPosition = transportConnection.getToPosition();
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
        for (Passable element : obstacles) {
            if (!element.isPassable(movement))
                return false;
        }
        return true;
    }

    private Boolean validateOccupiedFields(Moveable movement) {
        for (Occupied element : occupiedFields) {
            if (element.isOccupied(movement.nextRequestedPositionFromCurrentPosition()))
                return false;
        }
        return true;
    }

    private Connectable findFirstMatchingConnection(Vector2DRoom position, Identifiable targetRoom) {
        for (Connectable connection : this.connections) {
            if (connection.getFromPosition().equals(position)) {
                if (connection.getToPosition().getRoomID().equals(targetRoom.getId())) {
                    return connection;
                }
            }
        }
        return null;
    }
}
