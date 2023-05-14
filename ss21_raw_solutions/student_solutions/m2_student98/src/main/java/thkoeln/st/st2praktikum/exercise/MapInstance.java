package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.AbstractRobot;
import thkoeln.st.st2praktikum.exercise.Robot;
import thkoeln.st.st2praktikum.exercise.AbstractRoom;
import thkoeln.st.st2praktikum.exercise.Room;

import java.util.ArrayList;
import java.util.UUID;

@NoArgsConstructor
public class MapInstance {

    private final ArrayList<AbstractRoom> rooms = new ArrayList<>();
    private final ArrayList<AbstractRobot> robots = new ArrayList<>();

    public UUID newRoom(Integer height, Integer width){
        AbstractRoom room = new Room(height, width);
        rooms.add(room);
        return room.getId();
    }

    public UUID newRobot(String name){
        AbstractRobot robot = new Robot(name);
        robots.add(robot);
        return robot.getId();
    }

    public AbstractRobot findRobot(UUID robotID) {
        for (AbstractRobot robot : robots) {
            if (robot.getId().equals(robotID))
                return robot;
        }
        return null;
    }

    public AbstractRoom findRoom(UUID roomID){
        for (AbstractRoom room : rooms) {
            if (room.getId().equals(roomID))
                return room;
        }
        return null;
    }
}
