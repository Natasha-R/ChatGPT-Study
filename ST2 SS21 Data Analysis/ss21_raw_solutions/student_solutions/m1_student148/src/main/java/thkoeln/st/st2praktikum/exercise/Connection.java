package thkoeln.st.st2praktikum.exercise;

import java.awt.*;
import java.util.UUID;

public class Connection implements DeStringable {
    private UUID connectionId;
    private UUID sourceFieldId, destinationFieldId;
    private Point sourceCoordinate;
    private Point destinationCoordinate;

    public Connection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public Connection() {
    }

    public UUID getId() {
        return this.connectionId;
    }

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }

    public Point getSourceCoordinate() {
        return this.sourceCoordinate;
    }

    public Point getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    @Override
    public int[] deString(String connectionString) {
        String connectionUnStrung = connectionString.replace("(", "");
        connectionUnStrung = connectionUnStrung.replace(")", "");
        String[] connectionArray = connectionUnStrung.split(",");
        int[] pointArray = new int[connectionArray.length];
        for (int i = 0; i < connectionArray.length; i++) {
            pointArray[i] = Integer.parseInt(connectionArray[i].trim());
        }
        return (pointArray);
    }
}