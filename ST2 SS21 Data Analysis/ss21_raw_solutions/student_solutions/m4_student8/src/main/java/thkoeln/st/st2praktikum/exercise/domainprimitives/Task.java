package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;



@Getter
@Embeddable
@NoArgsConstructor
@Setter
public class Task
{

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps)
    {
        this.taskType = taskType;
        if (numberOfSteps.intValue() < 0)
            throw new RuntimeException("Invalid number of Steps");
        this.numberOfSteps = numberOfSteps;
    }

    public Task(TaskType taskType, UUID gridId)
    {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    public Task(String taskString)
    {

        String uuidPattern = "[a-f0-9]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
        String splitTask[];
        if (!taskString.matches("\\[[a-z]{2},\\d+\\]"))
        {
            if (!taskString.matches("\\[[a-z]{2},"+uuidPattern+"\\]"))
                throw new RuntimeException("Invalid Task pattern");
        }
        splitTask = prepareTask(taskString);
        extractDirectionTask(splitTask[0],splitTask[1]);
    }


    public static Task fromString(String taskString ) {
        return new Task(taskString);
    }

    private void extractDirectionTask(String direction, String steps)
    {
        switch (direction)
        {
            case "en":
                this.taskType = TaskType.ENTER;
                this.gridId = UUID.fromString(steps);
                break;
            case "tr":
                this.taskType = TaskType.TRANSPORT;
                this.gridId = UUID.fromString(steps);
                break;
            case "no":
                this.taskType = TaskType.NORTH;
                assignSteps(steps);
                break;
            case "ea":
                this.taskType = TaskType.EAST;
                assignSteps(steps);
                break;
            case "so":
                this.taskType = TaskType.SOUTH;
                assignSteps(steps);
                break;
            case "we":
                this.taskType = TaskType.WEST;
                assignSteps(steps);
                break;
        }
    }

    private void assignSteps(String steps) throws RuntimeException
    {
        Integer stepVal = Integer.valueOf(steps);
        if (stepVal.intValue()< 0)
            throw new RuntimeException("Invalid command assignment");
        this.numberOfSteps = stepVal;
    }

    private String[] prepareTask(String taskString)
    {
        taskString = taskString.replace("[","").replace("]","");
        String splitCommand[] = taskString.split(",");
        return splitCommand;
    }


}
