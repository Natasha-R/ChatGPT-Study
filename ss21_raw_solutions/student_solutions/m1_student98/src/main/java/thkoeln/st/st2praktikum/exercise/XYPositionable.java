package thkoeln.st.st2praktikum.exercise;

import java.util.Optional;
import java.util.UUID;

public interface XYPositionable {

    Integer getXPos();

    Integer getYPos();

    Roomable getRoom();

    UUID getRoomID();

    Boolean equals(XYPositionable newPosition);

    XYPositionable clone(Optional<Integer> newXPos, Optional<Integer> newYPos, Optional<Roomable> newRoom);

    String coordinatesToString();

    String debugPositionToString();
}
