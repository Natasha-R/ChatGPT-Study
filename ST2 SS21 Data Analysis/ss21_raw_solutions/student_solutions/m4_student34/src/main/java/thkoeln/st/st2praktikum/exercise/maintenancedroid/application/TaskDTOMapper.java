package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

@Component
public class TaskDTOMapper {

    public TaskDTO mapToDTO(Task task) {
        return new TaskDTO(task.getTaskType(), task.getNumberOfSteps(), task.getGridId());
    }

    public Task mapToValueObject(TaskDTO taskDTO) {
        if(taskDTO.getNumberOfSteps() != null) {
            return new Task(taskDTO.getTaskType(), taskDTO.getNumberOfSteps());
        } else if(taskDTO.getGridId() != null) {
            return new Task(taskDTO.getTaskType(), taskDTO.getGridId());
        }
        throw new IllegalArgumentException();
    }
}
