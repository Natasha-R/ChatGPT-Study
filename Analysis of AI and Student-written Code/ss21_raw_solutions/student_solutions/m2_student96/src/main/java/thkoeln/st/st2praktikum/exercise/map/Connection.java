package thkoeln.st.st2praktikum.exercise.map;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.UUID;


@Data
public class Connection {
    UUID connectionID;
    Point sourcePoint;
    Point destinationPoint;

    public Connection( Point sourcePoint, Point destinationPoint ) {
        this.setConnectionID(UUID.randomUUID());
        this.setSourcePoint(sourcePoint);
        this.setDestinationPoint(destinationPoint);
        sourcePoint.getConnections().put( OrderType.TRANSPORT, destinationPoint );
    }

}