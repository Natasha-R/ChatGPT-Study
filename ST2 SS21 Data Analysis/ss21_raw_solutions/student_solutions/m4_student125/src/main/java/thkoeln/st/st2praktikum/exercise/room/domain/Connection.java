package thkoeln.st.st2praktikum.exercise.room.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Connection extends Field
{
    private UUID connectionId;
    private UUID sourceId;
    private UUID destinationId;
    private UUID transportTechnologyId;

    Coordinate sourceCoordinate;
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

    public UUID getConnectionId()
    {
        return connectionId;
    }

    public void setConnectionId(UUID connectionId)
    {
        this.connectionId = connectionId;
    }

    public UUID getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(UUID sourceId)
    {
        this.sourceId = sourceId;
    }

    public UUID getDestinationId()
    {
        return destinationId;
    }

    public void setDestinationId(UUID destinationId)
    {
        this.destinationId = destinationId;
    }

    public UUID getTransportTechnologyId()
    {
        return transportTechnologyId;
    }

    public void setTransportTechnologyId(UUID transportTechnologyId)
    {
        this.transportTechnologyId = transportTechnologyId;
    }

    public Coordinate getSourceCoordinate()
    {
        return sourceCoordinate;
    }

    public void setSourceCoordinate(Coordinate sourceCoordinate)
    {
        this.sourceCoordinate = sourceCoordinate;
    }

    public Coordinate getDestinationCoordinate()
    {
        return destinationCoordinate;
    }

    public void setDestinationCoordinate(Coordinate destinationCoordinate)
    {
        this.destinationCoordinate = destinationCoordinate;
    }
}
