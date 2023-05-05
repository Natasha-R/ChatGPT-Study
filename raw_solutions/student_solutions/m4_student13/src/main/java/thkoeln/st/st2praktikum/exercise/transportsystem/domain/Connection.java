package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.field.domain.Room;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection {
    @OneToOne
    private Room entryRoom;

    @OneToOne
    private Room exitRoom;

    @Id
    private UUID uuid;

    @OneToOne
    private TransportSystem transportSystem;

    public Connection(Room entryRoom, Room exitRoom, TransportSystem transportSystem){
        this.entryRoom = entryRoom;
        this.exitRoom = exitRoom;
        uuid = UUID.randomUUID();
        this.transportSystem = transportSystem;
    }


    public Room getEntryRoom() {
        return entryRoom;
    }
    public Room getExitRoom() {
        return exitRoom;
    }
    public UUID getId() {
        return uuid;
    }
}