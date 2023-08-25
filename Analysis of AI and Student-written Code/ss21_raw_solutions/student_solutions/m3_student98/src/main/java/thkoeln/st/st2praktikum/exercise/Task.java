package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.core.InvalidInputException;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) throws InvalidInputException {
        if (!validateStepLength(numberOfSteps))
            throw new InvalidInputException("Only positive movement numbers are permitted.");
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
    public Task(String taskString) throws InvalidInputException {
        parseTaskString(taskString);
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

    private void parseTaskString(String taskString) throws InvalidInputException {
        if (!validateTaskStringFormatting(taskString))
            throw new InvalidInputException("String formatting invalid");

        Pair<String, String> taskSplit = separateTaskString(taskString);
        this.taskType = parseTaskStringToType(taskSplit.getLeft());

        if(getTaskStringValueType(this.taskType) == TaskStringValueType.INTEGER) {
            if(!validateStepLength(Integer.parseInt(taskSplit.getRight())))
                throw new InvalidInputException("Only positive movement numbers are permitted.");
            this.numberOfSteps = (Integer.parseInt(taskSplit.getRight()));
        }
        if(getTaskStringValueType(this.taskType) == TaskStringValueType.UUID) {
            this.gridId = UUID.fromString(taskSplit.getRight());
        }
    }

    private Pair<String, String> separateTaskString(String taskString) {
        taskString = taskString.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] taskSplit = taskString.split(",");
        return new Pair(taskSplit[0], taskSplit[1]);
    }

    private Boolean validateStepLength(Integer numberOfSteps) {
        return numberOfSteps > 0;
    }

    private Boolean validateTaskStringFormatting(String taskString) { // \[\w+,\d\]
        return taskString.matches("\\[\\w+,[\\w-]+\\]");
    }

    private TaskType parseTaskStringToType(String taskStringCommandSection) throws InvalidInputException {
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

    private TaskStringValueType getTaskStringValueType(TaskType task) {
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
