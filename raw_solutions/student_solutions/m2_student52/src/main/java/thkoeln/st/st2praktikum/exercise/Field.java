package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Field {
    @Getter private final ArrayList<Barrier> barriers = new ArrayList<>();

    // connection from this field to the field with the hashmap key
    @Getter private final HashMap<UUID, Connection> connectionsTo = new HashMap<>();

    // only stored to fill api requirements
    @Getter private final UUID id;

    public Field(UUID uuid, int height, int width){
        this.id = uuid;
        var leftB = new Barrier(0,0,0,height);
        var rightB = new Barrier(width,0,width,height);
        var downB = new Barrier(0,0,width,0);
        var upB = new Barrier(0,height,width,height);
        getBarriers().add(leftB); getBarriers().add(rightB);
        getBarriers().add(downB); getBarriers().add(upB);
    }
    public void addConnection(UUID destFieldID, Point src, Point dest){
        // maybe todo, check if coords are actually valid
        // this would however break the one-sided connection from mm to this.
        var c = new Connection(src, dest);
        getConnectionsTo().put(destFieldID, c);
    }
}
