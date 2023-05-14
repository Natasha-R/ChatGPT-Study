package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import java.util.UUID;

@NoArgsConstructor
public class Connection extends AbstractEntity {

    @Getter
    private UUID sourceSpaceId;
    @Getter
    private Coordinate sourceCoordinate;
    @Getter
    private UUID destinationSpaceId;
    @Getter
    private Coordinate destinationCoordinate;



    public Connection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate){
        this.sourceSpaceId = sourceSpaceId;
        this.sourceCoordinate = sourceCoordinate;

        this.destinationSpaceId = destinationSpaceId;
        this.destinationCoordinate = destinationCoordinate;


    }


}
