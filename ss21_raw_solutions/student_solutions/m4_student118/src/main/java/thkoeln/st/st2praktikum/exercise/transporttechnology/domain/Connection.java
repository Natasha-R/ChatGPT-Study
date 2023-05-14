package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Connection extends AbstractEntity {


    private UUID sourceSpace;
    private UUID destinationSpace;

    @Embedded
    private Coordinate sourceCoordinate;
    @Embedded
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceSpace, UUID destinationSpace, Coordinate sourceCoordinate, Coordinate destinationCoordinate){
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

}
