package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.room.Walkable;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;

import javax.persistence.Id;
import java.util.UUID;

public class Connection implements Connectable {
    @Id
    private UUID id;
    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;
    private Walkable sourceRoom;
    private Walkable destinationRoom;

    public Connection(Walkable sourceRoom, String sourceCoordinates, Walkable destinationRoom, String destinationCoordinates) {
        this.id = UUID.randomUUID();
        this.sourceCoordinate = new Coordinate(sourceCoordinates);
        this.destinationCoordinate = new Coordinate(destinationCoordinates);
        this.sourceRoom = sourceRoom;
        this.destinationRoom = destinationRoom;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public Coordinate getSourcePosition() {
        return this.sourceCoordinate;
    }

    @Override
    public Coordinate getDestinationPosition() {
        return this.destinationCoordinate;
    }

    @Override
    public Walkable getSourceRoom() { return this.sourceRoom; }

    @Override
    public Walkable getDestinationRoom() { return this.destinationRoom; }


}
