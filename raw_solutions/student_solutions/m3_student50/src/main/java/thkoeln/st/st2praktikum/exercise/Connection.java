package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Connection extends AbstractEntity{

    private UUID sourceRoom;
    private UUID destinationRoom;
  //  @ManyToOne
  //    private TransportSystem transportSystem;

    @Embedded
    private Coordinate sourceCoordinate;
    @Embedded
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceRoom, UUID destinationRoom, Coordinate sourceCoordinate, Coordinate destinationCoordinate){
        this.sourceRoom = sourceRoom;
        this.destinationRoom = destinationRoom;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

}
