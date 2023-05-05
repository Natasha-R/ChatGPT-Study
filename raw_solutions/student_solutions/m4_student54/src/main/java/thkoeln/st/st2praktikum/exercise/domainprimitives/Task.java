package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    protected Task() {}

    public Task(TaskType taskType, Integer numberOfSteps)
    {
        if(numberOfSteps < 0)
        {
            throw new IllegalArgumentException("NumberOfSteps must not be negative!");
        }

        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
    }

    public Task(TaskType taskType, UUID gridId)
    {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public static Task fromString(String taskString)
    {
        Validator validator = new Validator();

        if(!validator.isCommandString(taskString))
        {
            throw new IllegalArgumentException("This commandstring is not valid!");
        }

        switch(validator.getTaskString())
        {
            case "no":
                return new Task(TaskType.NORTH, Integer.parseInt(validator.getTaskParameter()));
            case "ea":
                return new Task(TaskType.EAST, Integer.parseInt(validator.getTaskParameter()));
            case "so":
                return new Task(TaskType.SOUTH, Integer.parseInt(validator.getTaskParameter()));
            case "we":
                return new Task(TaskType.WEST, Integer.parseInt(validator.getTaskParameter()));
            case "tr":
                return new Task(TaskType.TRANSPORT, UUID.fromString(validator.getTaskParameter()));
            case "en":
                return new Task(TaskType.ENTER, UUID.fromString(validator.getTaskParameter()));
            default:
                throw new IllegalArgumentException("This command is not supported!");
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

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setNumberOfSteps(Integer numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public void setGridId(UUID gridId) {
        this.gridId = gridId;
    }
}
