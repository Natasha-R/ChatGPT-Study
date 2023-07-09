package thkoeln.st.st2praktikum.exercise.command;

import java.util.UUID;

public interface CommandInterface {
    Direction getDirection();

    Integer getSteps();

    UUID getSpaceShipDeckUUUID();

    Boolean isStepCommand();

    Boolean isUUIDCommand();
}

