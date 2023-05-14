package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Connection {
    @Getter
    private UUID id;
    @Getter
    private UUID sourceRoomId;
    @Getter
    private Point sourceCoordinate;
    @Getter
    private UUID destinationRoomId;
    @Getter
    private Point destinationCoordinate;

    public Connection(UUID sourceRoomId, Point sourceCoordinate, UUID destinationRoomId, Point destinationCoordinate) {
        this.id = UUID.randomUUID();
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }
}