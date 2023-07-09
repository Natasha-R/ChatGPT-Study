package thkoeln.st.st2praktikum.exercise.entyties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Connection {
    protected UUID connectionId;
    protected UUID sourceSpaceId;
    protected UUID destinationSpaceId;
    Point sourceCoordinate,destinationCoordinate;

    public Connection(UUID sourceSpaceId, Point sourceCoordinate, UUID destinationSpaceId, Point destinationCoordinate) {
        this.connectionId=UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }
}
