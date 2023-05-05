package thkoeln.st.st2praktikum.exercise.command;

import java.util.UUID;

public class Command implements CommandInterface, UUIDCommandInterface, StepCommandInterface {
    final private Direction direction;
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

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Integer getSteps() {
        return steps;
    }

    @Override
    public UUID getSpaceShipDeckUUUID() {
        return spaceShipDeckUUID;
    }

    @Override
    public Boolean isStepCommand() {
        return steps != null;
    }

    @Override
    public Boolean isUUIDCommand() {
        return spaceShipDeckUUID != null;
    }
}
