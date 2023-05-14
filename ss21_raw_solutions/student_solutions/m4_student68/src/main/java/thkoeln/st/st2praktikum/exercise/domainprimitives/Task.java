package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.Embeddable;
import java.util.UUID;


@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps <= 0)
            throw new TaskException("Invalid numbers of steps: "+ numberOfSteps);
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
    public Task(String taskString) {
        if ( !taskString.matches("\\[[a-z]{2},([1-9][0-9]*|\\w{8}\\-\\w{4}\\-\\w{4}\\-\\w{4}\\-\\w{12})\\]") )
            throw new TaskException("Invalid Command: "+ taskString);

        String[] fragmentedRobotCommand = taskString.replaceAll("[\\[\\]]","").split(",");

        switch(fragmentedRobotCommand[0]){
            case "no": taskType = TaskType.NORTH; break;
            case "so": taskType = TaskType.SOUTH; break;
            case "we": taskType = TaskType.WEST; break;
            case "ea": taskType = TaskType.EAST; break;
            case "tr": taskType = TaskType.TRANSPORT; break;
            case "en": taskType = TaskType.ENTER; break;
            default: throw new TaskException("Not able to Resolve TaskType: "+ fragmentedRobotCommand[0]);
        }
        if (taskType == TaskType.TRANSPORT || taskType == TaskType.ENTER)
            gridId = UUID.fromString(fragmentedRobotCommand[1]);
        else
            numberOfSteps = Integer.parseInt(fragmentedRobotCommand[1]);
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

    @Override
    public String toString() { return "(" + this.taskType + "," + this.numberOfSteps+ ","+ this.gridId + ")"; }
    
}
