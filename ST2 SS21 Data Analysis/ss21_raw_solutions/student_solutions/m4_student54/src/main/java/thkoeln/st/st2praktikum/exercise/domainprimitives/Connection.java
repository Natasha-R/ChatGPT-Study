package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class Connection
{

    private UUID sourceRoom;
    private UUID destinationRoom;
    private Vector2D sourceCoordinate;
    private Vector2D destinationCoordinate;

    private UUID transportCategory;


    public Connection(UUID transportCategory, UUID sourceRoom, Vector2D sourceCoordinate, UUID destinationRoom, Vector2D destinationCoordinate)
    {
        this.transportCategory = transportCategory;
        this.sourceRoom = sourceRoom;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoom = destinationRoom;
        this.destinationCoordinate = destinationCoordinate;
    }


    public UUID getSourceRoom()
    {
        return sourceRoom;
    }

    public void setSourceRoom(UUID sourceRoom) {
        this.sourceRoom = sourceRoom;
    }

    public void setDestinationRoom(UUID destinationRoom) {
        this.destinationRoom = destinationRoom;
    }

    public void setSourceCoordinate(Vector2D sourceCoordinate) {
        this.sourceCoordinate = sourceCoordinate;
    }

    public void setDestinationCoordinate(Vector2D destinationCoordinate) {
        this.destinationCoordinate = destinationCoordinate;
    }

    public void setTransportCategory(UUID transportCategory) {
        this.transportCategory = transportCategory;
    }

    public UUID getDestinationRoom()
    {
        return destinationRoom;
    }

    public Vector2D getDestinationCoordinate()
    {
        return destinationCoordinate;
    }

    public UUID getTransportCategory() {
        return transportCategory;
    }
}
