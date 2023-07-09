package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Jumpable {
    boolean connectable(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId);
}
