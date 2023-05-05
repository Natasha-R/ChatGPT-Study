package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Robot {

    protected String name;
    protected UUID id;
    protected Room currentRoom;
    protected Coordinate currentPosition;
    protected Coordinate rotation;


    public Robot (String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.currentPosition = new Coordinate("(0,0)");
        this.rotation = new Coordinate("(0,0)");
    }


    public Boolean spawn (Room room, HashMap<UUID, Robot> robots) {
        for (Robot robot : robots.values()) 
            if (robot != this && robot.currentPosition != null && robot.currentRoom == room && robot.currentPosition.getCoordinateString().equals("(0,0)")) 
                    return false;           

        this.currentRoom = room;

        return true;
    }

    
    public void rotate (String direction) {
        this.rotation.y = 0;
        this.rotation.x = 0;

        switch (direction) {
            case "no":
                this.rotation.y += 1;
                break;
            case "so":
                this.rotation.y -= 1;
                break;
            case "ea":
                this.rotation.x += 1;
                break;
            case "we":
                this.rotation.x -= 1;
                break;
        }
    }


    public Boolean move (String direction, Integer steps) { 
        this.rotate(direction);

        for (int i = 0; i < steps; i++) {
            if (this.currentRoom.routeIsBlocked(this.currentPosition, this.currentPosition.x + rotation.x, this.currentPosition.y + rotation.y)) break;
            this.currentPosition.x += rotation.x;
            this.currentPosition.y += rotation.y;
        }

        return true;
    }


    public Boolean transport (Room room) {
        for (Connection connection : this.currentRoom.connections.values()) {
            if (connection.sourceCoordinate.x == this.currentPosition.x && connection.sourceCoordinate.y == this.currentPosition.y) {
                this.currentRoom = room;
                this.currentPosition = connection.destinationCoordinate;
                return true;
            }
        }

        return false;
    }
}
