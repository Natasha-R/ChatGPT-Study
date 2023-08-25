package thkoeln.st.st2praktikum.exercise;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Entity
public class Connection {
    @Id
    private UUID id = UUID.randomUUID();//@Column(name = "ID", insertable = false, updatable = false)
    @Embedded
    private Point sourcePoint;
    @Embedded
    private Point destinationPoint;
    private UUID sourceRoomId;
    private UUID destinationRoomId;

    public Connection() { }

    public UUID getId() { return id; }
    public Point getSourceCoordinate() { return sourcePoint; }
    public Point getDestinationCoordinate() { return destinationPoint; }
    public UUID getSourceRoomId() { return sourceRoomId; }
    public UUID getDestinationRoomId() { return destinationRoomId; }

    private Boolean isUnoccupied(Point point, List<MiningMachine> machinesOnTargetField) {
        Predicate<MiningMachine> p = walkable -> point.equals(walkable.getPoint());
        return machinesOnTargetField
                .stream()
                .noneMatch(p);
    }

    public Boolean transport(MiningMachine tidyUpRobot, List<MiningMachine> robotsInDestinationRoom) {
        if(tidyUpRobot.getFieldId().equals(this.sourceRoomId)
                && tidyUpRobot.getPoint().equals(this.sourcePoint)
                && isUnoccupied(this.destinationPoint, robotsInDestinationRoom)) {
            tidyUpRobot.setFieldId(this.destinationRoomId);
            tidyUpRobot.setPoint(this.destinationPoint);
            return true;
        } else {
            return false;
        }
    }

    public Connection(UUID _sourceRoomId, Point _sourcePoint, UUID _destinationRoomId, Point _destinationPoint) {
        this.id = UUID.randomUUID();
        this.sourcePoint = _sourcePoint;
        this.destinationRoomId = _destinationRoomId;
        this.destinationPoint = _destinationPoint;
        this.sourceRoomId = _sourceRoomId;
    }
}
