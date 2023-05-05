package thkoeln.st.st2praktikum.exercise.cleaningdevice.dto;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import java.util.UUID;

@Data
public class TaskDto {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

}
