package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import java.util.UUID;

public interface Commandable {
    boolean movement(UUID maintenanceDroid, Command command);
    boolean transport(UUID maintenanceDroid, Command command);
    boolean initialize(UUID maintenanceDroid, Command command);

}