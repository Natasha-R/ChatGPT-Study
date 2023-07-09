package thkoeln.st.st2praktikum.exercise.converter;

import java.util.UUID;

public interface Convertable {


    int [] pointStringToIntArray(String input);

    UUID toUUID(String input);

    String toUUIDAsString(String input);

}
