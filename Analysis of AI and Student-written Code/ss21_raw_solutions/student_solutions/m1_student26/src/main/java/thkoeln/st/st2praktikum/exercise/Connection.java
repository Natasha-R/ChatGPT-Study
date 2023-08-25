package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Connection extends AbstractEntity {

    @Getter
    private UUID sourceSpaceId;
    @Getter
    private String sourceCoordinate;
    @Getter
    private UUID destinationSpaceId;
    @Getter
    private String destinationCoordinate;



    public Connection (UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate){
        this.sourceSpaceId = sourceSpaceId;
        this.sourceCoordinate = sourceCoordinate;

        this.destinationSpaceId = destinationSpaceId;
        this.destinationCoordinate = destinationCoordinate;


    }


}
