package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    


    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps<0)
            throw new UnsupportedOperationException();
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
        
    }

    public Task(TaskType taskType, UUID gridId) {
        this.taskType = taskType;
        this.gridId = gridId;
        
    }


    public static Task fromString(String taskString ) {
        if (!taskString.startsWith("[")
                || !taskString.endsWith("]")) throw new UnsupportedOperationException();
        String[] taskdata = taskString.replace("[", "")
                .replace("]", "").split(",");
        TaskType taskType = checkAndgetTasktype(taskdata[0]);
        if (taskType.equals(TaskType.ENTER) || taskType.equals(TaskType.TRANSPORT)) {

         return new Task( taskType, UUID.fromString(taskdata[1]));
        } else {
            int numberOfSteps = -1;
            try {
                numberOfSteps = Integer.parseInt(taskdata[1]);
            } catch (NumberFormatException e) {
                throw new UnsupportedOperationException();
            }
            return new Task( taskType, numberOfSteps);

        }


    }

    private static TaskType checkAndgetTasktype(String taskdatum) {
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

    
}
