package thkoeln.st.st2praktikum.exercise;

import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

public class Task {

    private static final Pattern taskStringPattern =
            Pattern.compile("^\\[(((no|ea|so|we),\\d)|((en|tr),[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}))\\]$");

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps < 0) {
            throw new IllegalArgumentException("numberOfSteps is not allowed to be negative");
        }
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
        var matcher = Task.taskStringPattern.matcher(taskString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(taskString + " is malformed");
        }

        var stringTokenizer = new StringTokenizer(taskString, "[,]");
        this.taskType = TaskType.of(stringTokenizer.nextToken());

        switch (this.taskType) {
            case ENTER:
            case TRANSPORT:
                this.gridId = UUID.fromString(stringTokenizer.nextToken());
                break;
            case NORTH:
            case EAST:
            case WEST:
            case SOUTH:
                this.numberOfSteps = Integer.parseInt(stringTokenizer.nextToken());
                break;
            default:
                throw new IllegalArgumentException();
        }
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
}
