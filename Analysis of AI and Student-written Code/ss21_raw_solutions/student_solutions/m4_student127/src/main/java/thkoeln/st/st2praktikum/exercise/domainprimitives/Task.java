package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.customexceptions.InvalidStringFormatException;
import thkoeln.st.st2praktikum.exercise.customexceptions.NegativeStepNumberException;

import javax.persistence.Embeddable;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Task {
    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    private void checkIfTaskStringIsValid(String taskString) {
        Pattern pattern = Pattern.compile("^\\[[(no|ea|so|we|en|tr)]+,[a-z0-9\\-]+]$");
        Matcher matcher = pattern.matcher(taskString);
        if (!matcher.matches()) {
            throw new InvalidStringFormatException("\"" + taskString + "\" is not a valid taskString in Task().");
        }
    }

    protected Task() { }
    public Task(String taskString) {
        checkIfTaskStringIsValid(taskString);
        StringTokenizer st = new StringTokenizer(taskString, "[],");

        switch (st.nextToken()) {
            case "tr":
                this.taskType = TaskType.TRANSPORT;
                this.gridId = UUID.fromString(st.nextToken());
                break;
            case "en":
                this.taskType = TaskType.ENTER;
                this.gridId = UUID.fromString(st.nextToken());
                break;
            case "no":
                this.taskType = TaskType.NORTH;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            case "we":
                this.taskType = TaskType.WEST;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            case "so":
                this.taskType = TaskType.SOUTH;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            case "ea":
                this.taskType = TaskType.EAST;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            default:
                throw new InvalidStringFormatException("This means that checkIfTaskStringIsValid() failed to catch an invalid string: " + taskString);
        }
        if (this.numberOfSteps != null && this.numberOfSteps < 0) {
            throw new NegativeStepNumberException("\"" + taskString + "\" included a negative number of steps.");
        }
    }
    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps < 0) {
            throw new NegativeStepNumberException(numberOfSteps + " was given but numberOfSteps cannot be negative in Task().");
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
    
}
