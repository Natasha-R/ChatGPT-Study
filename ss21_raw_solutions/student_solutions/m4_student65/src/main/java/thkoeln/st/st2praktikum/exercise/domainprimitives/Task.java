package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.IllegalActionException;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Task {

    private TaskType taskType=TaskType.INVALID;
    private Integer numberOfSteps=0;
    private UUID gridId;
    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if(numberOfSteps < 0)
        {
            throw new IllegalActionException("Number of steps must be positive!");
        }
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
        TaskType type;
        UUID gritId;
        int index = taskString.indexOf("[");
        if(index != 0)
        {
            throw new IllegalActionException("Task Input has wrong format!");
        }
        String tasktype = taskString.substring(1,3);

        if (tasktype.equals("no")) {
            type = TaskType.NORTH;
        }
        else if (tasktype.equals("ea")) {
            type = TaskType.EAST;
        }
        else if (tasktype.equals("so")) {
            type = TaskType.SOUTH;
        }
        else if (tasktype.equals("we")) {
            type = TaskType.WEST;
        }
        else if (tasktype.equals("tr")) {
            type = TaskType.TRANSPORT;
        }
        else if (tasktype.equals("en")) {
            type = TaskType.ENTER;
        }
        else
        {
            throw new IllegalActionException("Task Input has wrong format!");
        }
        int indexOfExtra = taskString.lastIndexOf(',');
        if(indexOfExtra != 3)
        {
            throw new IllegalActionException("Task Input has wrong format!");
        }
        if(type == TaskType.TRANSPORT || type == TaskType.ENTER)
        {
            gritId = UUID.fromString(taskString.substring(indexOfExtra+1,indexOfExtra+37));
            return new Task(type,gritId);
        }
        else
        {
            int numberOfSteps = Obstacle.getNumberAt(taskString,indexOfExtra + 1);
            return new Task(type,numberOfSteps);
            //numberOfSteps = Integer.valueOf(String.valueOf(taskString.charAt(indexOfExtra + 1)));
        }
    }
    
}
