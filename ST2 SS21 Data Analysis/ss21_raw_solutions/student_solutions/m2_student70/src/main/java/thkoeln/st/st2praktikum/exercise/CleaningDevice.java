package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CleaningDevice {

    UUID cleaningDeviceId;
    UUID spaceId;
    Point position;
    String name;
    public CleaningDevice(String name) {
        this.cleaningDeviceId = UUID.randomUUID();
        this.name=name;
    }



}
