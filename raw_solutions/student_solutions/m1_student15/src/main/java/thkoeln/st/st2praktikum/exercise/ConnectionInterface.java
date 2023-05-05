package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ConnectionInterface {
    Coordinate stringToCoordinate(String coord);
    UUID getId();
    Connection getConnection();
}
