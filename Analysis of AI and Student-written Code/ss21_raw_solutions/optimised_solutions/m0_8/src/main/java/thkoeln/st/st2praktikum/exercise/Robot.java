package thkoeln.st.st2praktikum.exercise;

import java.awt.*;

public class Robot {
    private Point position;

    public Robot(Point initialPosition) {
        this.position = initialPosition;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void walkTo(String direction, int steps, Room room) {
        for (int i = 0; i < steps; i++) {
            Point nextPosition = new Point(position);
            switch (direction) {
                case "no":
                    nextPosition.y += 1;
                    break;
                case "ea":
                    nextPosition.x += 1;
                    break;
                case "so":
                    nextPosition.y -= 1;
                    break;
                case "we":
                    nextPosition.x -= 1;
                    break;
            }

            if (room.isInBounds(nextPosition) && !room.isWall(nextPosition)) {
                position = nextPosition;
            } else {
                break;
            }
        }
    }
}
