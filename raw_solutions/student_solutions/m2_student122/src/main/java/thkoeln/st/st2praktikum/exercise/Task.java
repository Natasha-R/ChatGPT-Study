package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps<0){
            throw new RuntimeException();
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

        String[] taskArray = getTaskAsArray(taskString);

        switch (taskArray[0]){
            case "en":
                System.out.println(taskArray[1]);
                this.gridId = UUID.fromString(taskArray[1]);
                this.taskType = TaskType.ENTER;
                break;
            case "tr":
                this.gridId = UUID.fromString(taskArray[1]);
                this.taskType = TaskType.TRANSPORT;
                break;
            case "no":
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                this.taskType = TaskType.NORTH;
                break;
            case "so":
                System.out.println(this.numberOfSteps);
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                System.out.println(this.numberOfSteps);
                this.taskType = TaskType.SOUTH;
                break;
            case "we":
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                this.taskType = TaskType.WEST;
                break;
            case "ea":
                this.numberOfSteps = Integer.parseInt(taskArray[1]);
                this.taskType = TaskType.EAST;
                break;
            default:
                throw new RuntimeException();
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

    public String[] getTaskAsArray(String commandString){
        return commandString.replace("[","").replace("]","").split(",");
    }


}
