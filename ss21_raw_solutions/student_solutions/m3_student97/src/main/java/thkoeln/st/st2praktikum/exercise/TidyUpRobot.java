package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class TidyUpRobot {

    @Id
    private UUID id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Room room;
    @Embedded
    private Point position;
    @Embedded
    private Vector orientation;


    public TidyUpRobot() {
        this.id = UUID.randomUUID();
        this.position = new Point(0,0);
        this.orientation = new Vector(0,0);
    }

    public TidyUpRobot(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.position = new Point(0,0);
        this.orientation = new Vector(0,0);
    }


    public Boolean spawn (Room room, Iterable<TidyUpRobot> robots) {
        if (!collisionWithOtherRobot(room, robots)) {
            this.room = room;
            return true;
        }        
        else return false;
    }


    private void rotate(CommandType direction) {

        switch (direction) {
            case NORTH:
                this.orientation = new Vector(0, 1);
                break;
            case SOUTH:
                this.orientation = new Vector(0, -1);
                break;
            case EAST:
                this.orientation = new Vector(1, 0);
                break;
            case WEST:
                this.orientation = new Vector(-1, 0);
                break;
            default: 
                throw new UnsupportedOperationException();
        }
    }


    public Boolean move (CommandType command, Integer steps) { 
        Integer newPositionX;
        Integer newPositionY;

        this.rotate(command);
        
        for (int i = 0; i < steps; i++) {
            if (this.room.routeIsWalkable(this.position, this.position.getX() + this.orientation.getX(), this.position.getY() + this.orientation.getY())) {
                newPositionX = this.position.getX() + this.orientation.getX();
                newPositionY = this.position.getY() + this.orientation.getY();
                this.position = new Point(newPositionX, newPositionY);
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


    public Boolean collisionWithOtherRobot (Room room, Iterable<TidyUpRobot> robots) {
        for (TidyUpRobot tidyUpRobot : robots) {

            if (tidyUpRobot != this &&
                tidyUpRobot.getPosition() != null &&
                tidyUpRobot.room == room &&
                tidyUpRobot.getPosition().toString().equals("(0,0)"))

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

    public UUID getRoomId() {
        return this.room.getId();
    }

    public Point getPoint() {
        return this.position;
    }
}
