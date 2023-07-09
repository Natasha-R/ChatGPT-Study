package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import org.dom4j.tree.AbstractEntity;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.UUID;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task  {


    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;
    private UUID id;


    public Task(TaskType taskType, Integer numberOfSteps) {


        if(numberOfSteps < 0){
            throw new NegativNumberExeption("Es sind keine negativen Schritte erlaubt");
        }

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
    public static Task fromString(String taskString ) {
        TaskType taskType = null;
        Integer numberOfSteps = null;
        UUID gridId = null;

        if (!taskString.substring(1, 3).equals("so") &&
                !taskString.substring(1, 3).equals("we") &&
                !taskString.substring(1, 3).equals("ea") &&
                !taskString.substring(1, 3).equals("tr") &&
                !taskString.substring(1, 3).equals("en") &&
                taskString.charAt(3) != ',' ||
                taskString.charAt(0) != '[' ||
                taskString.charAt(taskString.length() - 1) != ']'
        ) {
            throw new InvalidStringError("Dies ist keine zulässiger String für einen Task");
        } else {


            if (taskString.substring(1, 3).equals("no")) {
                taskType = TaskType.NORTH;
                numberOfSteps = Integer.parseInt(taskString.substring(4, taskString.length() - 1));
                Task task = new Task(taskType,numberOfSteps);
                task.setId(UUID.randomUUID());
                return task;
            }
            if (taskString.substring(1, 3).equals("so")) {
                taskType = TaskType.SOUTH;
                numberOfSteps = Integer.parseInt(taskString.substring(4, taskString.length() - 1));
                Task task = new Task(taskType,numberOfSteps);
                task.setId(UUID.randomUUID());
                return task;
            }
            if (taskString.substring(1, 3).equals("we")) {
                taskType = TaskType.WEST;
                numberOfSteps = Integer.parseInt(taskString.substring(4, taskString.length() - 1));
                Task task = new Task(taskType,numberOfSteps);
                task.setId(UUID.randomUUID());
                return task;
            }
            if (taskString.substring(1, 3).equals("ea")) {
                taskType = TaskType.EAST;
                numberOfSteps = Integer.parseInt(taskString.substring(4, taskString.length() - 1));
                Task task = new Task(taskType,numberOfSteps);
                task.setId(UUID.randomUUID());
                return task;
            }
            if (taskString.substring(1, 3).equals("en")) {
                taskType = TaskType.ENTER;
                gridId = UUID.fromString(taskString.substring(4, taskString.length() - 1));
                Task task = new Task(taskType,gridId);
                task.setId(UUID.randomUUID());
                return task;
            }
            if (taskString.substring(1, 3).equals("tr")) {
                taskType = TaskType.TRANSPORT;
                gridId = UUID.fromString(taskString.substring(4, taskString.length() - 1));
                Task task = new Task(taskType,gridId);
                task.setId(UUID.randomUUID());
                return task;
            }

        }
        return null;
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

    public void setId(UUID id) {
        this.id = id;
    }

    @Id
    public UUID getId() {
        return id;
    }
}
