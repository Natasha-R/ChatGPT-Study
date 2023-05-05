package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Connection extends AbstractEntity {

    @Getter
    private UUID sourceSpaceId;
    @Getter
    private Coordinate sourceCoordinate;
    @Getter
    private UUID destinationSpaceId;
    @Getter
    private Coordinate destinationCoordinate;



    public Connection (UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate){
        this.sourceSpaceId = sourceSpaceId;
        this.sourceCoordinate = sourceCoordinate;

        this.destinationSpaceId = destinationSpaceId;
        this.destinationCoordinate = destinationCoordinate;


    }


}
