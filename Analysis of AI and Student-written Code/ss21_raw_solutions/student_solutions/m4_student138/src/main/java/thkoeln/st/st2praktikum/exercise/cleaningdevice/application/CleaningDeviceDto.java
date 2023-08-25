package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CleaningDeviceDto implements Serializable {
    private UUID uuid;
    private String name;

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
