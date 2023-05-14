package thkoeln.st.st2praktikum.exercise.map;

import thkoeln.st.st2praktikum.exercise.robot.Instructable;
import thkoeln.st.st2praktikum.exercise.room.Buildable;

public interface Operable extends Queryable {
    void addRoom(Buildable room);
    void addRobot(Instructable robot);
}
