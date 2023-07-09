package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exceptions.TaskException;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if(numberOfSteps < 0)
            throw new TaskException("Number of steps schould be positive.");
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) {
        String[] taskArray = taskString.split(",");
        taskType = valueOf(taskArray[0].substring(1));
        switch (taskType){
            case EAST:
            case WEST:
            case NORTH:
            case SOUTH:
                try {
                    numberOfSteps = Integer.parseInt(taskArray[1].substring(0, taskArray[1].length() - 1));
                }catch (Exception e){
                    throw new TaskException("Wrong number of steps.");
                }
                break;
            case ENTER:
            case TRANSPORT:
                try{
                    gridId = UUID.fromString(taskArray[1].substring(0, taskArray[1].length()-1));
                }catch (Exception e){
                    throw new TaskException("Wrong UUID.");
                }
                break;
            default:
                throw new TaskException("Does not know the task: " + taskString);
        }
    }

    private TaskType valueOf(String taskType){
        switch (taskType){
            case"ea":
                return TaskType.EAST;
            case "we":
                return TaskType.WEST;
            case "no":
                return TaskType.NORTH;
            case "so":
                return TaskType.SOUTH;
            case "tr":
                return TaskType.TRANSPORT;
            case "en":
                return TaskType.ENTER;
            default:
                throw new TaskException("Unknown Tasktype");
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
