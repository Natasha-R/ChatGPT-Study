package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;

import java.util.UUID;

public class Connection {
    @Getter
    UUID id;

    UUID sourceRoomId;
    Coordinate sourceCoordinate;
    UUID destinationRoomId;
    Coordinate destinationCoordinate;

    public Connection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        this.id = UUID.randomUUID();
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }
}
