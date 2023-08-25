package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;



import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.room.domain.Walkable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.execution.RobotCommand;

import java.util.UUID;

public interface Instructable extends Identifiable {
    Boolean readCommand (RobotCommand robotCommand);
    Coordinate getCoordinate();
    Walkable getRoom();
    UUID getRoomId();
    void addTask(Task task);
    void removeAllTasks();
}
