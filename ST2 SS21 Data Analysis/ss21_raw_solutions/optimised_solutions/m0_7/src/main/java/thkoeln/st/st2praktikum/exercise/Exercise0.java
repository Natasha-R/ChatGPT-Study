package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.Arrays;

public class Exercise0 implements Walkable {

    private final Room room;
    private final Robot robot;

    public Exercise0() {
        this.room = new Room(12, 8, Arrays.asList(new Point(3,3), new Point(7,0), new Point(7,2), new Point(4,3), new Point(7,3), new Point(1,4), new Point(8,4)));
        this.robot = new Robot(new Point(3, 0), room);
    }

    @Override
    public String walkTo(String walkCommandString) {
        Command command = Command.fromString(walkCommandString);
        robot.executeCommand(command);
        return robot.getPosition();
    }
}

