package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Getter
@Embeddable
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Task(TaskType taskType, Integer numberOfSteps) {
        if(numberOfSteps >= 0) {
            this.taskType = taskType;
            this.numberOfSteps = numberOfSteps;
        }else{
            throw new RuntimeException("numberOfSteps should be positive");
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
        if(taskString.matches("\\[\\D{2}\\,.*")){
            String[] parts = taskString.split(",");
            String task = parts[0].replaceAll("[^a-z]", "");
            String secondPart = parts[1];
            if(task.equals("no") || task.equals("ea") || task.equals("so") || task.equals("we")){
                if(secondPart.matches("\\d+\\]")){
                    numberOfSteps = Integer.parseInt(secondPart.replaceAll("[^0-9]", ""));
                    if(task.equals("no")){
                        taskType = TaskType.NORTH;
                    }else if(task.equals("ea")){
                        taskType = TaskType.EAST;
                    }
                    else if(task.equals("so")){
                        taskType = TaskType.SOUTH;
                    }
                    else if(task.equals("we")){
                        taskType = TaskType.WEST;
                    }
                }else{
                    throw new RuntimeException("Wrong Syntax");
                }
            }else if(task.equals("tr") || task.equals("en")){
                if(secondPart.matches("\\w{8}\\-\\w{4}\\-\\w{4}\\-\\w{4}\\-\\w{12}\\]")){
                    gridId = UUID.fromString(secondPart.replaceAll("]", ""));
                    if(task.equals("tr")){
                        taskType = TaskType.TRANSPORT;
                    }else if(task.equals("en")){
                        taskType = TaskType.ENTER;
                    }
                }else{
                    throw new RuntimeException("Wrong Syntax");
                }
            }else{
                throw new RuntimeException("Die Aufgabe gibt es nicht");
            }
        }else{
            throw new RuntimeException("Wrong Syntax");
        }
    }
}


