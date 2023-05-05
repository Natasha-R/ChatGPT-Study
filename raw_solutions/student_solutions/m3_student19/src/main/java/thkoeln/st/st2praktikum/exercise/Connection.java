package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class Connection {

    @Id
    private UUID id;

    private UUID sourceSpaceId;
    private UUID destinationSpaceId;

    @ManyToOne
    private TransportCategory transportCategory;

    @Embedded
    private Vector2D sourceVector2D;

    @Embedded
    private Vector2D destinationVector2D;

    public Connection() {}

    public Connection(UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2D) {
        this.id = UUID.randomUUID();
//        this.transportCategory = transportCategory;
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceVector2D = sourceVector2D;
        this.destinationVector2D = destinationVector2D;
    }
}
