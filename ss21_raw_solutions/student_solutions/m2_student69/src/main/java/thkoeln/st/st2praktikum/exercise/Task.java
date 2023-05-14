package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if(numberOfSteps < 0) {
            throw new RuntimeException("Error! Please check your number of steps!");
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
        if(!taskString.startsWith("[") || !taskString.contains(",") || !taskString.endsWith("]")) {
            throw new RuntimeException("Please check your input!");
        }
        String[] temp = taskString.split(",");
        if(temp.length > 2) {
            throw new RuntimeException("Error, please check your first input!");
        }

        String firstReplace = temp[0].replace("[", "");
        String secondReplace = temp[1].replace("]", "");
        if(firstReplace.length() != 2)
        {
            throw new RuntimeException("Error, please check your first input!");
        }

        try {
            if (firstReplace.equals("we")) {
                taskType = TaskType.WEST;
                numberOfSteps = Integer.parseInt(secondReplace);
            } else if (firstReplace.equals("no")) {
                taskType = TaskType.NORTH;
                numberOfSteps = Integer.parseInt(secondReplace);

            } else if (firstReplace.equals("so")) {
                taskType = TaskType.SOUTH;
                numberOfSteps = Integer.parseInt(secondReplace);

            } else if (firstReplace.equals("ea")) {
                taskType = TaskType.EAST;
                numberOfSteps = Integer.parseInt(secondReplace);

            } else if (firstReplace.equals("en")) {
                taskType = TaskType.ENTER;
                gridId = UUID.fromString(secondReplace);
            } else if (firstReplace.equals("tr")) {
                taskType = TaskType.TRANSPORT;
                gridId = UUID.fromString(secondReplace);

            } else {
                throw new RuntimeException("Please check your first input!");
            }
        }catch (IllegalArgumentException e) {
            throw new RuntimeException("Please check your second input");
        }

        if(numberOfSteps != null)
        if(numberOfSteps < 0) {
            throw new RuntimeException("Error! Please check your number of steps!");
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
