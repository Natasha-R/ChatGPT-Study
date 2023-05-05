package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class Connection {
    private final UUID connectionID = UUID.randomUUID();
    @Embedded
    private Vector2D destinationCoordinate;
    private UUID destinationRoom;

    public Connection(Vector2D vector2D, UUID destinationRoomParam) {
        destinationCoordinate = vector2D;
        destinationRoom = destinationRoomParam;
    }

    public Vector2D getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getDestinationRoom() {
        return destinationRoom;
    }

    public UUID getConnectionID() { return connectionID; }
}

