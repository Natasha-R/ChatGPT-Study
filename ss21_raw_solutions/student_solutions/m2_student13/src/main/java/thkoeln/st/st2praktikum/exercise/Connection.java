package thkoeln.st.st2praktikum.exercise;
import java.util.UUID;

public class Connection {
    private Room entryRoom;
    private Room exitRoom;
    private UUID uuid;

    public Connection(Room entryRoom, Room exitRoom){
        this.entryRoom = entryRoom;
        this.exitRoom = exitRoom;
        uuid = UUID.randomUUID();
    }


    public Room getEntryRoom() {
        return entryRoom;
    }
    public Room getExitRoom() {
        return exitRoom;
    }
    public UUID getUuid() {
        return uuid;
    }
}
