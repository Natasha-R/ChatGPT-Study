package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
@EqualsAndHashCode
public class Task {

    private TaskType taskType;
    private Integer numberOfSteps;
    private UUID gridId;

    private static final HashMap<String, TaskType> mTasks = new HashMap<>();
    private static final HashMap<String, TaskType> eTasks = new HashMap<>();

    static {
        mTasks.put("no",TaskType.NORTH);
        mTasks.put("ea",TaskType.EAST);
        mTasks.put("so",TaskType.SOUTH);
        mTasks.put("we",TaskType.WEST);

        eTasks.put("en", TaskType.ENTER);
        eTasks.put("tr", TaskType.TRANSPORT);
    }



    public Task(String cmd){
        var s = cmd.replace("[","").replace("]","").split(",");
        if(s.length != 2) throw new InvalidCommandException("Invalid command length");
        var c = mTasks.get(s[0]);
        if (c!= null){
            this.taskType = c;
            this.numberOfSteps = Integer.parseInt(s[1]);
        }else {
            c = eTasks.get(s[0]);
            if (c == null) throw new InvalidCommandException("Task type does not exist: "+ cmd);
            this.taskType = c;
            this.gridId= UUID.fromString(s[1]);
        }
    }
    public Task(TaskType TaskType, Integer numberOfSteps) {
        this.taskType = TaskType;
        this.numberOfSteps = numberOfSteps;
        if (numberOfSteps < 0) {
            throw new InvalidCommandException("Negative steps are not supported");
        }
    }

    public Task(TaskType TaskType, UUID gridId) {
        this.taskType = TaskType;
        this.gridId = gridId;
    }

    @Override
    public String toString() {
        if (numberOfSteps != null)
            return "[" + taskType.toString() + "," + numberOfSteps.toString() + "]";
        else
            return "[" + taskType.toString() + "," + gridId.toString() + "]";
    }
}
