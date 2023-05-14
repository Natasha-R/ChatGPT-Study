package thkoeln.st.st2praktikum.exercise.cleaningdevice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CleaningDeviceDto {

    private UUID cleaningDeviceId;
    private String name;
}
