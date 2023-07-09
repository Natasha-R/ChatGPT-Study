package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class Connector {
    @Id
    private UUID connectorid;

    private UUID spacedestination;
    @Embedded
    private Point location1;
    @Embedded
    private Point location2;

    public Connector(Point sourcePoint, UUID destinationSpaceId, Point destinationPoint){
        connectorid = UUID.randomUUID();
        location1 = sourcePoint;
        location2 = destinationPoint;
        spacedestination = destinationSpaceId;
    }

    public Connector(String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate){
        connectorid=UUID.randomUUID();
        sourceCoordinate=sourceCoordinate.substring(1,sourceCoordinate.length()-1);
        destinationCoordinate=destinationCoordinate.substring(1,destinationCoordinate.length()-1);
        location1= Point.fromString(sourceCoordinate);
        location2= Point.fromString(destinationCoordinate);
        spacedestination=destinationSpaceId;
    }

    public Point getLocation1() {
        return location1;
    }

    public UUID getSpacedestination() {
        return spacedestination;
    }

    public Point getLocation2() {
        return location2;
    }

    public UUID getId(){
        return connectorid;
    }
}
