package thkoeln.st.st2praktikum.exercise.domainprimitives;

import java.util.UUID;


public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

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
    
    public TaskType getTaskType() {
        return taskType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

    public static Task fromString(String taskString ) {
        TaskType taskType;
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
                return new Task(TaskType.NORTH,Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)));

            case "ea":
                if(Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) < 0)
                    throw new RuntimeException("numberOfSteps cant be negative");
                return new Task(TaskType.EAST,Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) );

            case "so":
                if(Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) < 0)
                    throw new RuntimeException("numberOfSteps cant be negative");
                return new Task(TaskType.SOUTH, Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)));

            case "we":
                if(Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) < 0)
                    throw new RuntimeException("numberOfSteps cant be negative");
                return new Task(TaskType.WEST,Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1)) );

            case "en":
                return new Task(TaskType.ENTER,UUID.fromString(commandSplit[1].substring(0,commandSplit[1].length() - 1)) );

            case "tr":
                return new Task(TaskType.TRANSPORT,UUID.fromString(commandSplit[1].substring(0,commandSplit[1].length() - 1)) );

            default: throw new RuntimeException();
        }
    }
}
