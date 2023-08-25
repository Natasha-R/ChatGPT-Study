package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Entity
public class Connection {
    @Id
    private UUID id;
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

    private Boolean isUnoccupied(Point point, List<TidyUpRobot> robotsInTargetRoom) {
        Predicate<TidyUpRobot> p = walkable -> point.equals(walkable.getPoint());
        return robotsInTargetRoom
                .stream()
                .noneMatch(p);
    }

    public Boolean transport(TidyUpRobot tidyUpRobot, List<TidyUpRobot> robotsInDestinationRoom) {
        if(tidyUpRobot.getRoomId().equals(this.sourceRoomId)
                && tidyUpRobot.getPoint().equals(this.sourcePoint)
                && isUnoccupied(this.destinationPoint, robotsInDestinationRoom)) {
            tidyUpRobot.setRoomId(this.destinationRoomId);
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
