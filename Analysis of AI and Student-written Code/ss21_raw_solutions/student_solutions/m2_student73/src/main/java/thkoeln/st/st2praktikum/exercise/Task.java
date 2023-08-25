package thkoeln.st.st2praktikum.exercise;

import org.assertj.core.condition.Negative;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidStringException;
import thkoeln.st.st2praktikum.exercise.exceptions.NegativeNumberException;

import java.util.UUID;

public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        this.taskType = taskType;
        if(numberOfSteps > 0) {
            this.numberOfSteps = numberOfSteps;
        } else throw new NegativeNumberException("No negative Numbers allowed");
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Task(String taskString) throws RuntimeException {
        if(checkStringSyntax(taskString)) {
            String task = taskString.substring(taskString.indexOf('[')+1, taskString.indexOf(','));
            try {
                this.taskType = findTaskType(task);
            } catch (RuntimeException e) {
                throw new InvalidStringException("Please input a correct String");
            }
            if (this.taskType == TaskType.TRANSPORT || this.taskType == TaskType.ENTER) {
               try {
                   this.gridId = UUID.fromString(taskString.substring(4, taskString.indexOf(']')));
               } catch (Exception e) {
                   throw new InvalidStringException("Please input a correct String");
               }
            } else {
                int i = Integer.parseInt(taskString.substring(4, taskString.indexOf(']')));
                if(i > 0) {
                    this.numberOfSteps = i;
                } else throw new NegativeNumberException("No negative Numbers allowed");
            }

        } else throw new InvalidStringException("Please input a correct String");
    }

    private TaskType findTaskType(String taskString) {
        switch (taskString) {
            case "tr":
                return TaskType.TRANSPORT;
            case "en":
                return TaskType.ENTER;
            case "no":
                return TaskType.NORTH;
            case "so":
                return TaskType.SOUTH;
            case "we":
                return TaskType.WEST;
            case "ea":
                return TaskType.EAST;
            default:
                throw new RuntimeException();
        }
    }

    private boolean checkStringSyntax (String taskString) {
        if(taskString.charAt(0) == '[' && taskString.charAt(taskString.length()-1) == ']') {
            if(countOccurrences(taskString, ',') == 1 && countOccurrences(taskString, '[') == 1 && countOccurrences(taskString, ']') == 1) {
                return taskString.charAt(3) == ',';
            } else return false;
        } else return false;
    }

    private Integer countOccurrences(String taskString ,char c) {
        int count = 0;
        for (int i = 0; i < taskString.length(); i++) {
            if (taskString.charAt(i) == c) {
                count++;
            }
        }
        return count;
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
