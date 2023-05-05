package thkoeln.st.st2praktikum.exercise.field;

import thkoeln.st.st2praktikum.exercise.UUidable;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.deck.Deck;
import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;

import java.util.UUID;


public class Field implements Delimitable, UUidable{

    private UUID fieldId;
    private UUID deckId;

    Connection connection;
    Coordinate coordinate;
    boolean blocked;

    public Field(UUID deckId, Coordinate coordinate, Connection connection, boolean blocked) {
        this.fieldId = UUID.randomUUID();
        this.deckId = deckId;
        this.connection = connection;
        this.coordinate = coordinate;
        this.blocked = blocked;
    }

    public Field(UUID deckId, Coordinate coordinate){
        this.fieldId = UUID.randomUUID();
        this.deckId = deckId;
        this.connection = null;
        this.coordinate = coordinate;
        this.blocked = false;
    }

    @Override
    public UUID getID() {
        return this.fieldId;
    }

    @Override
    public Connection getConnection() throws NoConnectionException {
        if(hasConnection()){
            return this.connection;
        }else{
            throw new NoConnectionException("No Connection" + this.coordinate.toString());
        }
    }

    @Override
    public boolean hasConnection() {
        return this.connection != null;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
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
        this.blocked = ! this.blocked;
    }

    public UUID getDeckId(){
        return this.deckId;
    }
}
