package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    private int height;
    private int width;
    private UUID roomUUID;
    private List<Barrier> barrierList = new ArrayList<Barrier>();


    public Room(int height, int width, UUID roomUUID) {
        this.height = height;
        this.width = width;
        this.roomUUID = roomUUID;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public UUID getRoomUUID() {
        return roomUUID;
    }

    public List<Barrier> getBarrierList() {
        return barrierList;
    }

}
