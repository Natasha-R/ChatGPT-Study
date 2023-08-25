package thkoeln.st.st2praktikum.exercise.Entity;

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
    protected UUID sourceFieldId;
    protected UUID destinationFieldId;
    Point sourceCoordinate,destinationCoordinate;

    public Connection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate) {
        this.connectionId=UUID.randomUUID();
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldId = destinationFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }
}
