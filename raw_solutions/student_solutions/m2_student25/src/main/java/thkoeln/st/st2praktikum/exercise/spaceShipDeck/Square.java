package thkoeln.st.st2praktikum.exercise.spaceShipDeck;

import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.coordinate.MyCoordinateInterface;

import java.util.UUID;

public class Square {
    private final MyCoordinateInterface coordinates;
    private final UUID squareID = UUID.randomUUID();
    private Connection connection;

    public Square(MyCoordinateInterface coordinates) {
        this.coordinates = coordinates;
    }

    public Boolean hasConnection() {
        return connection != null;
    }

    public UUID getSquareID() {
        return squareID;
    }

    public MyCoordinateInterface getCoordinates() {
        return coordinates;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
