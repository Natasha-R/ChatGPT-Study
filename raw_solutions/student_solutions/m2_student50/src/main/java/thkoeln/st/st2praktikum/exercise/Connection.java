package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Connection extends AbstractEntity{

    private UUID sourceRoom;
    private UUID destinationRoom;

    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceRoom, UUID destinationRoom, Coordinate sourceCoordinate, Coordinate destinationCoordinate){
        this.sourceRoom = sourceRoom;
        this.destinationRoom = destinationRoom;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

}
