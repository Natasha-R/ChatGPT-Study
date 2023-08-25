package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection extends AbstractEntity {

    private UUID sourceSpaceId;

    private UUID destinationSpaceId;

    private String sourceSpaceCoordinate;

    private String destinationSpaceCoordinate;

    public Connection(UUID sourceSpaceId, UUID destinationSpaceId, String sourceSpaceCoordinate, String destinationSpaceCoordinate) {
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceSpaceCoordinate = sourceSpaceCoordinate;
        this.destinationSpaceCoordinate = destinationSpaceCoordinate;
    }

    public UUID getSourceSpaceId() {
        return sourceSpaceId;
    }

    public UUID getDestinationSpaceId() {
        return destinationSpaceId;
    }

    public String getSourceSpaceCoordinate() {
        return sourceSpaceCoordinate;
    }

    public String getDestinationSpaceCoordinate() {
        return destinationSpaceCoordinate;
    }

}
