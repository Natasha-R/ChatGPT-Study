package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.StringParser.TaskParser;
import thkoeln.st.st2praktikum.exercise.Exception.NegativeStepsInTaskException;

import javax.persistence.Embeddable;
import java.util.UUID;
import java.util.regex.MatchResult;

@Embeddable
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Task {
    protected TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;
    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) {
        TaskParser taskParser = new TaskParser();
        MatchResult taskStringMatchResult = taskParser.parse(taskString);

        this.setTaskTypeByString(taskStringMatchResult.group(1));
        String secondTaskParameter = taskStringMatchResult.group(2);

        switch (this.taskType)
        {
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                this.numberOfSteps = Integer.parseInt(secondTaskParameter);
                break;
            case TRANSPORT:
            case ENTER:
                this.gridId = UUID.fromString(secondTaskParameter);
                break;
        }
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;

        if(numberOfSteps < 0)
        {
            throw new NegativeStepsInTaskException();
        }

        this.numberOfSteps = numberOfSteps;
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

    protected void setTaskTypeByString(String taskTypeString)
    {
        switch (taskTypeString)
        {
            case "no":
                this.taskType = TaskType.NORTH;
                return;
            case "we":
                this.taskType = TaskType.WEST;
                return;
            case "so":
                this.taskType = TaskType.SOUTH;
                return;
            case "ea":
                this.taskType = TaskType.EAST;
                return;
            case "tr":
                this.taskType = TaskType.TRANSPORT;
                return;
            case "en":
                this.taskType = TaskType.ENTER;
                return;
            default:
                throw new RuntimeException("Invalid TaskType String");
        }
    }
}
