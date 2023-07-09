package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private final UUID connectionID;
    private final UUID sourceFieldId;
    private final UUID destinationFieldID;
    private final Integer sourceX;
    private final Integer sourceY;
    private final Integer destinationX;
    private final Integer destinationY;

    public Connection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate){
        this.connectionID = UUID.randomUUID();
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldID = destinationFieldId;
        this.sourceX = Integer.parseInt(sourceCoordinate.substring(1,2));
        this.sourceY = Integer.parseInt(sourceCoordinate.substring(3,4));
        this.destinationX = Integer.parseInt(destinationCoordinate.substring(1,2));
        this.destinationY = Integer.parseInt(destinationCoordinate.substring(3,4));
    }

    public UUID getConnectionID() {
        return connectionID;
    }

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }

    public UUID getDestinationFieldID() {
        return destinationFieldID;
    }

    public Integer getSourceX() {
        return sourceX;
    }

    public Integer getSourceY() {
        return sourceY;
    }

    public Integer getDestinationX() {
        return destinationX;
    }

    public Integer getDestinationY() {
        return destinationY;
    }
}
