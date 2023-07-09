package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps < 0)
            throw new IllegalArgumentException("numberOfSteps can't be negative.");

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
        String[] taskValues = taskString.split(",");

        if (taskString.matches("^\\[((so)|(no)|(we)|(ea)),\\s?[1-9]+\\]$")) {

            switch (taskValues[0].substring(1)) {
                case "no": this.taskType = TaskType.NORTH; break;
                case "ea": this.taskType = TaskType.EAST; break;
                case "so": this.taskType = TaskType.SOUTH; break;
                case "we": this.taskType = TaskType.WEST; break;
            }
            this.numberOfSteps = Integer.parseInt(taskValues[1].substring(0, taskValues[1].length() - 1));

        } else if (taskString.matches("^\\[((en)|(tr)),\\s?[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}\\]$")) {

            switch (taskValues[0].substring(1)) {
                case "en": this.taskType = TaskType.ENTER; break;
                case "tr": this.taskType = TaskType.TRANSPORT; break;
            }
            this.gridId = UUID.fromString(taskValues[1].substring(0, taskValues[1].length() - 1));

        } else {
            throw new IllegalArgumentException("TaskString doesn't match correct format. String was: " + taskString);
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
