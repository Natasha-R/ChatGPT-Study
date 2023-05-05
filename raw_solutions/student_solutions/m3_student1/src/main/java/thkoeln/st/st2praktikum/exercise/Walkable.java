package thkoeln.st.st2praktikum.exercise;

import java.util.List;

public interface Walkable {
    Point walk(Command task, Field room, List<Walkable> walkablesInRoom);
    Point getPoint();
}
