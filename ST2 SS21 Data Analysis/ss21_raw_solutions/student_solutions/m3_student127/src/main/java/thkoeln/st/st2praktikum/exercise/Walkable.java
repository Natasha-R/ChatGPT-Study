package thkoeln.st.st2praktikum.exercise;

import java.util.List;

public interface Walkable {
    Point walk(Task task, Room room, List<Walkable> walkablesInRoom);
    Point getPoint();
}
