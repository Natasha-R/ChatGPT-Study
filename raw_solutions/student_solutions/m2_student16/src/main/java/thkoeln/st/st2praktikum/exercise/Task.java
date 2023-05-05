package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if(this.numberOfSteps < 0) throw new IllegalArgumentException();
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) {
        taskType = getTaskTypeFromString(taskString);
        String secondPartOfTaskString = taskString.substring(taskString.lastIndexOf(',') + 1, taskString.length() - 1);
        if(taskType.equals(TaskType.ENTER) || taskType.equals(TaskType.TRANSPORT)){
            this.gridId = UUID.fromString(secondPartOfTaskString);
        }
        else{
            numberOfSteps = Integer.parseInt(secondPartOfTaskString);
            if(numberOfSteps < 0) throw new IllegalArgumentException();
        }
    }

    private static TaskType getTaskTypeFromString(String taskString){
        switch(taskString.substring(1,taskString.lastIndexOf(','))){
            case "en":
                return TaskType.ENTER;
            case "tr":
                return TaskType.TRANSPORT;
            case "no":
                return TaskType.NORTH;
            case "so":
                return TaskType.SOUTH;
            case "ea":
                return TaskType.EAST;
            case "we":
                return TaskType.WEST;
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
