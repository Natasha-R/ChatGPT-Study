package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private UUID connectionId;
    private UUID sourceFieldId;
    private int sourceCoordinateX;
    private int sourceCoordinateY;
    private UUID destinationFieldId;
    private int destinationCoordinateX;
    private int destinationCoordinateY;

    public Connection(UUID connectionId, UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        this.connectionId = connectionId;
        this.sourceFieldId = sourceFieldId;
        String cleanedSourceCoordinateString =
                sourceCoordinate.replaceAll("\\(","").replaceAll("\\)","");
        String[] sourceCoordinateArray = cleanedSourceCoordinateString.split(",");
        this.sourceCoordinateX = Integer.parseInt(sourceCoordinateArray[0]);
        this.sourceCoordinateY = Integer.parseInt(sourceCoordinateArray[1]);
        this.destinationFieldId = destinationFieldId;
        String cleanedDestinationCoordinateString =
                destinationCoordinate.replaceAll("\\(","").replaceAll("\\)","");
        String[] destinationCoordinateArray = cleanedDestinationCoordinateString.split(",");
        this.destinationCoordinateX = Integer.parseInt(destinationCoordinateArray[0]);
        this.destinationCoordinateY = Integer.parseInt(destinationCoordinateArray[1]);
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }

    public int getSourceCoordinateX() {
        return sourceCoordinateX;
    }

    public int getSourceCoordinateY() {
        return sourceCoordinateY;
    }

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }

    public int getDestinationCoordinateX() {
        return destinationCoordinateX;
    }

    public int getDestinationCoordinateY() {
        return destinationCoordinateY;
    }
}
