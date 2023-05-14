package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

public class Connection {
    private UUID sourceFieldid;
    private Coordinate sourceCoordinate;
    private  UUID destinationFieldid;
    private Coordinate destinationCoordinate;
    private UUID connectionid;


    public UUID getSourceFieldid() {
        return sourceFieldid;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationFieldid() {
        return destinationFieldid;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getConnectionid() {
        return connectionid;
    }



    public int[] transformsourcecoordinatestringinint (String sourcecoordinate) {
        sourcecoordinate = sourcecoordinate.replaceAll("[()]", "");
        int[] sourcecoordinates = Arrays.stream(sourcecoordinate.split(", *")).mapToInt(Integer::parseInt).toArray();
        return sourcecoordinates;
    }



    public Connection(UUID sourceFieldid, Coordinate sourceCoordinate, UUID destinationFieldid,
                      Coordinate destinationCoordinate) {
        this.sourceFieldid = sourceFieldid;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldid = destinationFieldid;
        this.destinationCoordinate = destinationCoordinate;




    }
}
