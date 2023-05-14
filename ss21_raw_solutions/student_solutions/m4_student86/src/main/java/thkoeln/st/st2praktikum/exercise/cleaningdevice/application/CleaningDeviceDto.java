package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CleaningDeviceDto {
    private String name;
    private Vector2D position;
    private Space space;

    //private UUID id;
}
