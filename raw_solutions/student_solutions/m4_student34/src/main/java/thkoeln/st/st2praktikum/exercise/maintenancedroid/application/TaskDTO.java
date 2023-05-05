package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class TaskDTO {
    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;
}
