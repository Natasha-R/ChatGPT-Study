package thkoeln.st.st2praktikum.exercise.Connection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Room.Room;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Connection implements Transportable{

    @Getter
    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne
    private TransportCategory transportCategory;

    @Embedded
    private Coordinate sourceCoordinate;
    @Embedded
    private Coordinate destinationCoordinate;

    @OneToOne
    private Room destinationRoom;
    @OneToOne
    private Room sourceRoom;

    public Connection(TransportCategory transportCategory, Room sourceRoom, Coordinate sourceCoordinate,
                      Room destinationRoom, Coordinate destinationCoordinate){

        this.transportCategory = transportCategory;
        this.sourceRoom = sourceRoom;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoom = destinationRoom;
        this.destinationCoordinate = destinationCoordinate;
    }

    @Override
    public Boolean validateConnection(Coordinate sourceCoordinate, Room destinationRoom){
        return (this.sourceCoordinate.equals(sourceCoordinate) &&
                this.destinationRoom.equals(destinationRoom));
    }

    @Override
    public Coordinate getDestinationCoordinate(){
        return destinationCoordinate;
    }
}
