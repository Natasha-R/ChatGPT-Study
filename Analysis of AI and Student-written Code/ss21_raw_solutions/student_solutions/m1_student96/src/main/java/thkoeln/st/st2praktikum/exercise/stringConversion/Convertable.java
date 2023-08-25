package thkoeln.st.st2praktikum.exercise.stringConversion;

import java.util.UUID;

public interface Convertable {

    String toCommand(String input);

    UUID toUUID(String input);

    String toUUIDAsString(String input);

    int toSteps(String input);
}