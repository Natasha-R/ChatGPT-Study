package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.FieldComponent.Connection;
import thkoeln.st.st2praktikum.exercise.FieldComponent.IConnection;

import java.util.UUID;

public interface IConnectionManager {
    Connection createConnection(Field sourceFieldId, int[] sourceCoordinate, Field destinationFieldId, int[] destinationCoordinate, UUIDManager uuidManager);
    boolean useConnection(UUID usedConnectionUUID);
    IConnection getConnection(String sourceFieldUUID, String destinationFieldUUID);
}
