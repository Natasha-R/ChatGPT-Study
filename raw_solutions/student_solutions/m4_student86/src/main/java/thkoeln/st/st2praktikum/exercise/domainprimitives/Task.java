package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Getter
public class Task {

    @Id
    private UUID id = UUID.randomUUID();

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    protected Task() { }

    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps < 0)
            throw new RuntimeException();
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    public Task(TaskType taskType, Integer numberOfSteps, UUID gridId) {
        if (numberOfSteps < 0)
            throw new RuntimeException();
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public static Task fromString(String taskString ) {
        if (!taskString.matches("^\\[.+,.+]$"))
            throw new TaskException("Formatfehler: " + taskString);

        String[] taskArguments = taskString
                .replaceAll("^\\[|]$", "")
                .split(",", 2);

        TaskType taskType;
        Integer numberOfSteps = 0;
        UUID gridId = null;

        switch (taskArguments[0]) {
            case "en":
                taskType = TaskType.ENTER;
                gridId = UUID.fromString(taskArguments[1]);
                break;
            case "tr":
                taskType = TaskType.TRANSPORT;
                gridId = UUID.fromString(taskArguments[1]);
                break;
            default: switch (taskArguments[0]) {
                case "we": taskType = TaskType.WEST; break;
                case "ea": taskType = TaskType.EAST; break;
                case "so": taskType = TaskType.SOUTH; break;
                case "no": taskType = TaskType.NORTH; break;
                default:
                    throw new TaskException("Unbekannter Befehl: " + taskString);
                }
                numberOfSteps = Integer.parseInt(taskArguments[1]);
                break;
        }

        return new Task(taskType, numberOfSteps, gridId);
    }
}
