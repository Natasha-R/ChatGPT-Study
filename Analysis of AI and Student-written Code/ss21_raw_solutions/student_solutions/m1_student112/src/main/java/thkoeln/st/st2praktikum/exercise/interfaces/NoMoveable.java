package thkoeln.st.st2praktikum.exercise.interfaces;

import java.util.UUID;

public interface NoMoveable {
    UUID getWallId();

    UUID getField();
    void setField(UUID fieldId);

    Integer getSourceX();
    void setSourceX(Integer sourceX);

    Integer getSourceY();
    void setSourceY(Integer sourceY);

    void setDestinationX(Integer destinationX);
    Integer getDestinationX();

    void setDestinationY(Integer destinationY);
    Integer getDestinationY();
}
