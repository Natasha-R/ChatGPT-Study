package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps < 0)
            throw new UnsupportedOperationException("invalid numer of steps");
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
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

    public static Task fromString(String taskString ) throws UnsupportedOperationException{
        try {
            if(!(taskString.startsWith("[")&&taskString.endsWith("]")&& StringUtils.countOccurrencesOf(taskString, ",") == 1))
                throw new UnsupportedOperationException("wrong format");

            String[] command = taskString.replace("[", "").replace("]", "").split(",");

            int numberOfSteps = 0;
            TaskType taskType;
            UUID gridId = null;
            switch (command[0]) {
                case "tr":
                    taskType = TaskType.TRANSPORT;
                    gridId = UUID.fromString(command[1]);
                    break;
                case "en":
                    taskType = TaskType.ENTER;
                    gridId = UUID.fromString(command[1]);
                    break;
                default:
                    taskType = generateDirectionTaskType(command[0]);
                    numberOfSteps = Integer.valueOf(command[1]);
                    break;
            }
            if(numberOfSteps < 0)
                throw new UnsupportedOperationException("invalid numer of steps");
            if(gridId != null)
                return new Task(taskType, gridId);
            else
                return new Task(taskType, numberOfSteps);
        }catch (Exception e){
            throw new UnsupportedOperationException("invalid Task");
        }
    }

    private static TaskType generateDirectionTaskType(String s){
        switch (s) {
            case "no":
                return TaskType.NORTH;
            case "ea":
                return TaskType.EAST;
            case "so":
                return TaskType.SOUTH;
            case "we":
                return TaskType.WEST;
            default:
                throw new UnsupportedOperationException("invalid Task");
        }
    }
    
}
