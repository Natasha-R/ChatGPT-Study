package thkoeln.st.st2praktikum.exercise.tidyuprobot.application.dto;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;
}
