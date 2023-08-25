package thkoeln.st.st2praktikum.exercise.map;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.robot.Instructable;
import thkoeln.st.st2praktikum.exercise.room.Buildable;

import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor
public class Map implements Operable {
    private HashMap<UUID, Buildable> rooms = new HashMap<UUID, Buildable>();
    private HashMap<UUID, Instructable> robots = new HashMap<UUID, Instructable>();

    @Override
    public void addRoom(Buildable room) {
         this.rooms.put(room.getId(),room);
    }

    @Override
    public void addRobot(Instructable robot) {
        this.robots.put(robot.getId(),robot);
    }

    @Override
    public Buildable getRoomById(UUID roomId) {
         return this.rooms.get(roomId);
    }

    @Override
    public Instructable getRobotById(UUID robotId) {
         return this.robots.get(robotId);
    }

}
