package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.security.InvalidParameterException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Getter
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    protected Task() {
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.validateAndSetSteps(numberOfSteps);
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    public Task(String command, String options) {
        if (this.commandOrOptionsAreNull(command, options))
            throw new InvalidParameterException("Task String does not match these Patterns: [no, 3], [en, <uuid>], [tr, <uuid>]");

        assert command != null;
        assert options != null;

        this.setTaskOptions(command, options);
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public static Task fromString(String taskString) {
        Pattern taskStringPattern = Pattern.compile("^\\[(so|no|we|ea|en|tr),(\\d*|[a-f0-9]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8})]$");
        Matcher taskStringMatcher = taskStringPattern.matcher(taskString);

        String command = null, options = null;

        while (taskStringMatcher.find()) {
            command = taskStringMatcher.group(1);
            options = taskStringMatcher.group(2);
        }

        return new Task(command, options);
    }

    private Boolean commandOrOptionsAreNull(String command, String options) {
        return command == null || options == null;
    }

    private void setTaskOptions(String command, String options) {
        switch (command) {
            case "en":
                setOptionsForEntering(options);
                break;
            case "tr":
                setOptionsForTraversing(options);
                break;
            default:
                setOptionsForMoving(command, options);
        }
    }

    private void setOptionsForEntering(String uuid) {
        this.gridId = UUID.fromString(uuid);
        this.taskType = TaskType.ENTER;
    }

    private void setOptionsForTraversing(String uuid) {
        this.gridId = UUID.fromString(uuid);
        this.taskType = TaskType.TRANSPORT;
    }

    private void setOptionsForMoving(String direction, String options) {
        this.validateAndSetSteps(Integer.parseInt(options));

        this.setDirection(direction);
    }

    private void validateAndSetSteps(Integer steps) {
        if (steps < 0)
            throw new InvalidParameterException("Steps must not be negative");
        this.numberOfSteps = steps;
    }

    private void setDirection(String direction) {
        switch (direction) {
            case "no": {
                this.taskType = TaskType.NORTH;
                break;
            }
            case "ea": {
                this.taskType = TaskType.EAST;
                break;
            }
            case "so": {
                this.taskType = TaskType.SOUTH;
                break;
            }
            case "we": {
                this.taskType = TaskType.WEST;
                break;
            }
            default:
                throw new InvalidParameterException("Invalid direction given. Must be one of (no, ea, so, we).");
        }
    }

    @Override
    public String toString() {
        return "Task{ " +
                "taskType=" + taskType +
                ", numberOfSteps=" + numberOfSteps +
                ", gridId=" + gridId +
                " }";
    }
}
