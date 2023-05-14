package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Connection extends thkoeln.st.st1praktikum.core.AbstractEntity {
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

