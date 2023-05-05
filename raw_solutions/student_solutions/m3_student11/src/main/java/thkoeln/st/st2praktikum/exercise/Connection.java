package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.exceptions.ConnectionOutOfBoundsException;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Connection {

    @Id
    @Getter
    private final UUID ID = UUID.randomUUID();

    @ManyToOne
    @Getter
    private final Room source, dest;

    @Embedded
    @Getter
    private final Coordinate sourceCords, destCords;

    @ManyToOne
    private final TransportTechnology transportTechnology;

    @Transient
    private final TidyUpRobotService world;

    public Connection(TransportTechnology transportTechnology, Room source, Coordinate sourceCords, Room dest, Coordinate destCords, TidyUpRobotService world){
        this.source = source;
        this.dest = dest;
        this.sourceCords = sourceCords;
        this.destCords = destCords;
        this.world = world;
        this.transportTechnology = transportTechnology;
        if(this.checkConnectionOutOfBounds()) throw new ConnectionOutOfBoundsException();
    }
    protected Connection(){
        source = null;
        dest = null;
        sourceCords = null;
        destCords = null;
        transportTechnology = null;
        world = null;
    }

    public UUID getTransportTechnologyID(){return transportTechnology.getID();}

    public UUID getSourceRoomID(){
        return source.getID();
    }
    public UUID getDestRoomID(){
        return dest.getID();
    }

    private boolean checkConnectionOutOfBounds(){
        RoomRepository rooms = world.getRooms();

        for (Room room : rooms.findAll()){
            if(room.getID().equals(this.source.getID())) {
                if (room.checkOutOfBounds(this.sourceCords))
                    return true;
            }
            else if(room.getID().equals(this.dest.getID())){
                if(room.checkOutOfBounds(this.destCords))
                    return true;
            }
        }
        return false;
    }
}
