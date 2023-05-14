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

    public Connection(UUID sourceRoomId, String sourceRoomCoordinate,
                      UUID destinationRoomId, String destinationRoomCoordinate) {
        id = UUID.randomUUID();
        this.sourceId = sourceRoomId;
        this.sourceCoordinate = Coordinate.getCoordinate(sourceRoomCoordinate);
        this.destinationId = destinationRoomId;
        this.destinationCoordinate = Coordinate.getCoordinate(destinationRoomCoordinate);
    }
}
