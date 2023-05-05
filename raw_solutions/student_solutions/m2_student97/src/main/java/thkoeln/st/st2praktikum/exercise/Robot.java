package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;


public class Robot {

    private String name;
    private UUID id;
    private Room room;
    private Point position;
    private Vector orientation;


    public Robot (String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.position = new Point(0,0);
        this.orientation = new Vector(0,0);
    }


    public Boolean spawn (Room room, HashMap<UUID, Robot> robots) {
        if (!collisionWithOtherRobot(room, robots)) {
            this.room = room;
            return true;
        }        
        else return false;
    }


    private void rotate(CommandType direction) {
        this.orientation.setX(0);
        this.orientation.setY(0);

        switch (direction) {
            case NORTH:
                this.orientation.setY(1);
                break;
            case SOUTH:
                this.orientation.setY(-1);
                break;
            case EAST:
                this.orientation.setX(1);
                break;
            case WEST:
                this.orientation.setX(-1);
                break;
            default: 
                throw new UnsupportedOperationException();
        }
    }


    public Boolean move (CommandType command, Integer steps) { 
        this.rotate(command);
        
        for (int i = 0; i < steps; i++) {

            if (this.room.routeIsWalkable(this.position, this.position.getX() + this.orientation.getX(), this.position.getY() + this.orientation.getY())) {
                this.position.setX(this.position.getX() + this.orientation.getX());
                this.position.setY(this.position.getY() + this.orientation.getY());
            }
            else break;
        }

        return true;
    }


    public Boolean transport (Room room) {
        for (Connection connection : this.room.getConnections().values()) {

            if (connection.getSourcePoint().getX() == this.position.getX() && 
                connection.getSourcePoint().getY() == this.position.getY()) {

                this.room = room;
                this.position = connection.getDestinationPoint();
                return true;
            }

        }

        return false;
    }


    public Boolean collisionWithOtherRobot (Room room, HashMap<UUID, Robot> robots) {
        for (Robot robot : robots.values()) {

            if (robot != this && 
                robot.getPosition() != null && 
                robot.room == room && 
                robot.getPosition().toString().equals("(0,0)"))

                return true;
        }
        
        return false;
    }


    public String getName() {
        return this.name;
    }


    public UUID getId () {
        return this.id;
    }


    public Room getRoom () {
        return this.room;
    }


    public Point getPosition () {
        return this.position;
    }
}
