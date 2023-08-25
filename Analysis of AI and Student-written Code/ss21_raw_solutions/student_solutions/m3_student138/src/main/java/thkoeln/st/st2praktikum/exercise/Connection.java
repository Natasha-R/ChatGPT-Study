package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Connection {
    @Id
    private UUID uuid;
    private UUID sourceSpaceId;
    //@Embedded
    private Vector2D sourceVector;
    private UUID destinationSpaceId;
    //@Embedded
    private Vector2D destinationVector;
    private UUID transportCategory;

    public UUID getTransportCategory() {
        return transportCategory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getSourceSpaceId() {
        return sourceSpaceId;
    }

    public Vector2D getSourceVector() {
        return sourceVector;
    }

    public Vector2D getDestinationVector() {
        return destinationVector;
    }

    public UUID getDestinationSpaceId() {
        return destinationSpaceId;
    }

    public Connection(UUID transportCategory, UUID sourceSpaceId, int sourceCoordinateX, int sourceCoordinateY, UUID destinationSpaceId, int destinationCoordinateX, int destinationCoordinateY)
    {
        uuid = UUID.randomUUID();
        this.transportCategory = transportCategory;
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;

        this.sourceVector =  new Vector2D(sourceCoordinateX, sourceCoordinateY);

        this.destinationVector =  new Vector2D(destinationCoordinateX, destinationCoordinateY);
    }

    public Connection(UUID transportCategory, UUID sourceSpaceId, Vector2D source, UUID destinationSpaceId, Vector2D destination)
    {
        uuid = UUID.randomUUID();
        this.transportCategory = transportCategory;
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;

        this.sourceVector =  source;

        this.destinationVector =  destination;
    }
}
