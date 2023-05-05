package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Setter
@Getter
@Embeddable
@EqualsAndHashCode
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if(this.numberOfSteps < 0) throw new IllegalArgumentException();
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }
    
    public TaskType getTaskType() {
        return taskType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

    public static Task fromString(String taskString ){
        TaskType taskType = getTaskTypeFromString(taskString);
        String secondPartOfTaskString = taskString.substring(taskString.lastIndexOf(',') + 1, taskString.length() - 1);
        if(taskType.equals(TaskType.ENTER) || taskType.equals(TaskType.TRANSPORT)){
            UUID gridId = UUID.fromString(secondPartOfTaskString);
            return new Task(taskType, gridId);
        }
        else{
            int numberOfSteps = Integer.parseInt(secondPartOfTaskString);
            if(numberOfSteps < 0) throw new IllegalArgumentException();
            return new Task(taskType, numberOfSteps);
        }
    }

    private static TaskType getTaskTypeFromString(String taskString){
        switch(taskString.substring(1,taskString.lastIndexOf(','))){
            case "en":
                return TaskType.ENTER;
            case "tr":
                return TaskType.TRANSPORT;
            case "no":
                return TaskType.NORTH;
            case "so":
                return TaskType.SOUTH;
            case "ea":
                return TaskType.EAST;
            case "we":
                return TaskType.WEST;
            default:
                throw new IllegalArgumentException();
        }
    }
}
