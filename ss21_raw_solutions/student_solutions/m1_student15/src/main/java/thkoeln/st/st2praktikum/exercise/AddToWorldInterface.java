package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface AddToWorldInterface {
    UUID addField(Integer height, Integer width);
    void addWall(UUID fieldId, String wallString);
    UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate);
    UUID addMiningMachine(String name);
}
