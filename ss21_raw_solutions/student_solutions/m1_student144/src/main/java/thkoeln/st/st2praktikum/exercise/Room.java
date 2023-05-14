package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Room{

    protected Integer height;
    protected Integer width;
    protected UUID roomId;

    public Room(Integer roomHeight, Integer roomWidth){
        width = roomWidth;
        height = roomHeight;
        roomId = UUID.randomUUID();

    }
}
