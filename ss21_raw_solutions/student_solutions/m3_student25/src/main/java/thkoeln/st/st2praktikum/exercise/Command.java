package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Getter
@Embeddable
public class Command {
    private Direction direction;
    private Integer steps = null;
    private UUID spaceShipDeckUUID = null;

    public Command(Direction direction, Integer steps) {
        this.direction = direction;
        this.steps = steps;
    }

    public Command(Direction direction, UUID spaceShipDeckUUID) {
        this.direction = direction;
        this.spaceShipDeckUUID = spaceShipDeckUUID;
    }

    protected Command() {}

    public Boolean isStepCommand() {
        return steps != null;
    }

    public Boolean isUUIDCommand() {
        return spaceShipDeckUUID != null;
    }
}
