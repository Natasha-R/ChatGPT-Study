package thkoeln.st.st2praktikum.exercise.map.connection;

import lombok.Data;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.map.Locatable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
public class Connection {
    @Id
    UUID connectionID;
    @Embedded
    Locatable sourcePoint;
    @Embedded
    Locatable destinationPoint;
    @Embedded
    TransportCategory transportCategory;

    public Connection(TransportCategory transportCategory, Locatable sourcePoint, Locatable destinationPoint) {
        this.setConnectionID(UUID.randomUUID());
        this.setSourcePoint(sourcePoint);
        this.setDestinationPoint(destinationPoint);
        this.transportCategory = transportCategory;
        sourcePoint.getConnections().put( OrderType.TRANSPORT, destinationPoint );
    }

}