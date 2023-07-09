package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps < 0)
            throw new RuntimeException("numberOfSteps cant be negative");

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
        if(taskString.split(",").length != 2)
            throw new RuntimeException("command must be divided by ','");

        String[] commandSplit = taskString.split(",");

        String leftCommand = commandSplit[0].substring(1);

        if(!commandSplit[0].startsWith("["))
            throw new RuntimeException("command must start with '['");


        if(!commandSplit[1].endsWith("]"))
            throw new RuntimeException("command must end with ']'");

        switch (leftCommand) {
            case "no":
                if(Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) < 0)
                    throw new RuntimeException("numberOfSteps cant be negative");
                this.taskType = TaskType.NORTH;
                this.numberOfSteps = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                break;

            case "ea":
                if(Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) < 0)
                    throw new RuntimeException("numberOfSteps cant be negative");
                this.taskType = TaskType.EAST;
                this.numberOfSteps = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                break;

            case "so":
                if(Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) < 0)
                    throw new RuntimeException("numberOfSteps cant be negative");
                this.taskType = TaskType.SOUTH;
                this.numberOfSteps = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                break;

            case "we":
                if(Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) < 0)
                    throw new RuntimeException("numberOfSteps cant be negative");
                this.taskType = TaskType.WEST;
                this.numberOfSteps = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                break;

            case "en":
                this.taskType = TaskType.ENTER;
                this.gridId = UUID.fromString(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                break;

            case "tr":
                this.taskType = TaskType.TRANSPORT;
                this.gridId = UUID.fromString(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                break;

            default: throw new RuntimeException();
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
