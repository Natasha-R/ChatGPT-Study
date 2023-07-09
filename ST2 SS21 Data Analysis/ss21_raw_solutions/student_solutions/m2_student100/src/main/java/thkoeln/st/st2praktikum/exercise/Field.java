package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Field {
    private int height;
    private int width;
    private UUID fielduuid;
    private List<Wall> walllist = new ArrayList<Wall>();


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public UUID getFielduuid() {
        return fielduuid;
    }

    public List<Wall> getWalllist() {
        return walllist;
    }

    public Field(int height, int width, UUID fielduuid) {
        this.height = height;
        this.width = width;
        this.fielduuid = fielduuid;
    }
}
