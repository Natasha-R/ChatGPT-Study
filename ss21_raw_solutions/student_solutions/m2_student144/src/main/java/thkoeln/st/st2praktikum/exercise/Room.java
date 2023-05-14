package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Room{

    private Integer height;
    private Integer width;
    private UUID roomId;

    public Room(Integer roomHeight, Integer roomWidth){
        width = roomWidth;
        height = roomHeight;
        roomId = UUID.randomUUID();

    }
    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public UUID getRoomId() {
        return roomId;
    }
}
