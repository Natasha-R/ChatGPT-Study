package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.UUID;

@Embeddable
@Getter
@EqualsAndHashCode
public class Task {
    private TaskType taskType;
    private Integer numberOfSteps;
    @Id
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps < 0)
            throw new TaskException("invalid numer of steps");
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) throws TaskException{
        try {
            if(!(taskString.startsWith("[")&&taskString.endsWith("]")&& StringUtils.countOccurrencesOf(taskString, ",") == 1))
                throw new TaskException("wrong format");

            String[] command = taskString.replace("[", "").replace("]", "").split(",");
            numberOfSteps = 0;
            switch (command[0]) {
                case "tr":
                    taskType = TaskType.TRANSPORT;
                    gridId = UUID.fromString(command[1]);
                    break;
                case "en":
                    taskType = TaskType.ENTER;
                    gridId = UUID.fromString(command[1]);
                    break;
                default:
                    taskType = generateDirectionTaskType(command[0]);
                    numberOfSteps = Integer.valueOf(command[1]);
                    break;
            }
            if(numberOfSteps < 0)
                throw new TaskException("invalid numer of steps");

         }catch (Exception e){
            throw new TaskException("invalid Task");
        }
    }

    private TaskType generateDirectionTaskType(String s){
        switch (s) {
            case "no":
                return TaskType.NORTH;
            case "ea":
                return TaskType.EAST;
            case "so":
                return TaskType.SOUTH;
            case "we":
                return TaskType.WEST;
            default:
                throw new TaskException("invalid Task");
        }
    }

}
