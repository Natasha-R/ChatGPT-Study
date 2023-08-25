package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.persistence.Embeddable;
import java.util.UUID;


public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps < 0) {
            throw new IllegalArgumentException("no negativ steps allowed");
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
        if (taskString.substring(1, 3).equals("no")) {
            //taskType = TaskType.NORTH;
            //numberOfSteps = Integer.parseInt(taskString.substring(4,5));

            return new Task(TaskType.NORTH, Integer.parseInt(taskString.substring(4,5)));
        }

        else if (taskString.substring(1, 3).equals("ea")) {
            //taskType = TaskType.EAST;
            //numberOfSteps = Integer.parseInt(taskString.substring(4,5));

            return new Task(TaskType.EAST, Integer.parseInt(taskString.substring(4,5)));
        }

        else if (taskString.substring(1, 3).equals("so")) {
            //taskType = TaskType.SOUTH;
            //numberOfSteps = Integer.parseInt(taskString.substring(4,5));

            return new Task(TaskType.SOUTH, Integer.parseInt(taskString.substring(4,5)));
        }

        else if (taskString.substring(1, 3).equals("we")) {
            //taskType = TaskType.WEST;
            //numberOfSteps = Integer.parseInt(taskString.substring(4,5));

            return new Task(TaskType.WEST, Integer.parseInt(taskString.substring(4,5)));
        }

        else if (taskString.substring(1, 3).equals("en") && taskString.length() == 41) {
            //taskType = TaskType.ENTER;
            //gridId = UUID.fromString(taskString.substring(4,40));

            return new Task(TaskType.ENTER, UUID.fromString(taskString.substring(4,40)));
        }

        else if (taskString.substring(1, 3).equals("tr") && taskString.length() == 41) {
            //taskType = TaskType.TRANSPORT;
            //gridId = UUID.fromString(taskString.substring(4,40));

            return new Task(TaskType.TRANSPORT, UUID.fromString(taskString.substring(4,40)));
        }

        else {
            throw new IllegalArgumentException("not the right format");
        }

    }
    
}
