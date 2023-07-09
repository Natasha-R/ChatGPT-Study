package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CleaningDevice {
 private    UUID cleaningDeviceId;
    private   UUID spaceId;
    private  Point position;
    private  String name;
    public CleaningDevice(String name) {
        this.cleaningDeviceId = UUID.randomUUID();
        this.name=name;
    }
public void move(String direction){
    switch (direction) {
        case "no": position.movNorth();break;
        case "so":  position.movSouth();break;
        case "ea":  position.movEast();break;
        case "we":  position.movWest();
    }
}


}
