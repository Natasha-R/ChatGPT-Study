package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface AddToWorldInterface {
    UUID addField(Integer height, Integer width);
    void addWall(UUID fieldId, Wall wall);
    UUID addConnection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate);
    UUID addMiningMachine(String name);
}


