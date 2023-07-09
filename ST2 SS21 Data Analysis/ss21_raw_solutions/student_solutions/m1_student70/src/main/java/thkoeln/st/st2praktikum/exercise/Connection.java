package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Connection {
    private UUID connectionId;
    private UUID sourceSpaceId;
    private UUID destinationSpaceId;
 private    Point sourceCoordinate, destinationCoordinate;

    public Connection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceCoordinate = new Point(sourceCoordinate);
        this.destinationCoordinate = new Point(destinationCoordinate);


    }

}
