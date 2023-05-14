package thkoeln.st.st2praktikum.exercise.world2.square;

import thkoeln.st.st2praktikum.exercise.world2.connection.Connection;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoConnectionFoundException;

import java.util.UUID;

public interface SquareController {

    Coordinate getCoordinate();
    boolean hasConnection();
    Connection getConnection() throws NoConnectionFoundException;
    void setConnection(Connection connection);
    boolean isBlocked();
    void changeBlocked();
    UUID getPlacedOnFieldId();

}
