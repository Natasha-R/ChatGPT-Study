package thkoeln.st.st2praktikum.exercise.room.domain.movement;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domaintypes.Occupying;
import thkoeln.st.st2praktikum.exercise.domaintypes.Passable;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.Moveable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.MovementStatus;

import java.util.ArrayList;
import java.util.List;

public class RoomMovementProcessing {

    private final Room room;
    private final List<Occupying> occupyingElements;
    private final List<Passable> passableElements;


    public RoomMovementProcessing(Room room, List<Occupying> occupyingElements, List<Passable> passableElements) {
        this.room = room;
        this.occupyingElements = occupyingElements;
        this.passableElements = passableElements;
    }


    public Boolean roomPositionIsEmpty(Point position) {
        for (Occupying element : occupyingElements) {
            if (element.isOccupied(position)) {
                return false;
            }
        }
        return true;
    }

    public Moveable movePosition(Moveable movement) {
        while (movement.getMovementStatus() == MovementStatus.READY || movement.getMovementStatus() == MovementStatus.RUNNING) {
            if (validateNextStep(movement)) {
                movement.takeSingleStep();
            } else {
                movement.setStatusBlocked();
            }
        }
        return movement;
    }

    private Boolean validateNextStep(Moveable movement) {
        try {
            if (!validateRoomBorder(movement))
                return false;
            if (!validateBarriers(movement))
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
        if (xPos < 0 || xPos >= this.room.getRoomWidth())
            return false;
        if (yPos < 0 || yPos >= this.room.getRoomHeight())
            return false;
        return true;
    }

    private Boolean validateBarriers(Moveable movement) {
        for (Passable element : passableElements) {
            if (!element.isPassable(movement))
                return false;
        }
        return true;
    }

    private Boolean validateOccupiedFields(Moveable movement) {
        for (Occupying element : occupyingElements) {
            if (element.isOccupied(movement.nextRequestedPositionFromCurrentPosition()))
                return false;
        }
        return true;
    }
}
