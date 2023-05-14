package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TidyUpRobotDto {
    private String name;
    private Vector2D currentPosition;
    private UUID currentRoom;
    private boolean isPlaced;
}
