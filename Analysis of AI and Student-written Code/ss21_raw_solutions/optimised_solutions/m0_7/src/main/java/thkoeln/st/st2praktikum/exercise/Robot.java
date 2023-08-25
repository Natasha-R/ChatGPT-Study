package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Robot {
    private Point position;
    private final Room room;

    Robot(Point startingPosition, Room room) {
        this.position = startingPosition;
        this.room = room;
    }

    void executeCommand(Command command) {
        Point newPosition = new Point(position);
        for (int i = 0; i < command.getSteps(); i++) {
            switch (command.getDirection()) {
                case "no": newPosition.y++; break;
                case "ea": newPosition.x++; break;
                case "so": newPosition.y--; break;
                case "we": newPosition.x--; break;
            }
            if (room.isValidPosition(newPosition)) {
                position = newPosition;
            }
        }
    }

    String getPosition() {
        return "(" + position.x + "," + position.y + ")";
    }
}