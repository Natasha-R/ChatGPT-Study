package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.TaskDto;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Task {
    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) throws InvalidInputException {
        if (!validateStepLength(numberOfSteps)) {
            throw new InvalidInputException("Number of steps for a task is not allowed to be negative.");
        }
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;

    }

    public Task(TaskType taskType, UUID gridId) throws InvalidInputException {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    public Task(TaskType taskType, Integer numberOfSteps, UUID gridId) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        this.gridId = gridId;
    }

    public static Task fromString(String taskString ) throws InvalidInputException {
        return parseTaskFromString(taskString);
    }

    public static Task fromDto(TaskDto taskDto) throws InvalidInputException {
        return new Task(taskDto.getTaskType(), taskDto.getNumberOfSteps(), taskDto.getGridId());
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

    private static Task parseTaskFromString(String taskString) throws InvalidInputException {
        if (!validateTaskStringFormatting(taskString)) {
            throw new InvalidInputException("String formatting invalid");
        }
        Pair<String, String> taskSplit = separateTaskString(taskString);
        TaskType taskType = parseTaskStringToType(taskSplit.getLeft());

        if(getTaskStringValueType(taskType) == TaskStringValueType.INTEGER) {
            if(!validateStepLength(Integer.parseInt(taskSplit.getRight()))) {
                throw new InvalidInputException("Only positive movement numbers are permitted.");
            }
            Integer numberOfSteps = (Integer.parseInt(taskSplit.getRight()));
            return new Task(taskType, numberOfSteps);
        }
        if(getTaskStringValueType(taskType) == TaskStringValueType.UUID) {
            UUID gridId = UUID.fromString(taskSplit.getRight());
            return new Task(taskType, gridId);
        }
        throw new RuntimeException("Unexpected behavior while creating new task from string. Case is undefined.");
    }

    private static Pair<String, String> separateTaskString(String taskString) {
        taskString = taskString.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] taskSplit = taskString.split(",");
        return new Pair(taskSplit[0], taskSplit[1]);
    }

    private static Boolean validateTaskStringFormatting(String taskString) { // \[\w+,\d\]
        return taskString.matches("\\[\\w+,[\\w-]+\\]");
    }

    private static Boolean validateStepLength(Integer numberOfSteps) {
        return numberOfSteps > 0;
    }

    private static TaskType parseTaskStringToType(String taskStringCommandSection) throws InvalidInputException {
        TaskType task;
        switch (taskStringCommandSection) {
            case "no": task = TaskType.NORTH; break;
            case "ea": task = TaskType.EAST; break;
            case "so": task = TaskType.SOUTH; break;
            case "we": task = TaskType.WEST; break;
            case "tr": task = TaskType.TRANSPORT; break;
            case "en": task = TaskType.ENTER; break;
            default: throw new InvalidInputException("Unidentified taskType in String");
        }
        return task;
    }

    private static TaskStringValueType getTaskStringValueType(TaskType task) {
        switch (task) {
            case ENTER:
            case TRANSPORT: return TaskStringValueType.UUID;
            default: return TaskStringValueType.INTEGER;
        }
    }

    private enum TaskStringValueType {
        UUID,
        INTEGER
    }
}
