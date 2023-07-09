package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    private Robot robot;
    private Room room;

    private Position position;


    public Exercise0() {

        this.position = new Position(new Point(3, 0));
        this.robot = new Robot(position.getPoint());

        List<Wall> walls = Arrays.asList(
                new Wall(3, 0, 3, 3),
                new Wall(7, 0, 7, 2),
                new Wall(4, 3, 7, 3),
                new Wall(1, 4, 8, 4)
        );

        this.room = new Room(12, 8, walls);
    }

    @Override
    public String walkTo(String walkCommandString) {
        String[] parts = walkCommandString.replace("[", "").replace("]", "").split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        robot.walkTo(direction, steps, room);

        position.setPoint(robot.getPosition());

        return position.toString();
    }
}
