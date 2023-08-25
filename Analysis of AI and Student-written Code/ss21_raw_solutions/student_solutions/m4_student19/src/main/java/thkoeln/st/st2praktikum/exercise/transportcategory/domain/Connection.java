package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
public class Connection {

    @Id
    private UUID id;

    private UUID sourceSpaceId;
    private UUID destinationSpaceId;

    @Embedded
    private Vector2D sourceVector2D;

    @Embedded
    private Vector2D destinationVector2D;

    public Connection() {}

    public Connection(UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2D) {
        this.id = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceVector2D = sourceVector2D;
        this.destinationVector2D = destinationVector2D;
    }
}
