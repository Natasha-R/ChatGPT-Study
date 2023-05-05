package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
public class Task {
    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID fieldId;

    @Setter
    private Boolean status = true;


    public Task(TaskType taskType, Integer numberOfSteps) throws RuntimeException {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        checkNumberOfStepsIsNotNegative();
    }

    public Task(TaskType taskType, UUID fieldId) throws RuntimeException {
        this.taskType = taskType;
        this.fieldId = fieldId;
    }

    protected Task() {
    }

    private void checkNumberOfStepsIsNotNegative() throws RuntimeException {
        if (numberOfSteps < 0) throw new RuntimeException("Numbers of Steps can not be negative");
    }

    private static TaskType getTaskType(String taskTypeString) throws RuntimeException {
        switch (taskTypeString) {
            case "no":
                return TaskType.NORTH;
            case "so":
                return TaskType.SOUTH;
            case "we":
                return TaskType.WEST;
            case "ea":
                return TaskType.EAST;
            case "en":
                return TaskType.ENTER;
            case "tr":
                return TaskType.TRANSPORT;
        }
        throw new RuntimeException("No valid TaskType");
    }

    public static Task fromString(String taskString ) {
        String[] task =
                taskString.replace("[", "").replace("]", "").split(",");

        TaskType tempTaskType = getTaskType(task[0]);

        Task newTask;
        if (tempTaskType.isMoveCommand()) {
            Integer tempNumberOfSteps = Integer.parseInt(task[1]);
            newTask = new Task(tempTaskType, tempNumberOfSteps);
        } else {
            UUID tempFieldId = UUID.fromString(task[1]);
            newTask = new Task(tempTaskType, tempFieldId);
        }
        return newTask;
    }

}
