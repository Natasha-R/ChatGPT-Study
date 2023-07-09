package thkoeln.st.st2praktikum.exercise.domainprimitives;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Task() {
        
    }

    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if (numberOfSteps < 0) {
            throw new RuntimeException("Error! Please check your number of steps!");
        }
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
        if (!taskString.startsWith("[") || !taskString.contains(",") || !taskString.endsWith("]")) {
            throw new RuntimeException("Please check your input!");
        }
        String[] temp = taskString.split(",");
        if (temp.length > 2) {
            throw new RuntimeException("Error, please check your first input!");
        }

        String firstReplace = temp[0].replace("[", "");
        String secondReplace = temp[1].replace("]", "");
        if (firstReplace.length() != 2) {
            throw new RuntimeException("Error, please check your first input!");
        }

        Task task = new Task();

        try {
            if (firstReplace.equals("we")) {
                task.taskType = TaskType.WEST;
                task.numberOfSteps = Integer.parseInt(secondReplace);
            } else if (firstReplace.equals("no")) {
                task.taskType = TaskType.NORTH;
                task.numberOfSteps = Integer.parseInt(secondReplace);

            } else if (firstReplace.equals("so")) {
                task.taskType = TaskType.SOUTH;
                task.numberOfSteps = Integer.parseInt(secondReplace);

            } else if (firstReplace.equals("ea")) {
                task.taskType = TaskType.EAST;
                task.numberOfSteps = Integer.parseInt(secondReplace);

            } else if (firstReplace.equals("en")) {
                task.taskType = TaskType.ENTER;
                task.gridId = UUID.fromString(secondReplace);
            } else if (firstReplace.equals("tr")) {
                task.taskType = TaskType.TRANSPORT;
                task.gridId = UUID.fromString(secondReplace);

            } else {
                throw new RuntimeException("Please check your first input!");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Please check your second input");
        }

        if (task.numberOfSteps != null)
            if (task.numberOfSteps < 0) {
                throw new RuntimeException("Error! Please check your number of steps!");
            }
        return task;
    }
    
}
