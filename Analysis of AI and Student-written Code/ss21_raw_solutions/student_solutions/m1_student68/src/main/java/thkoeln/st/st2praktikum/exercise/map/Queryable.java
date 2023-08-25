package thkoeln.st.st2praktikum.exercise.map;

import thkoeln.st.st2praktikum.exercise.robot.Instructable;
import thkoeln.st.st2praktikum.exercise.room.Buildable;

import java.util.UUID;

public interface Queryable {
    Buildable getRoomById(UUID roomId);
    Instructable getRobotById(UUID robotId);
}
