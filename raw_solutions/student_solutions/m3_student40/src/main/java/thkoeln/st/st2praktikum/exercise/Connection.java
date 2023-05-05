package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Connection extends Tile {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Room destinationRoom;
    @Embedded
    private Coordinate destinationPosition;
    @ManyToOne
    private TransportSystem transportSystem;

    public Connection(Room destinationRoom, Coordinate destinationPosition, TransportSystem transportSystem ){
        this.destinationRoom = destinationRoom;
        this.destinationPosition = destinationPosition;
        this.transportSystem = transportSystem;
    }

    public boolean canUse(UUID destinationID){
        return destinationRoom.getId().equals(destinationID);
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isConnection() {
        return true;
    }
}
