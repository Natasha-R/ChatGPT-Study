package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.UUID;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Connection
{
    @OneToOne
    private Room sourceRoom;
    @OneToOne
    private Room destinationRoom;
    private Vector2D sourceCoordinate;
    private Vector2D destinationCoordinate;
    private TransportCategory transportCategory;

    public Connection(TransportCategory transportCategory, Room sourceRoom, Vector2D sourceCoordinate, Room destinationRoom, Vector2D destinationCoordinate)
    {
        this.transportCategory = transportCategory;
        this.sourceRoom = sourceRoom;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoom = destinationRoom;
        this.destinationCoordinate = destinationCoordinate;
    }


    public Room getSourceRoom()
    {
        return sourceRoom;
    }

    public Room getDestinationRoom()
    {
        return destinationRoom;
    }

    public Vector2D getDestinationCoordinate()
    {
        return destinationCoordinate;
    }

    public TransportCategory getTransportCategory() {
        return transportCategory;
    }
}
