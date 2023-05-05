package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import java.util.UUID;

public interface Commandable {
    boolean movement(UUID cleaningDevice, Command command);
    boolean transport(UUID cleaningDevice, Command command);
    boolean initialize(UUID cleaningDevice, Command command);
}
