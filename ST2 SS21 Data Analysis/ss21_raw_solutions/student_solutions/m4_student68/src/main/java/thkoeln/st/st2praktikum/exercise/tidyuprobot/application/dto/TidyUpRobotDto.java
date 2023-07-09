package thkoeln.st.st2praktikum.exercise.tidyuprobot.application.dto;


import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TidyUpRobotDto {
    private UUID robotID;
    private String name;
}


