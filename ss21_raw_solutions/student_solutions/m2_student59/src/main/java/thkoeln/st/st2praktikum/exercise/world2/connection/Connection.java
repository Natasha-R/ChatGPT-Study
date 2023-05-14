package thkoeln.st.st2praktikum.exercise.world2.connection;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Id;
import java.util.UUID;

@Getter
public class Connection {

    @Id
    private final UUID connectionId = UUID.randomUUID();
    private final UUID sourceFieldId;
    private final Point sourcePoint;
    private final UUID destinationFieldId;
    private final Point destinationPoint;

    public Connection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        this.sourceFieldId = sourceFieldId;
        this.sourcePoint = sourcePoint;
        this.destinationFieldId = destinationFieldId;
        this.destinationPoint = destinationPoint;
    }
}
