package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Addable {
    UUID addField(Integer height, Integer width);
    void addWall(UUID fieldId, Wall wall);
    UUID addConnection(UUID transportCategoryId, UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate);
    UUID addMiningMachine(String name);
}

