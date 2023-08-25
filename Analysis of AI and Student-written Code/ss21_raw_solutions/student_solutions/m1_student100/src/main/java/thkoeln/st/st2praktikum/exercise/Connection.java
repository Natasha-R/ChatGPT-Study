package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

public class Connection {
    UUID sourceFieldid;
    String sourceCoordinate;
    UUID destinationFieldid;
    String destinationCoordinate;
    UUID connectionid;
    int[] sourcecoordinateinarray;
    int[] destinationcoordinateinarray;



    public int[] transformsourcecoordinatestringinint (String sourcecoordinate) {
        sourcecoordinate = sourcecoordinate.replaceAll("[()]", "");
        int[] sourcecoordinates = Arrays.stream(sourcecoordinate.split(", *")).mapToInt(Integer::parseInt).toArray();
        return sourcecoordinates;
    }



    public Connection(UUID sourceFieldid, String sourceCoordinate, UUID destinationFieldid,
            String destinationCoordinate) {
        this.sourceFieldid = sourceFieldid;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldid = destinationFieldid;
        this.destinationCoordinate = destinationCoordinate;
        this.sourcecoordinateinarray = transformsourcecoordinatestringinint(sourceCoordinate);
        this.destinationcoordinateinarray = transformsourcecoordinatestringinint(destinationCoordinate);



    }
}
