package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import thkoeln.st.st2praktikum.StringParser.PointParser;
import thkoeln.st.st2praktikum.StringParser.TaskParser;
import thkoeln.st.st2praktikum.exercise.domainprimitives.exception.NegativeStepsInTaskException;

import javax.persistence.Embeddable;
import java.util.UUID;
import java.util.regex.MatchResult;

@Embeddable
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps < 1)
        {
            throw new NegativeStepsInTaskException();
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

    public static Task fromString(String taskString) {
        TaskParser taskParser = new TaskParser();
        MatchResult taskMatchResult = taskParser.parse(taskString);

        TaskType taskType = TaskType.fromString(taskMatchResult.group(1));
        String secondTaskParameter = taskMatchResult.group(2);

        switch (taskType)
        {
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                int numberOfSteps = Integer.parseInt(secondTaskParameter);

                return new Task(taskType, numberOfSteps);
            case TRANSPORT:
            case ENTER:
                UUID gridUUID = UUID.fromString(secondTaskParameter);

                return new Task(taskType, gridUUID);
        }

        throw new RuntimeException("@TODO");
    }
    
}
