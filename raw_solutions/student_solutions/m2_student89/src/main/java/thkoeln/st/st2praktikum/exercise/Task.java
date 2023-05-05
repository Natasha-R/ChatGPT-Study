package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.regex.Pattern;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        if (numberOfSteps < 0) {
            throw new RuntimeException("Negative Schritte nicht zulässig");
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

        if (taskString == null) {
            throw new UnsupportedOperationException();
        }

        String[] parts = taskString.replaceAll("\\[|\\]", "").split(Pattern.quote(","));

        if (parts.length < 2) {
            throw new RuntimeException("Falscher Eingabestring");
        }


        if (validCommandAndValueCHECK(parts[0],parts[1])) {
            correctInit(parts);
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
    private boolean validCommandAndValueCHECK(String command, String value)
    {
        String uuidCheck[] = value.split("-");
        if (uuidCheck.length != 5 && (command == "tr" || command == "en")) {
            throw new IllegalArgumentException("gridId erwartet aber nicht bekommen");
        }
        if (uuidCheck.length == 5 && (command == "no" || command == "ea" || command == "so" || command == "we")) {
            throw new IllegalArgumentException("Steps erwartet aber UUID bekommen");
        }
        return true;

    }
    private void correctInit(String[] str)
    {
        switch (str[0]) {
            case "tr":
                this.taskType = TaskType.TRANSPORT;
                this.gridId = UUID.fromString(str[1]);
                break;

            case "en":
                this.taskType = TaskType.ENTER;
                this.gridId = UUID.fromString(str[1]);
                break;

            case "no":
                this.taskType = TaskType.NORTH;
                this.numberOfSteps = Integer.parseInt(str[1]);
                break;

            case "ea":
                this.taskType = TaskType.EAST;
                this.numberOfSteps = Integer.parseInt(str[1]);
                break;

            case "so":
                this.taskType = TaskType.SOUTH;
                this.numberOfSteps = Integer.parseInt(str[1]);
                break;

            case "we":
                this.taskType = TaskType.WEST;
                this.numberOfSteps = Integer.parseInt(str[1]);
                break;

            default:
                throw new RuntimeException("Falscher taskType");
        }


    }

}
