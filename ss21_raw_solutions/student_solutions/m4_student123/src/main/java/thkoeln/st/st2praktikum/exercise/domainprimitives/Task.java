package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Setter(AccessLevel.PROTECTED)
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps<0)throw new IllegalArgumentException("NumberofSteps cant be negative.");
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

    public static Task fromString(String taskString ) {
        String[] commandValue = taskString.split(",");
        Task newTask = new Task();
        if (taskString.matches("^\\[((so)|(no)|(we)|(ea)),\\s?[1-9]+\\]$")){
            switch (commandValue[0].substring(1)){
                case "no":
                    newTask.taskType = TaskType.NORTH;
                    break;
                case "so":
                    newTask.taskType  = TaskType.SOUTH;
                    break;
                case "ea":
                    newTask.taskType  = TaskType.EAST;
                    break;
                case "we":
                    newTask.taskType  = TaskType.WEST;
                    break;
            }
            newTask.numberOfSteps = Integer.parseInt(commandValue[1].substring(0,commandValue[1].length()-1));
            return newTask;
        }
        else if(taskString.matches("^\\[((en)|(tr)),\\s?[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}\\]$")){
            switch (commandValue[0].substring(1)){
                case "en":
                    newTask.taskType = TaskType.ENTER;
                    break;
                case "tr":
                    newTask.taskType = TaskType.TRANSPORT;
                    break;
            }
            newTask.gridId = UUID.fromString(commandValue[1].substring(0,commandValue[1].length()-1));
            return newTask;
        }
        else throw new IllegalArgumentException("Task konnte nicht gefunden werden.");
    }
    
}
