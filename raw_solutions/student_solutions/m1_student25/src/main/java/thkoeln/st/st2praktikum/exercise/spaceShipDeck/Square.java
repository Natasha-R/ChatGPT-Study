package thkoeln.st.st2praktikum.exercise.spaceShipDeck;

import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.coordinate.CoordinateInterface;

import java.util.UUID;

public class Square {
    private final CoordinateInterface coordinates;
    private final UUID squareID = UUID.randomUUID();
    private Connection connection;

    public Square(CoordinateInterface coordinates) {
        this.coordinates = coordinates;
    }

    public Boolean hasConnection(){
        return connection != null;
    }

    public UUID getSquareID() {
        return squareID;
    }

    public CoordinateInterface getCoordinates() {
        return coordinates;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
