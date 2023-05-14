package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


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
    public Task(String taskString)
    {
        Validator validator = new Validator();

        if(!validator.isCommandString(taskString))
        {
            throw new IllegalArgumentException("This commandstring is not valid!");
        }

        switch(validator.getTaskString())
        {
            case "no":
                this.taskType = TaskType.NORTH;
                this.numberOfSteps = Integer.parseInt(validator.getTaskParameter());
                break;
            case "ea":
                this.taskType = TaskType.EAST;
                this.numberOfSteps = Integer.parseInt(validator.getTaskParameter());
                break;
            case "so":
                this.taskType = TaskType.SOUTH;
                this.numberOfSteps = Integer.parseInt(validator.getTaskParameter());
                break;
            case "we":
                this.taskType = TaskType.WEST;
                this.numberOfSteps = Integer.parseInt(validator.getTaskParameter());
                break;
            case "tr":
                this.taskType = TaskType.TRANSPORT;
                this.gridId = UUID.fromString(validator.getTaskParameter());
                break;
            case "en":
                this.taskType = TaskType.ENTER;
                this.gridId = UUID.fromString(validator.getTaskParameter());
                break;
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
}
