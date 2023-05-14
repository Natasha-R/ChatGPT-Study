package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embedded;
import java.util.UUID;

public class Task {
    @Embedded
    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if(numberOfSteps < 0)
        {
            throw new IllegalActionException("Number of steps must be positive!");
        }
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) {
        int index = taskString.indexOf("[");
        if(index != 0)
        {
            throw new IllegalActionException("Task Input has wrong format!");
        }
        String tasktype = taskString.substring(1,3);

        if (tasktype.equals("no")) {
            taskType = TaskType.NORTH;
        }
        else if (tasktype.equals("ea")) {
            taskType = TaskType.EAST;
        }
        else if (tasktype.equals("so")) {
            taskType = TaskType.SOUTH;
        }
        else if (tasktype.equals("we")) {
            taskType = TaskType.WEST;
        }
        else if (tasktype.equals("tr")) {
            taskType = TaskType.TRANSPORT;
        }
        else if (tasktype.equals("en")) {
            taskType = TaskType.ENTER;
        }
        else
        {
            throw new IllegalActionException("Task Input has wrong format!");
        }
        int indexOfExtra = taskString.lastIndexOf(',');
        if(indexOfExtra != 3)
        {
            throw new IllegalActionException("Task Input has wrong format!");
        }
        if(taskType == TaskType.TRANSPORT || taskType == TaskType.ENTER)
        {
            gridId = UUID.fromString(taskString.substring(indexOfExtra+1,indexOfExtra+37));
        }
        else
        {
            numberOfSteps = Obstacle.getNumberAt(taskString,indexOfExtra + 1);
            //numberOfSteps = Integer.valueOf(String.valueOf(taskString.charAt(indexOfExtra + 1)));
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
