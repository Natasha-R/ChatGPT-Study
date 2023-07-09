package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
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

    private TaskType taskType ;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps >= 0) {
            this.taskType = taskType;
            this.numberOfSteps = numberOfSteps;
        }else {
            throw new RuntimeException("keine negativen Werte");
        }
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;

    }

    public Task(TaskType taskType, Integer numberOfSteps, UUID gridId) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
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
        String commands[] = taskString.split(",");
        String command = commands[0].replace("[", " ").trim();
        String step= commands[1].replace("]", " ").trim();
        switch (command){
            case "no": return new Task(TaskType.NORTH, Integer.parseInt(step));
            case "ea": return new Task(TaskType.EAST, Integer.parseInt(step));
            case "we": return new Task(TaskType.WEST, Integer.parseInt(step));
            case "so": return new Task(TaskType.SOUTH, Integer.parseInt(step));
            case "tr": return new Task(TaskType.TRANSPORT,UUID.fromString(step));
            case "en": return new Task(TaskType.ENTER,UUID.fromString(step));
            default: throw new RuntimeException("Failed");
    }
    }
}
