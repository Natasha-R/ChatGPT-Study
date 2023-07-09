package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps >= 0) {
            this.taskType = taskType;
            this.numberOfSteps = numberOfSteps;
        }else {
            throw new RuntimeException("keine negativen Werte");
        }
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString){
        String commands[] = taskString.split(",");
        String command = commands[0].replace("[", " ").trim();
        String step= commands[1].replace("]", " ").trim();
        switch (command){
            case "no": taskType = TaskType.NORTH; numberOfSteps = Integer.parseInt(step); break;
            case "ea": taskType = TaskType.EAST; numberOfSteps = Integer.parseInt(step);break;
            case "we": taskType = TaskType.WEST; numberOfSteps = Integer.parseInt(step);break;
            case "so": taskType = TaskType.SOUTH; numberOfSteps = Integer.parseInt(step); break;
            case "tr": taskType = TaskType.TRANSPORT; gridId = UUID.fromString(step); break;
            case "en": taskType = TaskType.ENTER;  gridId = UUID.fromString(step); break;
            default: throw new RuntimeException("Failed");
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
