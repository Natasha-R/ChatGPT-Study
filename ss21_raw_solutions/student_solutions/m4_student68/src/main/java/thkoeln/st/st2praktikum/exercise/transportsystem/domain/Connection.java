package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    private UUID sourceRoomId;
    private UUID destinationRoomId;

    public Connection(UUID sourceRoomId, String sourceCoordinates, UUID destinationRoomId, String destinationCoordinates) {
        this.id = UUID.randomUUID();
        this.sourceCoordinate = new Coordinate(sourceCoordinates);
        this.destinationCoordinate = new Coordinate(destinationCoordinates);
        this.sourceRoomId = sourceRoomId;
        this.destinationRoomId = destinationRoomId;
    }

    public Connection(UUID sourceRoomId, Coordinate sourceCoordinates, UUID destinationRoomId, Coordinate destinationCoordinates) {
        this.id = UUID.randomUUID();
        this.sourceCoordinate = sourceCoordinates;
        this.destinationCoordinate = destinationCoordinates;
        this.sourceRoomId = sourceRoomId;
        this.destinationRoomId = destinationRoomId;
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
    public UUID getSourceRoomId() { return this.sourceRoomId; }

    @Override
    public UUID getDestinationRoomId() { return this.destinationRoomId; }


}
