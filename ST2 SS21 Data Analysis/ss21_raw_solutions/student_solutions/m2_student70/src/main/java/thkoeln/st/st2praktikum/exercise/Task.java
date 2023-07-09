package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
if(numberOfSteps<1)  throw new UnsupportedOperationException();
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

        if (!taskString.startsWith("[")
                || !taskString.endsWith("]")) throw new UnsupportedOperationException();
        String[] taskdata = taskString.replace("[", "")
                .replace("]", "").split(",");
        TaskType taskType = checkAndgetTasktype(taskdata[0]);
        if (taskType.equals(TaskType.ENTER) || taskType.equals(TaskType.TRANSPORT)) {

                this.taskType = taskType;
                this.gridId = UUID.fromString(taskdata[1]);



        } else {
            int numberOfSteps = -1;
            try {
                numberOfSteps = Integer.parseInt(taskdata[1]);
            } catch (NumberFormatException e) {
                throw new UnsupportedOperationException();
            }
            this.taskType = taskType;
            this.numberOfSteps = numberOfSteps;


        }


    }

    private TaskType checkAndgetTasktype(String taskdatum) {
        switch (taskdatum) {
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
                throw new UnsupportedOperationException();
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
