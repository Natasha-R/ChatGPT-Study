package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
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

    /**
     * @param taskString the command in form of a string e.g. [no, <integer>], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) throws RuntimeException {
        String[] task =
                taskString.replace("[", "").replace("]", "").split(",");

        taskType = getTaskType(task[0]);

        if (taskType.isMoveCommand()) {
            numberOfSteps = Integer.parseInt(task[1]);
            checkNumberOfStepsIsNotNegative();
        } else {
            fieldId = UUID.fromString(task[1]);
        }
    }

    protected Task() {
    }

    private void checkNumberOfStepsIsNotNegative() throws RuntimeException {
        if (numberOfSteps < 0) throw new RuntimeException("Numbers of Steps can not be negative");
    }

    private TaskType getTaskType(String taskTypeString) throws RuntimeException {
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
}
