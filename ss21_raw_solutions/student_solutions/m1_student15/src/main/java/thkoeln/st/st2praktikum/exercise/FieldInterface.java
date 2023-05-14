package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface FieldInterface {
    void addWall(String wallString);
    void stringToCoordinate(String wall);
    public UUID getId();
    Field getField();
}
