package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Walkable {
    private final int fieldWidth;
    private final int fieldHeight;
    private final List<Wall> walls = new ArrayList<>();
    private Point machinePosition;

    public Exercise0() {
        this.fieldWidth = 12;
        this.fieldHeight = 9;
        this.machinePosition = new Point(0, 2);

        // Initialize walls
        walls.add(new Wall(new Point(3, 0), new Point(3, 3)));
        walls.add(new Wall(new Point(5, 0), new Point(5, 4)));
        walls.add(new Wall(new Point(4, 5), new Point(7, 5)));
        walls.add(new Wall(new Point(7, 5), new Point(7, 9)));
    }

    @Override
    public String walk(String walkCommandString) {
        // Parse command
        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        // Execute command
        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case "no":
                    if (canMoveTo(new Point(machinePosition.x, machinePosition.y + 1)))
                        machinePosition.y++;
                    break;
                case "ea":
                    if (canMoveTo(new Point(machinePosition.x + 1, machinePosition.y)))
                        machinePosition.x++;
                    break;
                case "so":
                    if (canMoveTo(new Point(machinePosition.x, machinePosition.y - 1)))
                        machinePosition.y--;
                    break;
                case "we":
                    if (canMoveTo(new Point(machinePosition.x - 1, machinePosition.y)))
                        machinePosition.x--;
                    break;
            }
        }

        // Return machine position
        return String.format("(%d,%d)", machinePosition.x, machinePosition.y);
    }

    private boolean canMoveTo(Point point) {
        // Check boundaries
        if (point.x < 0 || point.y < 0 || point.x >= fieldWidth || point.y >= fieldHeight)
            return false;

        // Check walls
        for (Wall wall : walls) {
            if (wall.intersects(point))
                return false;
        }

        return true;
    }
}
