package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Connection extends Barrier
{
    UUID connectionId;

    UUID sourceId, destinationId, transportTechnologyId;

    @OneToOne
    Coordinate sourceCoordinate;
    @OneToOne
    Coordinate destinationCoordinate;

    public Connection(UUID transportTechnology, UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate)
    {
        this.connectionId = UUID.randomUUID();
        this.transportTechnologyId = transportTechnology;
        this.sourceId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }
}
