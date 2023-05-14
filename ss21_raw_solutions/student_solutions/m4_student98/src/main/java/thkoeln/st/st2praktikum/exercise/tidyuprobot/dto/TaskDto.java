package thkoeln.st.st2praktikum.exercise.tidyuprobot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private UUID id;
    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;
}
