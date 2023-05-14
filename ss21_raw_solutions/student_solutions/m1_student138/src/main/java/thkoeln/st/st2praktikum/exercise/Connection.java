package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID uuid;
    public UUID getUuid() {
        return uuid;
    }

    private Space sourceSpace;
    public Space getSourceSpace() {
        return sourceSpace;
    }

    private Integer sourceCoordinateX;
    public Integer getSourceCoordinateX() {
        return sourceCoordinateX;
    }

    private Integer sourceCoordinateY;
    public Integer getSourceCoordinateY() {
        return sourceCoordinateY;
    }

    private Space destinationSpace;
    public Space getDestinationSpace() { return destinationSpace; }

    private Integer destinationCoordinateX;
    public int getDestinationCoordinateX() {
        return destinationCoordinateX;
    }

    private Integer destinationCoordinateY;
    public int getDestinationCoordinateY() {
        return destinationCoordinateY;
    }


    public Connection(Space sourceSpace, int sourceCoordinateX, int sourceCoordinateY, Space destinationSpace, int destinationCoordinateX, int destinationCoordinateY)
    {
        uuid = UUID.randomUUID();
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;

        this.sourceCoordinateX = sourceCoordinateX;
        this.sourceCoordinateY = sourceCoordinateY;

        this.destinationCoordinateX = destinationCoordinateX;
        this.destinationCoordinateY = destinationCoordinateY;
    }

}
