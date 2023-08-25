package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Field {
    int height;
    int width;
    UUID fielduuid;
    List<Wall> walllist = new ArrayList<Wall>();



    public Field(int height, int width, UUID fielduuid) {
        this.height = height;
        this.width = width;
        this.fielduuid = fielduuid;
    }
}
