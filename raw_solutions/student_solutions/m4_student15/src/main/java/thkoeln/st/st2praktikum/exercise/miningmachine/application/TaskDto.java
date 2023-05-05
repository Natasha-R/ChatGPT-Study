package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
