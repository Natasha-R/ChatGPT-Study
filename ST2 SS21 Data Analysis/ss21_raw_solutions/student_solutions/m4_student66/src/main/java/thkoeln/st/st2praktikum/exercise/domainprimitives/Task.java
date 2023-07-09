package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class Task {

    @Getter
    private TaskType taskType;
    @Getter
    private Integer numberOfSteps;
    @Getter
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if(numberOfSteps < 0)
            throw new TaskException("Number of steps schould be positive.");
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public static Task fromString(String taskString) {
        String[] taskArray = taskString.split(",");
        TaskType taskType = valueOf(taskArray[0].substring(1));
        switch (taskType){
            case EAST:
            case WEST:
            case NORTH:
            case SOUTH:
                try {
                    Integer numberOfSteps = Integer.parseInt(taskArray[1].substring(0, taskArray[1].length() - 1));
                    return new Task(taskType, numberOfSteps);
                }catch (Exception e){
                    throw new TaskException("Wrong number of steps.");
                }
            case ENTER:
            case TRANSPORT:
                try{
                    UUID gridId = UUID.fromString(taskArray[1].substring(0, taskArray[1].length()-1));
                    return new Task(taskType, gridId);
                }catch (Exception e){
                    throw new TaskException("Wrong UUID.");
                }
            default:
                throw new TaskException("Does not know the task: " + taskString);
        }
    }

    private static TaskType valueOf(String taskType){
        switch (taskType){
            case"ea":
                return TaskType.EAST;
            case "we":
                return TaskType.WEST;
            case "no":
                return TaskType.NORTH;
            case "so":
                return TaskType.SOUTH;
            case "tr":
                return TaskType.TRANSPORT;
            case "en":
                return TaskType.ENTER;
            default:
                throw new TaskException("Unknown Tasktype");
        }
    }
}
