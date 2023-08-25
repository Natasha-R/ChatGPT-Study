package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Unique;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection implements Unique {

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDestinationSpaceId() {
        return destinationSpaceId;
    }

    public UUID getSourceSpaceId() {
        return sourceSpaceId;
    }

    @Id
    private UUID id;

    private UUID sourceSpaceId;

    @Embedded
    private Coordinate sourceCoordinate;

    private UUID destinationSpaceId;
    @Embedded
    private Coordinate destinationCoordinate;


    public Connection(Space sourceSpace, Coordinate sourceCoordinate, Space destinationSpace, Coordinate destinationCoordinate){

        if(sourceSpace.inBounds(sourceCoordinate) && destinationSpace.inBounds(destinationCoordinate)) {
            this.id = UUID.randomUUID();
            this.sourceSpaceId = sourceSpace.getId();
            this.destinationSpaceId = destinationSpace.getId();


            this.sourceCoordinate = sourceCoordinate;
            this.destinationCoordinate = destinationCoordinate;
        }else throw new RuntimeException("Coordinate out of Bounds");
    }


}
