package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {


    @Id
    private UUID uuid;
    //private int xPosition;
    //private int yPosition;

    @Embedded
    private Point point;

    @OneToOne
    private Room north;

    @OneToOne
    private Room south;

    @OneToOne
    private Room east;

    @OneToOne
    private Room west;

    private boolean blocked;

    @OneToOne
    private Field field;


    public void setEast(Room east) {
        this.east = east;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Field getField() {
        return this.field == null ? null : this.field ;
    }

    public Point getPoint() {
        return this.point == null ? null : this.point;
    }

    //public int getXPosition() {return xPosition;}

    //public int getYPosition() {return yPosition;}

    public boolean isBlocked() {
        return blocked;
    }


    public Room (Point point, Field field){
        this.point = point;
        this.blocked = false;
        this.field = field;
        this.uuid = UUID.randomUUID();
    }


    public Room moveNorth(int steps) {
        blocked = false;
        if(north == null || steps == 0 || north.blocked ) return this;
        return north.moveNorth(steps-1);
    }

    public Room moveSouth(int steps){
        blocked = false;
        if(south == null || steps == 0 || south.blocked) return this;
        return south.moveSouth(steps-1);
    }

    public Room moveEast(int steps){
        blocked = false;
        if(east == null || steps == 0 || east.blocked) return this;
        return east.moveEast(steps-1);
    }

    public Room moveWest(int steps){
        blocked = false;
        if(west == null || steps == 0 || west.blocked) return this;
        return west.moveWest(steps-1);
    }
}
