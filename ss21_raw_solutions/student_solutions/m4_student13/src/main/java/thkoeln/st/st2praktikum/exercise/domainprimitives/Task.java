package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;

        if(numberOfSteps >= 0){
            this.numberOfSteps = numberOfSteps;
        } else {
            throw new TaskException("no negativ steps allowed");
        }

    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) {

        String [] typ = taskString
                .substring(1,taskString.length()-1)
                .split(",");


        switch(typ[0]){
            case "no" : this.taskType = TaskType.NORTH;
                this.numberOfSteps = Integer.parseInt(typ[1]);
                break;

            case "so" : this.taskType = TaskType.SOUTH;
                this.numberOfSteps = Integer.parseInt(typ[1]);
                break;

            case "ea" : this.taskType = TaskType.EAST;
                this.numberOfSteps = Integer.parseInt(typ[1]);
                break;

            case "we" : this.taskType = TaskType.WEST;
                this.numberOfSteps = Integer.parseInt(typ[1]);
                break;

            case "en": this.taskType = TaskType.ENTER;
                this.gridId = UUID.fromString(typ[1]);
                break;

            case "tr": this.taskType = TaskType.TRANSPORT;
                this.gridId = UUID.fromString(typ[1]);
                break;
            default: throw new TaskException("no valid task");
        }


        //throw new UnsupportedOperationException();
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
        //throw new UnsupportedOperationException();
    }
}


