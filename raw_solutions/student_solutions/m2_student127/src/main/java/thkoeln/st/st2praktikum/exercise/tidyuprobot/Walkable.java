package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.List;

public interface Walkable {
    Point walk(Task task, Room room, List<Walkable> walkablesInRoom);
    Point getPoint();
}
