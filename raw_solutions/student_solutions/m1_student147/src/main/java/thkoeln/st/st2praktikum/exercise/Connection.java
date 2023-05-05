package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Connection {
    private UUID id;
    private UUID sourceId;
    private UUID destinationId;
    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceSpaceId, String sourceSpaceCoordinate,
                      UUID destinationSpaceId, String destinationSpaceCoordinate) {
        id = UUID.randomUUID();
        this.sourceId = sourceSpaceId;
        this.sourceCoordinate = Coordinate.getCoordinate(sourceSpaceCoordinate);
        this.destinationId = destinationSpaceId;
        this.destinationCoordinate = Coordinate.getCoordinate(destinationSpaceCoordinate);
    }
}
