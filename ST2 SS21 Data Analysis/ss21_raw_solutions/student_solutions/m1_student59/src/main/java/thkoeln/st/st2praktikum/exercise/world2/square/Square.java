package thkoeln.st.st2praktikum.exercise.world2.square;

import thkoeln.st.st2praktikum.exercise.world2.connection.Connection;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoConnectionFoundException;
import thkoeln.st.st2praktikum.exercise.world2.services.UUidable;

import java.util.UUID;

public class Square implements UUidable,SquareController{

    private final UUID squareId;
    private final UUID fieldId;

    Connection connection;
    Coordinate coordinate;
    boolean blocked;

    public Square(UUID fieldId, Coordinate coordinate){
        this.squareId = UUID.randomUUID();
        this.fieldId = fieldId;
        this.connection = null;
        this.coordinate = coordinate;
        this.blocked = false;
    }

    public Square(UUID fieldId, Coordinate coordinate, Connection connection){
        this.squareId = UUID.randomUUID();
        this.fieldId = fieldId;
        this.connection = connection;
        this.coordinate = coordinate;
        this.blocked = false;
    }

    public Square(UUID fieldId, Coordinate coordinate, boolean blocked){
        this.squareId = UUID.randomUUID();
        this.fieldId = fieldId;
        this.connection = null;
        this.coordinate = coordinate;
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "Square{" +
                "squareId=" + squareId +
                ", fieldId=" + fieldId +
                ", connection=" + connection +
                ", coordinate=" + coordinate +
                ", blocked=" + blocked +
                '}';
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public boolean hasConnection() {
        return this.connection != null;
    }

    @Override
    public Connection getConnection() throws NoConnectionFoundException {
        if(this.hasConnection()){
            return this.connection;
        }else{
            throw new NoConnectionFoundException(this.coordinate.toString());
        }
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isBlocked() {
        return this.blocked;
    }

    @Override
    public void changeBlocked() {
        this.blocked = !this.blocked;
    }

    @Override
    public UUID getPlacedOnFieldId() {
        return this.fieldId;
    }

    @Override
    public UUID getId() {
        return this.squareId;
    }
}
