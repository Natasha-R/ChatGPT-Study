package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Commandable {
    boolean movement(UUID cleaningDevice, String command);
    boolean transport(UUID cleaningDevice, String command);
    boolean initialize(UUID cleaningDevice, String command);
}
