package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection
{
    @Id
    private UUID uuid;

    @OneToOne
    private Field source;
    @OneToOne
    private Field destination;

    @Embedded
    private Vector2D sourceCoordinate;
    @Embedded
    private Vector2D destinationCoordinate;

    @OneToOne
    private TransportCategory transportCategory;

    public Connection(TransportCategory transportCategory, Field source, Vector2D sourceCoordinate, Field destination, Vector2D destinationCoordinate)
    {
        uuid = UUID.randomUUID();

        this.transportCategory= transportCategory;

        this.source = source;
        this.sourceCoordinate = sourceCoordinate;
        this.destination = destination;
        this.destinationCoordinate = destinationCoordinate;
    }
}