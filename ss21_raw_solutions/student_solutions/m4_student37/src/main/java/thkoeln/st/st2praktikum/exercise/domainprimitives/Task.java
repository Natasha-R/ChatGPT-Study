package thkoeln.st.st2praktikum.exercise.domainprimitives;

import java.security.InvalidParameterException;
import java.util.UUID;


public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Task()
    {
        set(TaskType.ENTER, null, null);
    }

    public Task(TaskType commandType, Integer numberOfSteps) {
        set(commandType, numberOfSteps);
    }

    public Task(TaskType commandType, UUID gridId) {
        set(commandType, gridId);
    }

    public Task(String string)
    {
        String[] command = string.substring(1,string.length()-1).split(",");
        switch(command[0])
        {
            case "tr":
                set(TaskType.TRANSPORT, UUID.fromString(command[1]));
                break;

            case "en":
                set(TaskType.ENTER,UUID.fromString(command[1]));
                break;

            case "no":
                set(TaskType.NORTH,Integer.parseInt(command[1]));
                break;

            case "ea":
                set(TaskType.EAST,Integer.parseInt(command[1]));
                break;

            case "so":
                set(TaskType.SOUTH,Integer.parseInt(command[1]));
                break;

            case "we":
                set(TaskType.WEST,Integer.parseInt(command[1]));
                break;

            default:
                throw new InvalidParameterException();
        }
    }

    public Task(TaskType commandType, Integer numberOfSteps, UUID gridId){
        set(commandType, numberOfSteps, gridId);
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


    public static Task fromString(String commandString )
    {
        return new Task(commandString);
    }

    public void set(TaskType commandType, Integer numberOfSteps)
    {
        set(commandType, numberOfSteps, null);
    }

    public void set(TaskType commandType, UUID gridId)
    {
        set(commandType, null, gridId);
    }

    public void set(TaskType commandType, Integer numberOfSteps, UUID gridId)
    {
        if(numberOfSteps != null)
        {
            if(numberOfSteps < 0)
            {
                throw new InvalidParameterException();
            }
        }

        this.taskType = commandType;
        this.numberOfSteps = numberOfSteps;
        this.gridId = gridId;
    }

}
