package thkoeln.st.st2praktikum.exercise.field;

import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;

import java.util.UUID;

public interface Delimitable {

  Connection getConnection() throws NoConnectionException;
  boolean hasConnection();
  Coordinate getCoordinate();
  void setConnection(Connection connection);
  boolean isBlocked();
  void changeBlocked();



}
