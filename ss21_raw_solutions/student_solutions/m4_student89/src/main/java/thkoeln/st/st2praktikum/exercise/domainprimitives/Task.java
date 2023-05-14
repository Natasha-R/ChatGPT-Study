package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;
import java.util.regex.Pattern;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
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
            throw new RuntimeException("Negative Schritte nicht zulässig");
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

        if (taskString == null) {
            throw new UnsupportedOperationException();
        }

        String[] parts = taskString.replaceAll("\\[|]", "").split(Pattern.quote(","));

        if (parts.length < 2) {
            throw new RuntimeException("Falscher Eingabestring");
        }


        if (validCommandAndValueCHECK(parts[0],parts[1])) {
            return correctInit(parts);
        }
        throw new UnsupportedOperationException();
    }
    private static boolean validCommandAndValueCHECK(String command, String value)
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
    private static Task correctInit(String[] str)
    {
        switch (str[0]) {
            case "tr":
                return new Task(TaskType.TRANSPORT,UUID.fromString(str[1])) ;

            case "en":
                return new Task(TaskType.ENTER,UUID.fromString(str[1])) ;

            case "no":
                return new Task(TaskType.NORTH,Integer.parseInt(str[1])) ;

            case "ea":
                return new Task(TaskType.EAST,Integer.parseInt(str[1])) ;

            case "so":
                return new Task(TaskType.SOUTH,Integer.parseInt(str[1])) ;

            case "we":
                return new Task(TaskType.WEST,Integer.parseInt(str[1])) ;
            default:
                throw new RuntimeException("Falscher taskType");
        }


    }

}
