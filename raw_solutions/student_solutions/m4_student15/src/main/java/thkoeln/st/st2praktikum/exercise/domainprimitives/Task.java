package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps >= 0) {
            this.taskType = taskType;
            this.numberOfSteps = numberOfSteps;
        }else{
            throw new RuntimeException("numberOfSteps should be positive");
        }


    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
        
    }

    public Task(TaskType taskType, Integer numberOfSteps, UUID gridId) {
        if (numberOfSteps >= 0) {
            this.taskType = taskType;
            this.numberOfSteps = numberOfSteps;
            this.gridId = gridId;
        } else {
            throw new RuntimeException("numberOfSteps should be positive");
        }
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
        int steps = 0;
        UUID grid = null;

        if(taskString.matches("\\[\\D{2}\\,.*")){
            String[] parts = taskString.split(",");
            String task = parts[0].replaceAll("[^a-z]", "");
            String secondPart = parts[1];
            if(task.equals("no") || task.equals("ea") || task.equals("so") || task.equals("we")){
                if(secondPart.matches("\\d+\\]")){
                    steps = Integer.parseInt(secondPart.replaceAll("[^0-9]", ""));
                    if(task.equals("no")){
                        return new Task(TaskType.NORTH, steps);
                    }else if(task.equals("ea")){
                        return new Task(TaskType.EAST, steps);
                    }
                    else if(task.equals("so")){
                        return new Task(TaskType.SOUTH, steps);
                    }
                    else if(task.equals("we")){
                        return new Task(TaskType.WEST, steps);
                    }
                }else{
                    throw new RuntimeException("Wrong Syntax");
                }
            }else if(task.equals("tr") || task.equals("en")){
                if(secondPart.matches("\\w{8}\\-\\w{4}\\-\\w{4}\\-\\w{4}\\-\\w{12}\\]")){
                    grid = UUID.fromString(secondPart.replaceAll("]", ""));
                    if(task.equals("tr")){
                        return new Task(TaskType.TRANSPORT, grid);
                    }else if(task.equals("en")){
                        return  new Task(TaskType.ENTER, grid);
                    }
                }else{
                    throw new RuntimeException("Wrong Syntax");
                }
            }else{
                throw new RuntimeException("Die Aufgabe gibt es nicht");
            }
        }else{
            throw new RuntimeException("Wrong Syntax");
        }
        return null;
    }
    
}
