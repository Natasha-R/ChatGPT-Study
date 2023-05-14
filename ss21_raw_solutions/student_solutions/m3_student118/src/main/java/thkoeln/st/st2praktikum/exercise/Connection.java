package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import javax.persistence.*;
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

//    @ManyToOne
//    private TransportTechnology transportTechnology;

    public Connection(UUID sourceSpace, UUID destinationSpace, Coordinate sourceCoordinate, Coordinate destinationCoordinate){
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

}
