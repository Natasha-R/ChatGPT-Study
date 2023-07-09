package thkoeln.st.st2praktikum.exercise;

import java.util.*;
import java.lang.*;

public class Exercise0 implements Walkable {

    private static final int ROOM_WIDTH = 12;
    private static final int ROOM_HEIGHT = 8;

    private Robot robot;

    public Exercise0() {
        this.robot = new Robot(3, 0);
        setupWalls();
    }

    private void setupWalls() {
        Wall.setupBoundaryWalls(ROOM_WIDTH, ROOM_HEIGHT);
        Wall.addWall(new Wall(3, 0, 3, 3));
        Wall.addWall(new Wall(7, 0, 7, 2));
        Wall.addWall(new Wall(4, 3, 7, 3));
        Wall.addWall(new Wall(1, 4, 8, 4));
    }

    @Override
    public String walkTo(String walkCommandString) {
        try {
            Command command = Command.parse(walkCommandString);
            this.robot.walk(command);
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
        }
        return robot.getPosition();
    }
}


