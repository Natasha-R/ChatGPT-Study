package thkoeln.st.st2praktikum.exercise.Connection;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Room.Room;

import java.util.UUID;


@Getter
@Setter
public class Connection implements ConnectionPacket{
    private UUID id;

    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;
    private Room destinationRoom;
    private Room sourceRoom;

    public Connection(Room sourceRoom, Coordinate sourceCoordinate,
                      Room destinationRoom, Coordinate destinationCoordinate){

        this.id = UUID.randomUUID();
        this.sourceRoom = sourceRoom;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoom = destinationRoom;
        this.destinationCoordinate = destinationCoordinate;
    }

    @Override
    public Boolean validateConnection(Room sourceRoom, Coordinate sourceCoordinate, Room destinationRoom){
        return (this.sourceRoom.equals(sourceRoom) &&
                this.sourceCoordinate.equals(sourceCoordinate) &&
                this.destinationRoom.equals(destinationRoom));
    }

    @Override
    public Coordinate getDestinationCoordinate(){
        return destinationCoordinate;
    }
}
