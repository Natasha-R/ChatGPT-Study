package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Connection {
    private UUID id;
    private UUID sourceId, destinationId;
    private Coordinate sourceCoordinate, destinationCoordinate;

    public Connection(UUID sourceRoomId, Coordinate sourceRoomCoordinate,
                      UUID destinationRoomId, Coordinate destinationRoomCoordinate) {
        id = UUID.randomUUID();
        this.sourceId = sourceRoomId;
        this.sourceCoordinate = sourceRoomCoordinate;
        this.destinationId = destinationRoomId;
        this.destinationCoordinate = destinationRoomCoordinate;
    }
}
