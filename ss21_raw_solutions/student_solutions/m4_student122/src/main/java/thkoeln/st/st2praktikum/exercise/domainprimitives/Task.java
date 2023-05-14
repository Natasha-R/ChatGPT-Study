package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Setter
@Getter
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(String taskString) {

        String[] taskArray = getTaskAsArray(taskString);

        switch (taskArray[0]){
            case "en":
                System.out.println(taskArray[1]);
                this.gridId = UUID.fromString(taskArray[1]);
                this.taskType = TaskType.ENTER;
                break;
            case "tr":
                this.gridId = UUID.fromString(taskArray[1]);
                this.taskType = TaskType.TRANSPORT;
                break;
            case "no":
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                this.taskType = TaskType.NORTH;
                break;
            case "so":
                System.out.println(this.numberOfSteps);
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                System.out.println(this.numberOfSteps);
                this.taskType = TaskType.SOUTH;
                break;
            case "we":
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                this.taskType = TaskType.WEST;
                break;
            case "ea":
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                this.taskType = TaskType.EAST;
                break;
            default:
                throw new RuntimeException();
        }
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps<0){
            throw new RuntimeException();
        }
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
        return new Task(taskString);
    }

    public String[] getTaskAsArray(String commandString){
        return commandString.replace("[","").replace("]","").split(",");
    }

}
