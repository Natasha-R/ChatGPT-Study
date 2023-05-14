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

    private String sourceCoordinate;
    private String destinationCoordinate;

    public Connection(UUID sourceSpace, UUID destinationSpace, String sourceCoordinate, String destinationCoordinate){
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

}
