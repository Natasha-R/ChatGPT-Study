package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domains.Connection;
import thkoeln.st.st2praktikum.exercise.domains.Room;

public interface Transportable {
    void transport(Connection connection, Room room);
}
