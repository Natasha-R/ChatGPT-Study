package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;

public interface Delimitable {

    Connection getConnection() throws NoConnectionException;
    boolean hasConnection();
    Coordinate getCoordinate();
    void setConnection(Connection connection);
    boolean isBlocked();
    void changeBlocked();



}
