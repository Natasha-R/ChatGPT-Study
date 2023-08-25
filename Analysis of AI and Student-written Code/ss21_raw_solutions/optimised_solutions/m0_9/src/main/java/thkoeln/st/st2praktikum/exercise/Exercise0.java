package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Walkable {
    private Point robotPosition;
    private List<Wall> walls;
    private int roomWidth;
    private int roomHeight;

    public Exercise0() {
        this.roomWidth = 12;
        this.roomHeight = 8;
        this.robotPosition = new Point(3, 0);
        this.walls = new ArrayList<>();

        addWall(new Point(3, 0), new Point(3, 3));
        addWall(new Point(7, 0), new Point(7, 2));
        addWall(new Point(4, 3), new Point(7, 3));
        addWall(new Point(1, 4), new Point(8, 4));
    }

    public Exercise0(int roomWidth, int roomHeight, Point robotStartPosition) {
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.robotPosition = robotStartPosition;
        this.walls = new ArrayList<>();
    }

    public void addWall(Point start, Point end) {
        this.walls.add(new Wall(start, end));
    }

    public void removeWall(Point start, Point end) {
        this.walls.removeIf(wall -> wall.getWallPoints().containsAll(new Wall(start, end).getWallPoints()));
    }

    @Override
    public String walkTo(String walkCommandString) {
        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        switch(direction) {
            case "no":
                for(int i = 0; i < steps; i++) {
                    Point nextPosition = new Point(robotPosition.x, robotPosition.y + 1);
                    if(!isWall(nextPosition) && robotPosition.y + 1 < roomHeight) {
                        robotPosition.y++;
                    } else break;
                }
                break;
            case "ea":
                for(int i = 0; i < steps; i++) {
                    Point nextPosition = new Point(robotPosition.x + 1, robotPosition.y);
                    if(!isWall(nextPosition) && robotPosition.x + 1 < roomWidth) {
                        robotPosition.x++;
                    } else break;
                }
                break;
            case "so":
                for(int i = 0; i < steps; i++) {
                    Point nextPosition = new Point(robotPosition.x, robotPosition.y - 1);
                    if(!isWall(nextPosition) && robotPosition.y - 1 >= 0) {
                        robotPosition.y--;
                    } else break;
                }
                break;
            case "we":
                for(int i = 0; i < steps; i++) {
                    Point nextPosition = new Point(robotPosition.x - 1, robotPosition.y);
                    if(!isWall(nextPosition) && robotPosition.x - 1 >= 0) {
                        robotPosition.x--;
                    } else break;
                }
                break;
        }

        return "(" + robotPosition.x + "," + robotPosition.y + ")";
    }

    private boolean isWall(Point point) {
        return this.walls.stream().anyMatch(wall -> wall.containsPoint(point));
    }

    public Point getRobotPosition() {
        return robotPosition;
    }

    public void setRobotPosition(Point robotPosition) {
        if (!isWall(robotPosition)) {
            this.robotPosition = robotPosition;
        } else {
            System.out.println("Cannot place the robot on a wall!");
        }
    }

    public String getRoomSize() {
        return "(" + roomWidth + "," + roomHeight + ")";
    }
}