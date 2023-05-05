package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class Space {
    protected UUID spaceId;
    protected int height,width;
    protected List<Wall> wallList=new ArrayList<>();
    protected List<Connection> connectionList=new ArrayList<>();
    protected List<CleaningDevice> cleaningDeviceList=new ArrayList<>();

    public Space() {

    }

    public Space(int height, int width) {
        this.spaceId= UUID.randomUUID();
        this.height = height;
        this.width = width;

    }
}
