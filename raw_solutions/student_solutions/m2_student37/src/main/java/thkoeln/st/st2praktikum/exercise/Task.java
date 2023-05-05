package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        if (numberOfSteps<0){
            throw new RuntimeException();
        }
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
        String[] command = taskString.split(",");
        switch (command[0].substring(1)){
            case "tr":
                this.taskType=TaskType.TRANSPORT;
                this.gridId=UUID.fromString(command[1].substring(0,command[1].length()-1));
                break;
            case "en":
                this.taskType=TaskType.ENTER;
                this.gridId=UUID.fromString(command[1].substring(0,command[1].length()-1));
                break;
            default:
                this.numberOfSteps=Integer.parseInt(command[1].substring(0,command[1].length()-1));
                switch (command[0].substring(1)){
                    case "no":
                        this.taskType=TaskType.NORTH;
                        break;
                    case "we":
                        this.taskType=TaskType.WEST;
                        break;
                    case "so":
                        this.taskType=TaskType.SOUTH;
                        break;
                    case "ea":
                        this.taskType=TaskType.EAST;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
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
