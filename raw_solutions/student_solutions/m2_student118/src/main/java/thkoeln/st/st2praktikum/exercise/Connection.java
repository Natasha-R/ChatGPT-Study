package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import java.util.UUID;

@Getter
@Setter
public class Connection extends AbstractEntity {

    private UUID sourceSpace;
    private UUID destinationSpace;

    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceSpace, UUID destinationSpace, Coordinate sourceCoordinate, Coordinate destinationCoordinate){
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

}
