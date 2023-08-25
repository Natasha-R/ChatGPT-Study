package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.interfaces.Connectable;
import thkoeln.st.st2praktikum.exercise.interfaces.Walkable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;
@Entity
@NoArgsConstructor
public class Connection implements Connectable {
    @Id
    private UUID id;
    @Embedded
    private Coordinate sourceCoordinate;
    @Embedded
    private Coordinate destinationCoordinate;
    @ManyToOne(targetEntity = Room.class)
    private Walkable sourceRoom;
    @ManyToOne(targetEntity = Room.class)
    private Walkable destinationRoom;

    public Connection(Walkable sourceRoom, String sourceCoordinates, Walkable destinationRoom, String destinationCoordinates) {
        this.id = UUID.randomUUID();
        this.sourceCoordinate = new Coordinate(sourceCoordinates);
        this.destinationCoordinate = new Coordinate(destinationCoordinates);
        this.sourceRoom = sourceRoom;
        this.destinationRoom = destinationRoom;
    }

    public Connection(Walkable sourceRoom, Coordinate sourceCoordinates, Walkable destinationRoom, Coordinate destinationCoordinates) {
        this.id = UUID.randomUUID();
        this.sourceCoordinate = sourceCoordinates;
        this.destinationCoordinate = destinationCoordinates;
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
