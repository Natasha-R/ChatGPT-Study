package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

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

    public static void main(String[] args){
        new Task("[tr,123e4567-e89b-12d3-a456-556642440000]");
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
